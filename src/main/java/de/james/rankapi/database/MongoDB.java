package de.james.rankapi.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MongoDB {

    private MongoClient mongoClient = MongoClients.create("mongodb://riveax:3-IIaZ8GFe58aS%3Ds9629UqiEXdWiwBqv@193.111.248.73:27017/?retryWrites=true&loadBalanced=false&serverSelectionTimeoutMS=5000&connectTimeoutMS=10000&authSource=proxysystem&authMechanism=SCRAM-SHA-1");
    public final MongoDatabase mongoDatabase;

    @Getter
    public MongoCollection<Document> mongoRank;

    @Getter
    public MongoCollection<Document> mongoPlayers;


    public MongoDB() {
        this.mongoDatabase = this.mongoClient.getDatabase("server");
        this.mongoRank = this.mongoDatabase.getCollection("server");
        this.mongoPlayers = this.mongoDatabase.getCollection("players");
    }

    public void createDefaultRanks() {
        if(!isInRankDatabase("default") || isInRankDatabase("default") == null) {
            Document adminRank = new Document("rankName", "admin")
                    .append("prefix", "§c§bAdministrator §8• §7")
                    .append("permissions", "proxysystem.admin");

            Document devRank = new Document("rankName", "dev")
                    .append("prefix", "§bDeveloper §8• §7")
                    .append("permissions", "proxysystem.dev");

            Document modRank = new Document("rankName", "mod")
                    .append("prefix", "§9Moderator §8• §7")
                    .append("permissions", "proxysystem.mod");

            Document supRank = new Document("rankName", "sup")
                    .append("prefix", "§aSupporter §8• §7")
                    .append("permissions", "proxysystem.sup");

            Document builderRank = new Document("rankName", "builder")
                    .append("prefix", "§eBuilder §8• §7")
                    .append("permissions", "proxysystem.builder");

            Document vipRank = new Document("rankName", "vip")
                    .append("prefix", "§6§bVIP §8• §7")
                    .append("permissions", "proxysystem.vip");

            Document playerRank = new Document("rankName", "default")
                    .append("prefix", "§7Player §8• §7")
                    .append("permissions", "proxysystem.player");

            List<Document> rankList = new ArrayList<>(Arrays.asList(adminRank, devRank, modRank, builderRank, supRank, vipRank, playerRank));

            for(Document document : rankList) {
                getMongoRank().insertMany(rankList);
            }
        }
    }

    public Boolean isInRankDatabase(String rankName) {
        return getMongoRank().find(Filters.eq("rankName", rankName)).first() != null;
    }

    public Boolean isInPlayerDatabase(String uuid) {
        return getMongoPlayers().find(Filters.eq("uuid", uuid)).first() != null;
    }

}
