package me.jordanwood.spigottasktwo.scoreboard.row;

import org.bukkit.entity.Player;

public class SpacerRow implements Row{

	@Override
	public String render(Player player) {
		return " ";
	}
}
