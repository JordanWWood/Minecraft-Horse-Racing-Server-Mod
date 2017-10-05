package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.events.StateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StateListener implements Listener {
    @EventHandler
    public void onStateChange(StateChangeEvent e) {
        switch (e.getState()) {
            case PREGAME: onPreGame(); break;
            case PRESTART: onPreStart(); break;
            case INGAME: onInGame(); break;
            case ENDGAME: onEndGame(); break;
        }
    }
    private void onPreGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new PreGameListener());
    }

    private void onPreStart() {
        SpigotTaskTwo.getStateManager().registerListeners(new PreStartListener());
    }

    private void onInGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new InGameListener());
    }

    private void onEndGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new EndListener());
    }
}
