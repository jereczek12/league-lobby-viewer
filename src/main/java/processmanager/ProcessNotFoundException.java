package processmanager;

public class ProcessNotFoundException extends RuntimeException {
    ProcessNotFoundException(String processName) {
        super("Process \"" + processName + "\" not found!");
    }
}
