package me.jordanwood.spigottasktwo.scoreboard.row;
import org.bukkit.entity.Player;

public class TextRow implements Row{
	private String text;
	
	public TextRow(String text){
		this.text = text;
	}
	
	@Override
	public String render(Player player) {
		return text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
