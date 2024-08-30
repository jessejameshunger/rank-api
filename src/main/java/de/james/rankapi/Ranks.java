package de.james.rankapi;

import de.james.rankapi.database.MongoDB;
import de.james.rankapi.listener.PlayerChatListener;
import de.james.rankapi.listener.PlayerJoinQuitListener;
import de.james.rankapi.ranks.RankController;
import lombok.Getter;
import org.bson.Document;
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

    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void onEnable() {
        ranks = this;
        mongoDB = new MongoDB();
        rankController = new RankController();

        mongoDB.createDefaultRanks();
        rankController.setAllPrefixesToTemp();
        rankController.setAllPermissionsOfRankToTemp();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

        logger.severe("rank-module by james was activated.");

    }

    @Override
    public void onDisable() {
        logger.severe("rank-module by james was deactivated.");
    }
}
