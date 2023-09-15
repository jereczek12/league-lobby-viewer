import controllers.LobbyViewerController;
import processmanager.LeagueClientProcessManager;
import view.LobbyViewerUI;

import javax.swing.*;

public class LeagueLobbyViewerApplication {
    LeagueLobbyViewerApplication() {
        setLookAndFeel();
        LobbyViewerUI lobbyViewerUI = new LobbyViewerUI();
        LeagueClientProcessManager leagueClientProcessManager = new LeagueClientProcessManager();
        LobbyViewerController lobbyViewerController = new LobbyViewerController(lobbyViewerUI, leagueClientProcessManager);
    }


    public static void main(String[] args) {
        new LeagueLobbyViewerApplication();
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
