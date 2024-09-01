package de.james.rankapi.players;

import com.mongodb.client.model.Filters;
import de.james.rankapi.database.MongoDB;

import java.util.Objects;

public class Players {

    private final MongoDB mongoDB = new MongoDB();

    public String getNameByUUID(String uuid) {
        if(mongoDB.isInPlayerDatabase(uuid)) {
            return String.valueOf(Objects.requireNonNull(mongoDB.getMongoPlayers().find(Filters.eq("uuid", uuid)).first()).getString("name"));
        }
        throw new NullPointerException("Player isn't in database: " + uuid);
    }

    public String getUUIDbyName(String playerName) {
        return String.valueOf(Objects.requireNonNull(mongoDB.getMongoPlayers().find(Filters.eq("name", playerName)).first()).getString("uuid"));
    }

}
