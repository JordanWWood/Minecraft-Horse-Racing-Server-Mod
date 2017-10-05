package me.jordanwood.spigottasktwo.managers;
import me.jordanwood.spigottasktwo.scoreboard.Scoreboard;
import me.jordanwood.spigottasktwo.scoreboard.row.Row;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardManager {
    private static ScoreboardManager instance;
    private static Scoreboard currentScoreboard;

    public void setScoreboard(Scoreboard scoreboard) {
        if (currentScoreboard != null) clearScoreboard();
        currentScoreboard = scoreboard;
        installScoreboard();
        PlayerManager.getInstance().installAllColours();
    }

    public void clearScoreboard() {
        if (currentScoreboard == null) return;
        Bukkit.getOnlinePlayers().forEach(this::clearScoreboard);
        currentScoreboard = null;
    }

    public void clearScoreboard(Player player) {
        if (currentScoreboard == null) return;
        player.getScoreboard().getTeams().forEach(t -> {
            t.setPrefix("");
            t.setSuffix("");
        });
    }

    public void updateScoreboard() {
        Bukkit.getOnlinePlayers().forEach(this::updateScoreboard);
    }

    public void updateScoreboard(UUID id) {
        updateScoreboard(Bukkit.getPlayer(id));
    }

    public void updateScoreboard(Player player) {
        if (currentScoreboard == null) return;
        HashMap<Integer, String> rows = renderScoreboard(player);
        updateRows(rows, player);
    }

    public void updateScoreboardRow(UUID rowId) {
        Bukkit.getOnlinePlayers().forEach(player -> this.updateScoreboardRow(player, rowId));
    }

    public void updateScoreboardRow(Player player, UUID rowId) {
        Map.Entry<Integer, String> row = renderScoreboardRow(player, rowId);
        updateRow(row.getKey(), row.getValue(), player);
    }

    private void updateRows(HashMap<Integer, String> rows, Player player) {
        if (rows != null && rows.size() > 0) rows.forEach((score, output) -> {
            this.updateRow(score, output, player);
        });
    }

    private void updateRow(Integer index, String row, Player player) {
        Team team = player.getScoreboard().getTeam("" + index);
        if (team != null) {
            if (row.length() > 16) {
                String first = row.substring(0, 16);
                String formatting = scrapeFormatting(first);
                String rest = formatting + row.substring(16);
                int restLength = rest.length();
                String last = rest.substring(0, restLength < 16 ? restLength : 16);
                team.setPrefix(first);
                team.setSuffix(last);
            } else {
                team.setPrefix(row);
                team.setSuffix("");
            }
        }
    }

    private String scrapeFormatting(String input) {
        String strPatter = "(" + ChatColor.COLOR_CHAR + "[0-9a-fklmnor])";
        Pattern pattern = Pattern.compile(strPatter);
        String formatting = "";

        Matcher matcher = pattern.matcher(input);

        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        if (matches.size() > 0) for (int i = matches.size(); i > 0; i--) {

            int match = (int) matches.get(i - 1).charAt(1);
            if (isColour(match)) {
                return ChatColor.COLOR_CHAR + "" + ((char) match) + formatting;
            } else if (isFormatting(match)) {
                formatting = ChatColor.COLOR_CHAR + "" + ((char) match) + formatting;
            } else if (isReset(match)) {
                return "" + formatting;
            }
        }
        return formatting;
    }

    private boolean isColour(int character) {
        return between(character, 48, 57) || between(character, 65, 70) || between(character, 97, 102);
    }

    private boolean isFormatting(int character) {
        return between(character, 75, 79) || between(character, 107, 111);
    }

    private boolean isReset(int character) {
        return character == 82 || character == 114;
    }

    private boolean between(int number, int min, int max) {
        return number >= min && number <= max;
    }

    private HashMap<Integer, String> renderScoreboard(Player player) {
        HashMap<Integer, String> rows = new HashMap<>();
        int length = currentScoreboard.getRowSet().size();
        if (length > 0) for (Row row : currentScoreboard.getRowSet()) {
            String output;
            try {
                output = row.render(player);
            } catch (Exception e) {
                output = " ";
            }
            rows.put(length, output);
            length--;
        }
        return rows;
    }

    private Map.Entry<Integer, String> renderScoreboardRow(Player player, UUID rowId) {
        int length = currentScoreboard.getRowSet().size();
        Row row = currentScoreboard.getRow(rowId);
        int position = length - currentScoreboard.getRowSet().lastIndexOf(row);
        String output;
        try {
            output = row.render(player);
        } catch (Exception e) {
            output = " ";
        }
        return new AbstractMap.SimpleEntry<Integer, String>(position, output);
    }

    public String generateRowStarter(String index) {
        try {
            return generateRowStarter(Integer.parseInt(index));
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public String generateRowStarter(int index) {
        return ChatColor.COLOR_CHAR + String.join(ChatColor.COLOR_CHAR + "", Integer.toHexString(index).toUpperCase().split("")) + ChatColor.WHITE;
    }

    public void installScoreboard() {
        Bukkit.getOnlinePlayers().forEach(this::installScoreboard);
    }

    public void installScoreboard(UUID id) {
        installScoreboard(Bukkit.getPlayer(id));
    }

    public void installScoreboard(Player player) {
        if (currentScoreboard == null) return;
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.getObjective("objective");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("objective", "dummy");
            objective.setDisplayName(currentScoreboard.getName());
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        for (int i = 1; i <= currentScoreboard.getRows().size(); i++) {
            Team t = scoreboard.getTeam("" + i);
            if (t == null) t = scoreboard.registerNewTeam("" + i);
            t.addEntry(generateRowStarter(i));
            objective.getScore(generateRowStarter(i)).setScore(i);
        }

        player.setScoreboard(scoreboard);

        updateScoreboard(player);
        PlayerManager.getInstance().installAllColoursForPlayer(player.getUniqueId());
    }

    public static ScoreboardManager getInstance() {
        if (instance == null) instance = new ScoreboardManager();
        return instance;
    }
}
