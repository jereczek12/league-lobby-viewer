import controllers.LobbyViewerController;
import processmanager.LeagueClientProcessManager;
import view.LobbyViewerUI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LeagueLobbyViewerApplication {
    public LeagueLobbyViewerApplication() {
        startApplication();
    }

    private void startApplication() {
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
