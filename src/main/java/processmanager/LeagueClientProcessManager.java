package processmanager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@Slf4j
public class LeagueClientProcessManager {
    private String commandLine;
    @Getter
    private LeagueClientInstance leagueClientInstance;

    /**
     * Method getting the full command line of a running process.
     * Runs a cmd.exe "wmic" command to get the commandline of a process with given name, captures and returns the outptut of cmd as a string.
     *
     * @param processName Name of the process to get a full command line of.
     * @return
     */
    public String getProcessCommandLine(String processName) {
        StringBuilder commandLineBuilder = new StringBuilder();
        try {
            String command = "wmic process where \"name='" + processName + "'\" get commandline";

            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            Process process = processBuilder.start();

            // Read the output of the command
            InputStreamReader processInputReader = new InputStreamReader(process.getInputStream());
            BufferedReader reader = new BufferedReader(processInputReader);

            String line;
            while ((line = reader.readLine()) != null) {
                commandLineBuilder.append(line);
            }
            reader.close();
            processInputReader.close();
            process.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commandLine = substringAfter(commandLineBuilder.toString(), "CommandLine").trim();
        log.debug("Full commandline of process: {}", commandLine);
        return commandLine;
    }

    /**
     * Sets Authorization token and Application Port fields of {@link LeagueClientInstance}
     * to appropriate values taken from the full commandline of LeagueClientUx.exe
     */
    public void setClientInstance() {
        getProcessCommandLine("LeagueClientUx.exe");
        leagueClientInstance = LeagueClientInstance.builder()
                .leagueClientAuthToken(getRiotClientAuthToken())
                .leagueClientAppPort(getRiotClientAppPort())
                .build();
        log.debug(leagueClientInstance.toString());
    }

    protected String getRiotClientAuthToken() {
        String riotClientAuthToken;
        riotClientAuthToken = "riot:" + substringBetween(commandLine, "--riotclient-auth-token=", "\"");
        riotClientAuthToken = Base64.getEncoder().encodeToString(riotClientAuthToken.getBytes());
        return riotClientAuthToken;
    }

    protected int getRiotClientAppPort() {
        String riotClientAppPort;
        riotClientAppPort = substringBetween(commandLine, "--riotclient-app-port=", "\"");
        return Integer.parseInt(riotClientAppPort);
    }

}
