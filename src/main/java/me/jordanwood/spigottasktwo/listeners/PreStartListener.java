package me.jordanwood.spigottasktwo.listeners;

import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PreStartListener implements Listener {
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
