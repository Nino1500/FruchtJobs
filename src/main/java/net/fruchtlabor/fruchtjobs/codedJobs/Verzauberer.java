package net.fruchtlabor.fruchtjobs.codedJobs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMaterial;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMonster;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class Verzauberer extends Job {

    ArrayList<FruchtMaterial> items = new ArrayList<>();
    ArrayList<FruchtMonster> monsters = new ArrayList<>();

    public Verzauberer(int max_level, String name, ChatColor color, Material gui, String description, String permission, ArrayList<FruchtMaterial> items, ArrayList<FruchtMonster> monsters) {
        super(max_level, name, color, gui, description, permission, items, monsters);
        this.items = items;
        this.monsters = monsters;
    }


    public double getExpEnchanting(ItemStack itemStack, int jobLvl){
        double exp = 0.0;
        for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()){
            for (FruchtMaterial books : items){
                if (books.material.equals(Material.ENCHANTED_BOOK)){
                    if(books.enchantment.equals(entry.getKey()) && books.enchantmentLvl == entry.getValue()){
                        exp += books.getExp();
                    }
                }
            }
        }
        return exp;
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
