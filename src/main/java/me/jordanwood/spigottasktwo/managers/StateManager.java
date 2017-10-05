package me.jordanwood.spigottasktwo.managers;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.events.StateChangeEvent;
import me.jordanwood.spigottasktwo.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    private State currentState;
    private List<Listener> currentListeners = new ArrayList<>();

    public StateManager(State state) {
        setCurrentState(state);
    }

    public void setCurrentState(State currentState) {
        StateChangeEvent event = new StateChangeEvent(currentState);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            for (Listener listener: currentListeners) {
                HandlerList.unregisterAll(listener);
            }

            this.currentState = currentState;
        }
    }

    public void registerListeners(Listener listener) {
        currentListeners.add(listener);
        Bukkit.getServer().getPluginManager().registerEvents(listener, SpigotTaskTwo.getInstance());
    }

    public State getCurrentState() {
        return currentState;
    }
}
