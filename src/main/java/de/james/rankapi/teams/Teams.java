package de.james.rankapi.teams;

import de.james.rankapi.Ranks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Teams {

    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    private Team prefixes = scoreboard.getTeam("playerPrefixes");

    public void createTeams() {
        if(scoreboard.getTeam("playerPrefixes") == null) {
            scoreboard.registerNewTeam("playerPrefixes");
        }

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.setScoreboard(scoreboard);
        }
    }

    public void updateScoreboardPrefixToPlayer(String playerName) {
        createTeams();
        String playerPrefix = Ranks.getRanks().getRankController().getPrefixesByPlayerNameTemp(playerName);
        char lastChar = playerPrefix.charAt(playerPrefix.length() - 1);
        System.out.println(lastChar);

        prefixes.setPrefix(playerPrefix);
        prefixes.addEntry(playerName);
        prefixes.setColor(ChatColor.getByChar(lastChar));

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.setScoreboard(scoreboard);
        }
    }



}
