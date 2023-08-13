import controllers.LobbyViewerController;
import feign.FeignImpl;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import processmanager.LeagueClientProcessManager;
import view.LobbyViewerUI;

import javax.swing.*;

@Slf4j
public class LeagueLobbyViewerApplication {
    LeagueLobbyViewerApplication() {
        setLookAndFeel();
        LobbyViewerUI lobbyViewerUI = new LobbyViewerUI();
        LeagueClientProcessManager leagueClientProcessManager = new LeagueClientProcessManager();
        LobbyViewerController lobbyViewerController = new LobbyViewerController(lobbyViewerUI, leagueClientProcessManager);
        Response response = new FeignImpl().getClient().get();
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
