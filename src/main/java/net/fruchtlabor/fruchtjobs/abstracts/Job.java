package net.fruchtlabor.fruchtjobs.abstracts;

import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public abstract class Job {

    private int max_level;
    private String name;
    private ChatColor color;
    private Material gui;
    private String description;
    private String permission;
    private ArrayList<MaterialsLog> items;
    private ArrayList<MaterialsEntityLog> monster;

    public Job(int max_level, String name, ChatColor color, Material gui, String description, String permission, ArrayList<MaterialsLog> items, ArrayList<MaterialsEntityLog> monster) {
        this.max_level = max_level;
        this.name = name;
        this.color = color;
        this.gui = gui;
        this.description = description;
        this.permission = permission;
        this.items = items;
        this.monster = monster;
    }

    public int getMax_level() {
        return max_level;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public Material getGui() {
        return gui;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }


    public ArrayList<MaterialsLog> getItems() {
        return items;
    }

    public ArrayList<MaterialsEntityLog> getMonster() {
        return monster;
    }

    public void setItems(ArrayList<MaterialsLog> items) {
        this.items = items;
    }

    public void setMonster(ArrayList<MaterialsEntityLog> monster) {
        this.monster = monster;
    }
}
