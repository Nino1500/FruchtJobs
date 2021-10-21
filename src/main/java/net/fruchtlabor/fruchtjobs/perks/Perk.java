package net.fruchtlabor.fruchtjobs.perks;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Perk {
    private String permission;
    private String name;
    private int cost;
    private String description;
    private Material gui;
    private String job;
    private ArrayList<Player> deactivated_list;


    public Perk(String permission, String name, int cost, String description, Material gui, String job) {
        this.permission = permission;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.gui = gui;
        this.job = job;
        deactivated_list = new ArrayList<>();
    }

    public void deactivate(Player player){
        deactivated_list.add(player);
    }

    public boolean isDeactivated(Player player){
        return deactivated_list.contains(player);
    }

    public void activate(Player player){
        deactivated_list.remove(player);
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public Material getGui() {
        return gui;
    }

    public String getJob() {
        return job;
    }
}
