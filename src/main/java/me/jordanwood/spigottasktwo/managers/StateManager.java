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
    private State currentState = null;
    private List<Listener> currentListeners = new ArrayList<>();

    public void nextState() {
        switch (currentState) {
            case PRESTART: setCurrentState(State.WAITING); break;
            case WAITING: setCurrentState(State.INGAME); break;
            case INGAME: setCurrentState(State.ENDGAME); break;
            case ENDGAME: setCurrentState(State.PRESTART); break;
        }
    }

    public void setCurrentState(State currentState) {
        StateChangeEvent event = new StateChangeEvent(currentState);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            currentListeners.forEach(HandlerList::unregisterAll);

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


