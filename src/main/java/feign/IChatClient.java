package feign;

import com.google.gson.JsonObject;


/**
 * Feign Client interface for sending requests to League of Legends Client.
 */
public interface IChatClient {
    /**
     * Sends GET request to "/riotclient/get_region_locale" endpoint
     *
     * @param authToken Authorization token
     * @return JsonObject containing information about region
     */
    @RequestLine("GET /riotclient/get_region_locale")
    @Headers({"Authorization: Basic {authToken}", "Accept: application/json"})
    JsonObject getRegion(@Param("authToken") String authToken);

    /**
     * Sends GET request to "/riotclient/get_region_locale" endpoint
     *
     * @param authToken Authorization token
     * @return JsonObject containing information about players in current Champion select chat lobby.
     */
    @RequestLine("GET /chat/v5/participants/champ-select")
    @Headers("Authorization: Basic {authToken}")
    JsonObject getPlayers(@Param("authToken") String authToken);

}
