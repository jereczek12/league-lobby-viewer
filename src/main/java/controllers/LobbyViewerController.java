package controllers;

import feign.LeagueChatClientImpl;
import model.Participant;
import model.Participants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import processmanager.LeagueClientInstance;
import processmanager.LeagueClientProcessManager;
import processmanager.ProcessNotFoundException;
import view.LobbyViewerUI;

import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LobbyViewerController {
    private final LobbyViewerUI lobbyViewerUI;
    private final LeagueClientProcessManager leagueClientProcessManager;
    private LeagueChatClientImpl feignClient;
    private Participants participants;


    public LobbyViewerController(LobbyViewerUI lobbyViewerUI, LeagueClientProcessManager leagueClientProcessManager) {
        this.lobbyViewerUI = lobbyViewerUI;
        this.leagueClientProcessManager = leagueClientProcessManager;
        addListeners();
    }

    void addListeners() {
        lobbyViewerUI.getCopyNamesButton().addActionListener(e -> {
            copySummonerNamesToClipboard();
        });

        lobbyViewerUI.getGetNamesButton().addActionListener(e -> {
            try {
                setFeignClient();
                participants = feignClient.getPlayers();
                lobbyViewerUI.setSummonerNamesFields(buildPlayersUrls(participants));
            } catch (ProcessNotFoundException processException) {
                processException.printStackTrace();
                JOptionPane.showMessageDialog(lobbyViewerUI, processException.getMessage(), "Process not found!", JOptionPane.ERROR_MESSAGE);
            }
        });

        lobbyViewerUI.getOpggButton().addActionListener(e -> {
            openOpGg();
        });
    }

    private void openOpGg() {
        if (participants == null || participants.getParticipants().isEmpty()) {
            return;
        }
        String url = String.format("https://www.op.gg/multisearch/%s?summoners=%s", feignClient.getRegion(), getNamesAsString());
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setFeignClient() {
        leagueClientProcessManager.setClientInstance();
        LeagueClientInstance leagueClientInstance = leagueClientProcessManager.getLeagueClientInstance();
        feignClient = new LeagueChatClientImpl(leagueClientInstance);
    }


    private List<String> buildPlayersUrls(Participants participants) {
        List<String> playersUrls = new ArrayList<>();
        String template = "<a href=\"https://www.op.gg/summoners/%s/%s\">%s</a>";
        for (Participant participant : participants.getParticipants()) {
            playersUrls.add(String.format(template, participant.getRegion(), participant.getName(), participant.getName()));
        }
        return playersUrls;
    }

    private void copySummonerNamesToClipboard() {
        if (participants == null || participants.getParticipants().isEmpty()) {
            return;
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(
                getNamesAsString()), null);
    }

    private String getNamesAsString() {
        return StringUtils.chop(
                participants.getParticipants().stream()
                        .map(Participant::getName)
                        .toList()
                        .toString().substring(1));
    }

}
