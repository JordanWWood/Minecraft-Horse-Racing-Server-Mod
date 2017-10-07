package me.jordanwood.spigottasktwo.config;

import me.jordanwood.spigottasktwo.config.Model.Portal;
import me.jordanwood.spigottasktwo.config.Model.Volume;

import java.util.List;
import java.util.Vector;

public class WorldConfig {
    private List<Portal> protals;
    private List<Vector> spawnLocations;
    private Volume startLine;

    public List<Portal> getProtals() {
        return protals;
    }

    public List<Vector> getSpawnLocations() {
        return spawnLocations;
    }

    public Volume getStartLine() {
        return startLine;
    }
}
