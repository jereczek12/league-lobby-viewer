package controllers;

import lombok.extern.slf4j.Slf4j;
import processmanager.LeagueClientProcessManager;
import view.LobbyViewerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@Slf4j
public class LobbyViewerController {
    private final LobbyViewerUI lobbyViewerUI;
    private final LeagueClientProcessManager leagueClientProcessManager;


    public LobbyViewerController(LobbyViewerUI lobbyViewerUI, LeagueClientProcessManager leagueClientProcessManager) {
        this.lobbyViewerUI = lobbyViewerUI;
        this.leagueClientProcessManager = leagueClientProcessManager;
        addListeners();
    }

    void addListeners() {
        lobbyViewerUI.getCopyNamesButton().addActionListener(e -> {
            copySummonerNames();
        });
        lobbyViewerUI.getGetNamesButton().addActionListener(e -> {
            getNames();
        });
    }

    private void getNames() {
        log.info(leagueClientProcessManager.getProcessCommandLine("LeagueClientUx.exe"));
    }

    private void copySummonerNames() {
        StringBuilder summonerNames = new StringBuilder();
        for (JTextField summonersTextField : lobbyViewerUI.getSummonersTextFields()) {
            summonerNames.append(summonersTextField.getText());
            summonerNames.append(", ");
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(summonerNames.toString()), null);
    }


}
