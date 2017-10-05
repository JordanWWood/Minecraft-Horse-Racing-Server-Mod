package me.jordanwood.spigottasktwo.listeners;

import me.jordanwood.spigottasktwo.SpigotTaskTwo;
import me.jordanwood.spigottasktwo.events.StateChangeEvent;
import me.jordanwood.spigottasktwo.managers.ScoreboardManager;
import me.jordanwood.spigottasktwo.managers.StateManager;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import me.jordanwood.spigottasktwo.scoreboard.row.SpacerRow;
import me.jordanwood.spigottasktwo.scoreboard.row.TextRow;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
    }

    private void onInGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new InGameListener());
    }

    private void onEndGame() {
        SpigotTaskTwo.getStateManager().registerListeners(new EndListener());
    }
}
