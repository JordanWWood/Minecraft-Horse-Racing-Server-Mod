package me.jordanwood.spigottasktwo;

import me.jordanwood.spigottasktwo.listeners.StateListener;
import me.jordanwood.spigottasktwo.managers.StateManager;
import me.jordanwood.spigottasktwo.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpigotTaskTwo extends JavaPlugin {
    private static SpigotTaskTwo instance;
    private static StateManager stateManager;

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getServer().getPluginManager().registerEvents(new StateListener(), this);
        stateManager = new StateManager(State.PREGAME);
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
}
