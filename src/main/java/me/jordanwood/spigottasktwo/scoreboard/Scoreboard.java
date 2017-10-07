package me.jordanwood.spigottasktwo.scoreboard;

import me.jordanwood.spigottasktwo.scoreboard.row.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scoreboard {
    private ConcurrentHashMap<UUID, Row> rows = new ConcurrentHashMap<UUID, Row>();
    private CopyOnWriteArrayList<Row> rowSet = new CopyOnWriteArrayList<Row>();
    private String name;

    public Scoreboard(String displayName) {
        name = displayName;
    }

    private UUID addRow(UUID id, Row row) {
        rows.put(id, row);
        rowSet.add(row);
        return id;
    }

    public UUID addRow(Row row) {
        UUID id = UUID.randomUUID();
        return addRow(id, row);
    }

    public List<UUID> addRows(Row... rows) {
        List<UUID> ids = new ArrayList<>();
        for (int i = 0; i < rows.length; i++)ids.add(addRow(rows[i]));
        return ids;
    }

    public Row getRow(UUID id) {
        return rows.getOrDefault(id, null);
    }

    public void replaceRow(UUID id, Row row) {
        rows.replace(id, row);
    }

    public CopyOnWriteArrayList<Row> getRowSet() {
        return rowSet;
    }

    public String getName() {
        return name;
    }

    public ConcurrentHashMap<UUID, Row> getRows() {
        return rows;
    }
}