package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.config.Model.Portal;
import me.jordanwood.spigottasktwo.managers.StateManager;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;

import me.jordanwood.spigottasktwo.utils.State;
import org.bukkit.Location;
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
import org.bukkit.util.Vector;

import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.UUID;

public class InGameListener implements Listener {
    static Scoreboard gameBoard;
    static HashMap<UUID, Boolean> hasPassedThroughPortal;
    static HashMap<UUID, Integer> laps;

    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent e) {
        e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "A game is currently in progress!");
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
                //TODO check which direction would look outward
                Location loc = p.getExit().toLocation(e.getPlayer().getWorld());

                hasPassedThroughPortal.replace(e.getPlayer().getUniqueId(), true);
                e.getPlayer().teleport(loc);
            }
        }

        if (e.getTo().toVector().isInAABB(StateListener.worldConfig.getStartLine().getFirstPoint(), StateListener.worldConfig.getStartLine().getFirstPoint())) {
            if (hasPassedThroughPortal.get(e.getPlayer().getUniqueId())) {
                if (laps.get(e.getPlayer().getUniqueId()) == 1) {
                    EndListener.setWinner(e.getPlayer());
                    SpigotTaskTwo.getStateManager().nextState();
                } else {
                    hasPassedThroughPortal.replace(e.getPlayer().getUniqueId(), false);
                    laps.replace(e.getPlayer().getUniqueId(), 1);
                }
            }
        }
    }
}
