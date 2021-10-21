package net.fruchtlabor.fruchtjobs.abstracts;

import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMaterial;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMonster;
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
    private ArrayList<FruchtMaterial> items;
    private ArrayList<FruchtMonster> monster;

    public Job(int max_level, String name, ChatColor color, Material gui, String description, String permission, ArrayList<FruchtMaterial> items, ArrayList<FruchtMonster> monster) {
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

    public ArrayList<FruchtMaterial> getItems() {
        return items;
    }

    public ArrayList<FruchtMonster> getMonster() {
        return monster;
    }

}
