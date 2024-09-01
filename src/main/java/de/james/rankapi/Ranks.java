package de.james.rankapi;

import de.james.rankapi.commands.RankCommand;
import de.james.rankapi.database.MongoDB;
import de.james.rankapi.listener.PlayerChatListener;
import de.james.rankapi.listener.PlayerJoinQuitListener;
import de.james.rankapi.players.Players;
import de.james.rankapi.ranks.RankController;
import de.james.rankapi.ranks.RanksDatabaseController;
import de.james.rankapi.teams.Teams;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Ranks extends JavaPlugin {

    @Getter
    public static Ranks ranks;

    @Getter
    public MongoDB mongoDB;

    @Getter
    public RankController rankController;

    @Getter
    public RanksDatabaseController ranksDatabaseController;

     @Getter
     public Teams teams;

     @Getter
     public Players players;

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void onEnable() {
        ranks = this;
        mongoDB = new MongoDB();
        rankController = new RankController();
        ranksDatabaseController = new RanksDatabaseController();
        teams = new Teams();
        players = new Players();

        mongoDB.createDefaultRanks();
        rankController.setAllPrefixesToTemp();
        rankController.setAllPermissionsOfRankToTemp();
        rankController.setAllRanks();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginCommand("rank").setExecutor(new RankCommand());

        logger.severe("rank-module by james was activated.");

    }

    @Override
    public void onDisable() {
        logger.severe("rank-module by james was deactivated.");
    }
}
