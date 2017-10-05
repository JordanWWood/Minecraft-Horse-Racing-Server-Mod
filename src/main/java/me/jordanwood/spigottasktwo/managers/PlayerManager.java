package me.jordanwood.spigottasktwo.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;


public class PlayerManager {
    private static PlayerManager instance;

    private static HashMap<UUID, String> playerColours = new HashMap<>();

    public void setPlayerNameColour(UUID id, String format) {
        playerColours.put(id, format);
        Player player = Bukkit.getPlayer(id);
        String teamName = "NameFormat"+format;
        Bukkit.getOnlinePlayers().forEach(client -> {
            org.bukkit.scoreboard.Scoreboard scoreboard = client.getScoreboard();
            Team team = scoreboard.getTeam(teamName);
            if (team == null) team = scoreboard.registerNewTeam(teamName);
            if (!team.hasEntry(player.getName())) {
                team.addEntry(player.getName());
                team.setPrefix(format);
            }
        });
    }

    public void unsetPlayerNameColour(UUID id) {
        Player player = Bukkit.getPlayer(id);
        String format = playerColours.get(id);
        String teamName = "NameFormat"+format;
        Bukkit.getOnlinePlayers().forEach(client -> {
            org.bukkit.scoreboard.Scoreboard scoreboard = client.getScoreboard();
            Team team = scoreboard.getTeam(teamName);
            if (team == null) return;
            if (team.hasEntry(player.getName())) team.removeEntry(player.getName());
        });
    }

    public void installAllColoursForPlayer(UUID id) {
        Player player = Bukkit.getPlayer(id);
        org.bukkit.scoreboard.Scoreboard scoreboard = player.getScoreboard();
        playerColours.forEach((playerId, format) -> {
            Player player2 = Bukkit.getPlayer(playerId);
            String teamName = "NameFormat"+format;
            Team team = scoreboard.getTeam(teamName);
            if (team == null) team = scoreboard.registerNewTeam(teamName);
            if (player2 != null && !team.hasEntry(player2.getName())) {
                team.addEntry(player2.getName());
                team.setPrefix(format);
            }
        });
    }

    public void installAllColours() {
        playerColours.forEach(this::setPlayerNameColour);
    }

    public void installPlayerColours(UUID id) {
        if (playerColours.containsKey(id)) this.setPlayerNameColour(id, playerColours.get(id));
    }

    public void uninstallPlayerColours(UUID id) {
        if (playerColours.containsKey(id)) {
            this.unsetPlayerNameColour(id);
        }
    }

    public static PlayerManager getInstance() {
        if (instance == null) instance = new PlayerManager();
        return instance;
    }
}
