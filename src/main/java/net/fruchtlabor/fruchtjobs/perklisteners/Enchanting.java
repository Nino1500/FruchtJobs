package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.Verzauberer;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Enchanting implements Listener {
    @EventHandler
    public void onEnchant(EnchantItemEvent event){
        Player player = event.getEnchanter();
        Map<Enchantment, Integer> enchants = event.getEnchantsToAdd();

        for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()){

            Job job = entry.getKey();
            JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());

            if (jobPlayer != null){

                double exp = 0.0;

                if (job instanceof Verzauberer){
                    Verzauberer verzauberer = (Verzauberer) job;
                    //TODO CHANGED TEST
                    exp += verzauberer.getExpEnchanting(event.getItem(), jobPlayer.lvl);

                }else{
                    continue;
                }
                if (exp > 0.0){
                    jobPlayer.addExp(exp);

                    for (Perk perk : entry.getValue()){

                        if (!perk.isDeactivated(player)){

                            if (perk.getName().equalsIgnoreCase("Erfahrener Zauberer")){
                                if (chanceToHundred(5)){
                                    player.giveExp(event.getExpLevelCost());
                                }
                            }else if(perk.getName().equalsIgnoreCase("Doppeldrop verzaubern")){
                                if (event.getItem().getType().equals(Material.BOOK)){
                                    if (chanceToHundred(1)){
                                        player.getInventory().addItem(event.getItem());
                                    }
                                }
                            }else if(perk.getName().equalsIgnoreCase("Glück des Verzauberns")){
                                if (chanceToHundred(2)){
                                    for (Map.Entry<Enchantment, Integer> ent : event.getEnchantsToAdd().entrySet()){
                                        if (ent.getKey().getMaxLevel() > ent.getValue()){
                                            for (Map.Entry<Enchantment, Integer> ent2 : event.getItem().getEnchantments().entrySet()){
                                                if (ent.getKey().equals(ent2.getKey())){
                                                    ent2.setValue(ent2.getValue()+1);
                                                    ArrayList<String> lore = new ArrayList<>();
                                                    lore.add(ChatColor.LIGHT_PURPLE+"Verzaubert");
                                                    event.getItem().getItemMeta().setLore(lore);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean chanceToHundred(int chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }
    public static boolean specifyChance(int max, double in){ // 1000 und 1 (1 in 1000 mal)
        double test = Math.random() * max;
        if (test < in){
            System.out.println("True");
            return true;
        }else{
            return false;
        }
    }
}
