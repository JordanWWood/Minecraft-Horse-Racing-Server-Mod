package me.jordanwood.spigottasktwo;

import me.jordanwood.spigottasktwo.listeners.ScoreboardListener;
import me.jordanwood.spigottasktwo.listeners.StateListener;
import me.jordanwood.spigottasktwo.managers.PlayerManager;
import me.jordanwood.spigottasktwo.managers.ScoreboardManager;
import me.jordanwood.spigottasktwo.managers.StateManager;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import me.jordanwood.spigottasktwo.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotTaskTwo extends JavaPlugin {
    private static SpigotTaskTwo instance;
    private static StateManager stateManager;
    private static ScoreboardManager scoreboardManager;
    private static PlayerManager playerManager;

    @Override
    public void onEnable() {
        instance = this;

        instance.getLogger().info("Running0");
        Bukkit.getServer().getPluginManager().registerEvents(new StateListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ScoreboardListener(), this);

        playerManager = new PlayerManager();
        scoreboardManager = new ScoreboardManager();
        stateManager = new StateManager();

        stateManager.setCurrentState(State.WAITING);
    }

    @Override
    public void onDisable() {

    }

    public static SpigotTaskTwo getInstance() {
        return instance;
    }

    public static StateManager getStateManager() {
        return stateManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
