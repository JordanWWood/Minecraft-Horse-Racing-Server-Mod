package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.events.StateChangeEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StateListener implements Listener {
    @EventHandler
    public void onStateChange(StateChangeEvent e) {
        switch (e.getState()) {
            case WAITING: onPreGame(); break;
            case PRESTART: onPreStart(); break;
            case INGAME: onInGame(); break;
            case ENDGAME: onEndGame(); break;
        }
    }
    private void onPreGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new WaitingListener());
    }

    private void onPreStart() {
        SpigotTaskTwo.getStateManager().registerListeners(new PreStartListener());
        tickTask(10);
    }

    private void onInGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new InGameListener());
    }

    private void onEndGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new EndListener());
    }

    private void tickTask(int timer) {
        Bukkit.getScheduler().runTaskLater(SpigotTaskTwo.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach((player) -> player.sendMessage(ChatColor.GOLD + "Game starting in: " + timer));

            if (timer != 0) {
                tickTask(timer - 1);
            }
        }, 20L);
    }
}
