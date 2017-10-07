package me.jordanwood.spigottasktwo.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EndListener implements Listener {
    private static Player winner;

    public static Player getWinner() {
        return winner;
    }

    public static void setWinner(Player winner) {
        EndListener.winner = winner;
    }
}
