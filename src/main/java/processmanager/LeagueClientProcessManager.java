package processmanager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class LeagueClientProcessManager {

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
                System.out.println(line);
                commandLineBuilder.append(line);
            }
            reader.close();
            processInputReader.close();
            process.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return StringUtils.substringAfter(commandLineBuilder.toString(), "CommandLine").trim();
    }

}
