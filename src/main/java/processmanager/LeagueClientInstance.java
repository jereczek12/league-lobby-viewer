package processmanager;

import lombok.Builder;

/**
 * @param leagueClientAuthToken Authorization token in form: "riot:{tokenValue}" encoded to Base64
 * @param leagueClientAppPort   port of local League Client application
 */
@Builder
public record LeagueClientInstance(
        String leagueClientAuthToken,
        int leagueClientAppPort
) {
}
