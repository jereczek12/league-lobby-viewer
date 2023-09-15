package controllers;

import feign.LeagueChatClientImpl;
import lombok.extern.slf4j.Slf4j;
import processmanager.LeagueClientInstance;
import processmanager.LeagueClientProcessManager;
import view.LobbyViewerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

@Slf4j
public class LobbyViewerController {
    private final LobbyViewerUI lobbyViewerUI;
    private final LeagueClientProcessManager leagueClientProcessManager;
    private LeagueChatClientImpl feignClient;


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
        leagueClientProcessManager.setClientInstance();
        LeagueClientInstance leagueClientInstance = leagueClientProcessManager.getLeagueClientInstance();
        feignClient = new LeagueChatClientImpl(leagueClientInstance);
        List<String> responsePlayers = feignClient.getPlayers();
        String regionResponse = feignClient.getRegion();
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
