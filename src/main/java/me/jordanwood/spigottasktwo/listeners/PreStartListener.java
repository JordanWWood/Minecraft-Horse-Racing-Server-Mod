package me.jordanwood.spigottasktwo.listeners;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PreStartListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "A game is currently in progress");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityCombustFire(EntityCombustEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHungerChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getTo().toVector().getX() != e.getFrom().toVector().getX() &&
                e.getTo().toVector().getZ() != e.getFrom().toVector().getZ())
            e.getPlayer().teleport(e.getFrom());
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {
        if (e.getEntity() instanceof Horse) {
            Horse h = (Horse) e.getEntity();
            Player p = (Player) e.getDismounted();

            h.setPassenger(p);
        }
    }
}
