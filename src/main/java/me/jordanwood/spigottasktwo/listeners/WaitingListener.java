package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.managers.PlayerManager;
import me.jordanwood.spigottasktwo.managers.ScoreboardManager;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import me.jordanwood.spigottasktwo.scoreboard.row.TextRow;
import me.jordanwood.spigottasktwo.utils.State;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WaitingListener implements Listener {
    static UUID countDownTimeRow, playerCountWaitingRow, playerCountCountDownRow;
    static Scoreboard waiting, countDown;

    private boolean isTimerCancelled = false;

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        SpigotTaskTwo.getInstance().getLogger().info("running2");
        if (e.getMessage().equals("startrace")) {
            tickTask(10);
        } else if (e.getMessage().equals("cancel")) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.GOLD + "Game cancelled"));

            isTimerCancelled = true;
        }
    }

    private void tickTask(int timer) {
        Bukkit.getScheduler().runTaskLater(SpigotTaskTwo.getInstance(), () -> {
            if (isTimerCancelled) return;
            Bukkit.getOnlinePlayers().forEach((player) -> player.sendMessage(ChatColor.GOLD + "Game starting in: " + timer));

            if (timer != 0) {
                ((TextRow)countDown.getRow(countDownTimeRow)).setText(String.format("%1$tM:%1$tS", TimeUnit.SECONDS.toMillis(timer)));
                SpigotTaskTwo.getScoreboardManager().updateScoreboard();
                tickTask(timer - 1);
            }

            ((TextRow)countDown.getRow(countDownTimeRow)).setText(String.format("%1$tM:%1$tS", TimeUnit.SECONDS.toMillis(timer)));
            SpigotTaskTwo.getScoreboardManager().updateScoreboard();
            Bukkit.getScheduler().runTask(SpigotTaskTwo.getInstance(), ()->{
                SpigotTaskTwo.getStateManager().nextState();
            });
        }, 20L);
    }
}
