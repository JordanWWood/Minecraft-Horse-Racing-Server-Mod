package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ScoreboardListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        SpigotTaskTwo.getPlayerManager().installAllColoursForPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogout(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        SpigotTaskTwo.getPlayerManager().uninstallPlayerColours(id);
    }

}
