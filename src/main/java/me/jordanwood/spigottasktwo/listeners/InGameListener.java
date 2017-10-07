package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.config.Model.Portal;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;

public class InGameListener implements Listener {
    static Scoreboard gameBoard;

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {
        if (e.getEntity() instanceof Horse) {
            Horse h = (Horse) e.getEntity();
            Player p = (Player) e.getDismounted();

            h.setPassenger(p);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        //TODO Update player distance on scoreboard

        for (Portal p : StateListener.worldConfig.getProtals()) {
            if (e.getPlayer().getLocation().toVector().isInAABB(p.getEntrance().getFirstPoint(), p.getEntrance().getSecondPoint())) {
                Location loc = p.getExit().toLocation(e.getPlayer().getWorld());

                e.getPlayer().teleport(loc);
            }
        }
    }
}
