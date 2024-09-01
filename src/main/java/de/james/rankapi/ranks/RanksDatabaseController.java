package de.james.rankapi.ranks;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import de.james.rankapi.Ranks;
import de.james.rankapi.database.MongoDB;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RanksDatabaseController {

    private MongoDB mongoDB = Ranks.getRanks().getMongoDB();


    public void setRankToPlayer(String uuid, String rankName) {
        if(mongoDB.isInRankDatabase(rankName)) {
            Document filter = new Document("uuid", uuid);
            Document updateOperation = new Document("$set", new Document("rank", rankName));

            mongoDB.getMongoPlayers().updateOne(filter, updateOperation);
        }
    }

    public String getRankFromPlayer(String uuid) {
        if(mongoDB.isInPlayerDatabase(uuid)) {
            return String.valueOf(Objects.requireNonNull(mongoDB.getMongoPlayers().find(Filters.eq("uuid", uuid)).first()).getString("rank"));
        }
        throw new NullPointerException("player isn't in database");
    }

    public String getRankPrefix(String rank) {
        if(mongoDB.isInRankDatabase(rank))
            return mongoDB.getMongoRank().find(Filters.eq("rankName", rank)).first().getString("prefix");
        return "default";
    }

    public void setRankPrefix(String rank, String rankPrefix) {
        if(mongoDB.isInRankDatabase(rank)) {
            Document searchedRank = mongoDB.getMongoRank().find(Filters.eq("rankName", rank)).first();
            searchedRank.append("prefix", rankPrefix);
            mongoDB.getMongoRank().insertOne(searchedRank);
        }
    }

    public int getRankLevel(String rankReturn) {
        String[] ranks = {"admin", "dev", "mod", "sup", "builder", "vip", "player"};
        if(mongoDB.isInRankDatabase(rankReturn)) {
            for(String rank : ranks) {
                if(rank.equals(rankReturn)) {
                    switch (rank) {
                        case "admin":
                            return 7;
                        case "dev":
                            return 6;
                        case "mod":
                            return 5;
                        case "sup":
                            return 4;
                        case "builder":
                            return 3;
                        case "vip":
                            return 2;
                        case "default":
                            return 1;
                        default:
                            return 0;
                    }
                }
            }
        }
        return 0;
    }

    public void setRankPermission(String rankName, String permission) {
        if(mongoDB.isInRankDatabase(rankName)) {
            List<String> newPermission = new ArrayList<>();
            newPermission.add(permission);

            Document currentPermission = mongoDB.getMongoRank().find(Filters.eq("rankName", rankName)).first();

            List<String> foundPermissions = currentPermission.getList("permissions", String.class);
            foundPermissions.add(newPermission.toString());

            Document newPermissions = currentPermission.append("permissions", foundPermissions);
            mongoDB.getMongoRank().insertOne(newPermissions);
        }
    }

    public Boolean hasPlayerPermission(String rankName, String permission) {
        if(mongoDB.isInRankDatabase(rankName)) {
            Document currentPermission = mongoDB.getMongoRank().find(Filters.eq("rankName", rankName)).first();
            List<String> foundPermissions = currentPermission.getList("permissions", String.class);

            if(foundPermissions.contains(permission))
                return true;
        }
        return false;
    }

}
