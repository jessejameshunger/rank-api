package de.james.rankapi.listener;

import de.james.rankapi.Ranks;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Ranks.getRanks().getTeams().updateScoreboardPrefixToPlayer(player.getName());

        Document document = new Document("uuid", player.getUniqueId().toString())
                .append("name", player.getName())
                .append("rank", "admin")
                        .append("permissions", "rank.*");

        Ranks.getRanks().getMongoDB().getMongoPlayers().insertOne(document);

    }

}
