package feign;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import processmanager.LeagueClientInstance;
import feign.ssl.InsecureSslSocketFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IChatClient}
 */
@Slf4j
public class LeagueChatClientImpl {
    @Getter
    private final IChatClient client;
    private final LeagueClientInstance instance;


    public LeagueChatClientImpl(LeagueClientInstance instance) {
        this.instance = instance;
        int port = instance.leagueClientAppPort();
        client = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .client(new Client.Default(InsecureSslSocketFactory.getInsecureSslSocketFactory(), null))
                .target(IChatClient.class, "https://127.0.0.1:" + port);
    }

    /**
     * Sends request using {@link IChatClient#getPlayers(String)} and filters received JSon to get a List of players names.
     *
     * @return List of players names in champion select lobby
     */
    public List<String> getPlayers() {
        JsonObject players = client.getPlayers(instance.leagueClientAuthToken());
        log.debug("Players request response: {}", players);
        List<String> playersNames = getPlayersNamesFromJson(players);
        return playersNames;
    }

    /**
     * Sends request using {@link IChatClient#getRegion(String)} and filters received JSon to get the name of Region.
     *
     * @return Name of the region used by league client
     */
    public String getRegion() {
        JsonObject regionResponse = client.getRegion(instance.leagueClientAuthToken());
        log.debug("Region request response: {}", regionResponse);
        String regionName = getRegionFromJson(regionResponse);
        return regionName;
    }

    private List<String> getPlayersNamesFromJson(JsonObject json) {
        List<String> playerNames = new ArrayList<>();
        JsonArray participants = json.get("participants").getAsJsonArray();
        for (JsonElement pa : participants) {
            JsonObject player = pa.getAsJsonObject();
            playerNames.add(player.get("name").getAsString());
        }
        return playerNames;
    }

    private String getRegionFromJson(JsonObject jsonObject) {
        return jsonObject.get("region").getAsString();
    }
}
