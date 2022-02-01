package net.fruchtlabor.fruchtjobs.jobRelated;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.*;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class JobFactory {

    public static Job getJob(String name) {
        if (name.equalsIgnoreCase("Farmer")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Farmer(100, name, ChatColor.YELLOW, Material.IRON_HOE, "Ich bin ein Farmer ...", "Jobs.Farmer.access", items, monsters);
        } else if (name.equalsIgnoreCase("Fischer")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Fischer(100,name, ChatColor.AQUA, Material.FISHING_ROD, "Ich angle, angle, angle ...", "Jobs.Fischer.access", items, monsters);
        } else if (name.equalsIgnoreCase("Foerster")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Foerster(100,name, ChatColor.DARK_GREEN, Material.IRON_AXE, "Ich jage, schere, fälle ...", "Jobs.Foerster.access", items, monsters);
        } else if (name.equalsIgnoreCase("Miner")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Miner(100,name, ChatColor.GRAY, Material.IRON_PICKAXE, "Stein, Erze, mehr Stein ...", "Jobs.Miner.access", items, monsters);
        } else if (name.equalsIgnoreCase("Schatzsucher")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Schatzsucher(100,name, ChatColor.GOLD, Material.IRON_SHOVEL, "Ne Buddel voll Rum?", "Jobs.Schatzsucher.access", items, monsters);
        } else if (name.equalsIgnoreCase("Verzauberer")) {
            ArrayList<MaterialsLog> items = new ArrayList<>();
            ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();
            return new Verzauberer(100, name, ChatColor.DARK_PURPLE, Material.BOOK, "Hex hex hex ...","Jobs.Verzauberer.access", items, monsters);
        }
        else {
            return null;
        }
    }
}
