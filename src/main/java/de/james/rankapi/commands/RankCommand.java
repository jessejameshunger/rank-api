package de.james.rankapi.commands;

import de.james.rankapi.Ranks;
import de.james.rankapi.database.MongoDB;
import de.james.rankapi.players.Players;
import de.james.rankapi.ranks.RankController;
import de.james.rankapi.ranks.RanksDatabaseController;
import de.james.rankapi.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    private final MongoDB mongoDB = Ranks.getRanks().getMongoDB();
    private final Players players = Ranks.getRanks().getPlayers();
    private final RankController rankController = Ranks.getRanks().getRankController();
    private final RanksDatabaseController ranksDatabaseController = Ranks.getRanks().getRanksDatabaseController();
    private final Teams teams = Ranks.getRanks().getTeams();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        String rank = args[2];
        String key = args[0];
        String targetPlayerName = args[1];

            if(key.equalsIgnoreCase("set")) {
                if(args.length == 3) {

                    if(ranksDatabaseController.hasPlayerPermission(rankController.getPlayerRank(player.getName()), "rank.*")) {
                        if(rankController.allRanks.contains(rank)) {
                            if(mongoDB.isInPlayerDatabase(players.getUUIDbyName(targetPlayerName))) {
                                String uuidFromTargetPlayer = players.getUUIDbyName(targetPlayerName);

                                    ranksDatabaseController.setRankToPlayer(uuidFromTargetPlayer, rank);
                                    player.sendMessage("§8» §7Der Rang von §e" + targetPlayerName + " §7wurde zu §e" + rank +" §7gesetzt.");
                                    teams.updateScoreboardPrefixToPlayer(targetPlayerName);
                            } else player.sendMessage("§8» §7Dieser Spieler hat noch nie auf diesem Netzwerk gespielt.");
                        } else player.sendMessage("§8» §7Dieser Rang existiert §cnicht§7.");
                    } else player.sendMessage("§8» §7Du hast keine Berechtigung diesen Befehl auszuführen.");
                  }
                }

        return false;
    }
}
