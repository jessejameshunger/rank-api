package de.james.rankapi.listener;

import de.james.rankapi.Ranks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String prefix = Ranks.getRanks().getRankController().getPrefixesByPlayerNameTemp(player.getName()) + player.getName();
        player.setDisplayName(prefix);
        player.setPlayerListName(prefix);

    }

}
