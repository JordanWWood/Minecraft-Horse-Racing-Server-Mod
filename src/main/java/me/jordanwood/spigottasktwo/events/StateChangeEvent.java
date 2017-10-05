package me.jordanwood.spigottasktwo.events;

import me.jordanwood.spigottasktwo.utils.State;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StateChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private State state;
    private boolean cancelled;

    public StateChangeEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
