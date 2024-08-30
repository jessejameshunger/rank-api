package de.james.rankapi.listener;

import de.james.rankapi.Ranks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setFormat(Ranks.getRanks().getRankController().getPrefixesByPlayerNameTemp(player.getName()) + player.getName() + " §8» §7" + event.getMessage());

    }

}
