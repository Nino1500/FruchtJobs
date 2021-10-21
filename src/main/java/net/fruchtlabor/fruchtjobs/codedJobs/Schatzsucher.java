package net.fruchtlabor.fruchtjobs.codedJobs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMaterial;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMonster;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class Schatzsucher extends Job {

    ArrayList<FruchtMaterial> items = new ArrayList<>();
    ArrayList<FruchtMonster> monsters = new ArrayList<>();

    public Schatzsucher(int max_level, String name, ChatColor color, Material gui, String description, String permission, ArrayList<FruchtMaterial> items, ArrayList<FruchtMonster> monsters) {
        super(max_level, name, color, gui, description, permission, items, monsters);
        this.items = items;
        this.monsters = monsters;
    }


    public double getExp(Material material, int jobLvl){
        for (FruchtMaterial mat : items){
            if(mat != null){
                if (mat.getMaterial().equals(material) && jobLvl >= mat.getAtLvl()) {
                    return mat.getExp();
                }
            }
        }
        return 0.0;
    }
    public double getExp(Entity entity, int jobLvl){
        for (FruchtMonster monster : monsters){
            if(monster != null){
                if(monster.entity.equals(entity.getType()) && jobLvl >= monster.atLvl){
                    return monster.getExp();
                }
            }
        }
        return 0.0;
    }

}
