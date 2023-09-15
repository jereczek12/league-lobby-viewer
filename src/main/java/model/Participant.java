package model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/***
 * Model class mapping name and region of a single player in lobby
 */

@Data
public class Participant {

    String name;
    @SerializedName("game_tag")
    String region;
}
