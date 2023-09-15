package model;

import feign.IChatClient;
import lombok.Data;

import java.util.List;

/**
 * Model class for JSON received in {@link IChatClient#getPlayers(String)}
 */
@Data
public class Participants {
    List<Participant> participants;
}
