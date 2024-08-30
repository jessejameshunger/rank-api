package de.james.rankapi.ranks;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import de.james.rankapi.Ranks;
import de.james.rankapi.database.MongoDB;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;

public class RankController {

    public HashMap<String, String> rankPrefixes = new HashMap<>();
    public HashMap<String, List<String>> rankPermissions = new HashMap<>();

    private MongoDB mongoDB = Ranks.getRanks().getMongoDB();

    public void setAllPrefixesToTemp () {
        try (MongoCursor<Document> cursor = mongoDB.getMongoRank().find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                rankPrefixes.put(doc.getString("rankName"), doc.getString("prefix"));
                System.out.println("Loaded all ranks with prefixes to Temp.");
            }
        }
    }

    public void setAllPermissionsOfRankToTemp() {
        try (MongoCursor<Document> cursor = mongoDB.getMongoRank().find(Filters.eq("rankName")).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                rankPermissions.put(doc.getString("rankName"), doc.getList("permissions", String.class));
                System.out.println("Loaded all permissions to Temp.");
            }
        }
    }

    public String getPrefixesTemp(String rankName) {
        if(rankPrefixes.containsKey(rankName)) {
            return rankPrefixes.get(rankName);
        }
        return "default";
    }

    public String getPrefixesByPlayerNameTemp(String playerName) {
        String playerRank = getPlayerRank(playerName);
        if(rankPrefixes.containsKey(playerRank)) {
            return rankPrefixes.get(playerRank);
        }
        return "default";
    }

    public Boolean hasRankPermission(String rankName, String permission) {
        Document currentPermission = mongoDB.getMongoRank().find(Filters.eq("rankName", rankName)).first();
        List<String> foundPermissions = currentPermission.getList("permissions", String.class);

        if(foundPermissions.contains(permission))
            return true;

        return false;
    }

    public String getPlayerRank(String playerName) {
        if(mongoDB.getMongoPlayers().find(Filters.eq("name", playerName)).first() != null) {
            return mongoDB.getMongoPlayers().find(Filters.eq("name", playerName)).first().getString("rank");
        } else
            throw new NullPointerException("Player isn't in Database: " + playerName);
    }

}
