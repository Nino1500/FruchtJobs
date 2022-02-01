package net.fruchtlabor.fruchtjobs.codedJobs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class Schatzsucher extends Job {

    ArrayList<MaterialsLog> items = new ArrayList<>();
    ArrayList<MaterialsEntityLog> monsters = new ArrayList<>();

    public Schatzsucher(int max_level, String name, ChatColor color, Material gui, String description, String permission, ArrayList<MaterialsLog> items, ArrayList<MaterialsEntityLog> monsters) {
        super(max_level, name, color, gui, description, permission, items, monsters);
        this.items = items;
        this.monsters = monsters;
    }


    public double getExp(Material material, int jobLvl){
        for (MaterialsLog mat : items){
            if(mat != null){
                if (mat.getMaterial().equals(material) && jobLvl >= mat.getAtLevel()) {
                    return mat.getExperience();
                }
            }
        }
        return 0.0;
    }
    public double getExp(Entity entity, int jobLvl){
        for (MaterialsEntityLog monster : monsters){
            if(monster != null){
                if(monster.getEntityType().equals(entity.getType()) && jobLvl >= monster.getAtLvl()){
                    return monster.getExperience();
                }
            }
        }
        return 0.0;
    }

    public void setItems(ArrayList<MaterialsLog> items) {
        this.items = items;
    }

    public void setMonsters(ArrayList<MaterialsEntityLog> monsters) {
        this.monsters = monsters;
    }

}
