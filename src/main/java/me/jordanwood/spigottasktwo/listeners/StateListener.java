package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.events.StateChangeEvent;
import me.jordanwood.spigottasktwo.managers.ScoreboardManager;
import me.jordanwood.spigottasktwo.managers.StateManager;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import me.jordanwood.spigottasktwo.scoreboard.row.SpacerRow;
import me.jordanwood.spigottasktwo.scoreboard.row.TextRow;
import me.jordanwood.spigottasktwo.utils.Title;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import sun.security.provider.ConfigFile;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StateListener implements Listener {
    private static String scoreboardTitle = ChatColor.GOLD +""+ ChatColor.BOLD + "SpigotTask2";

    @EventHandler
    public void onStateChange(StateChangeEvent e) {
        switch (e.getState()) {
            case WAITING: onWaiting(); break;
            case PRESTART: onPreStart(); break;
            case INGAME: onInGame(); break;
            case ENDGAME: onEndGame(); break;
        }
    }

    private void onWaiting() {
        SpigotTaskTwo.getStateManager().registerListeners(new WaitingListener());

        SpigotTaskTwo.getInstance().getLogger().info("Running1");
        WaitingListener.waiting = new Scoreboard(scoreboardTitle);
        WaitingListener.countDown = new Scoreboard(scoreboardTitle);

        TextRow timerTitle = new TextRow(ChatColor.GOLD +""+ ChatColor.BOLD+"Starting in:");
        WaitingListener.countDown.addRows(new SpacerRow(), timerTitle);
        TextRow waiting = new TextRow("Awaiting players...");
        WaitingListener.waiting.addRows(new SpacerRow(), timerTitle, waiting, new SpacerRow());

        TextRow timer = new TextRow("00:10");
        WaitingListener.countDownTimeRow = WaitingListener.countDown.addRow(timer);
        WaitingListener.countDown.addRow(new SpacerRow());

        TextRow playersTitle = new TextRow(ChatColor.GOLD +""+ ChatColor.BOLD+"Players:");
        WaitingListener.waiting.addRow(playersTitle);
        WaitingListener.countDown.addRow(playersTitle);

        TextRow players = new TextRow("1/12");
        WaitingListener.playerCountWaitingRow = WaitingListener.waiting.addRow(players);
        WaitingListener.playerCountCountDownRow = WaitingListener.countDown.addRow(players);
        SpigotTaskTwo.getScoreboardManager().setScoreboard(WaitingListener.waiting);
    }

    private void onPreStart() {
        SpigotTaskTwo.getStateManager().registerListeners(new PreStartListener());

        WorldCreator wc = new WorldCreator("racetrack");
        wc.generateStructures(false);
        wc.environment(org.bukkit.World.Environment.NORMAL);
        wc.seed(0);
        wc.generator(new BlankChunkGenerator());
        World world = Bukkit.createWorld(wc);

        for(Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(new Location(world, 0, 0, 0));
        }

        InGameListener.gameBoard = new Scoreboard(scoreboardTitle);

        // TODO update as players move
        InGameListener.gameBoard.addRows(new SpacerRow(), new TextRow(ChatColor.GOLD + "" + ChatColor.BOLD + "Players:"));
        for (Player p: Bukkit.getOnlinePlayers()) {
            InGameListener.gameBoard.addRow(new TextRow(p.getName()));
        }

        SpigotTaskTwo.getScoreboardManager().setScoreboard(InGameListener.gameBoard);
    }

    private void onInGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new InGameListener());
    }

    private void onEndGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new EndListener());
    }

    private void tickTask(int timer) {
        Bukkit.getScheduler().runTaskLater(SpigotTaskTwo.getInstance(), () -> {
            Bukkit.getOnlinePlayers().forEach((player) -> Title.createTitle(player, String.valueOf(timer), ""));

            if (timer != 0) {
                tickTask(timer - 1);
            }

            Bukkit.getScheduler().runTask(SpigotTaskTwo.getInstance(), ()->{
                SpigotTaskTwo.getStateManager().nextState();
            });
        }, 20L);
    }
}

class BlankChunkGenerator extends ChunkGenerator {
    @Override
    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
    {
        return new byte[world.getMaxHeight() / 16][];
    }
}

