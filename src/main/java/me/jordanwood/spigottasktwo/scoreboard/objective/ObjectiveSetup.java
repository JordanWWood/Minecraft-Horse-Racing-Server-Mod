package me.jordanwood.spigottasktwo.scoreboard.objective;

public class ObjectiveSetup {
    private String identifier;
    private String criteria;
    private ObjectiveDisplaySlot displaySlot;
    private String displayName;

    public ObjectiveDisplaySlot getDisplaySlot() {
        return displaySlot;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDisplaySlot(ObjectiveDisplaySlot displaySlot) {
        this.displaySlot = displaySlot;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
