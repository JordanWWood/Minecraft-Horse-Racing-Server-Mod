package me.jordanwood.spigottasktwo.scoreboard.objective;

public enum ObjectiveDisplaySlot {
    PLAYER_LIST(0),
    SIDEBAR(1),
    BELOW_NAME(2);

    private final int value;

    ObjectiveDisplaySlot(int value) { this.value = value; }

    public int getValue() { return value; }
}
