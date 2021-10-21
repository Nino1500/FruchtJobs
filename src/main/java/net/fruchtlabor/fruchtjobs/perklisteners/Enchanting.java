package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.Verzauberer;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : enchants.entrySet()){
                        exp += verzauberer.getExpEnchanting(event.getItem(), jobPlayer.lvl);
                    }
                }else{
                    continue;
                }
                if (exp > 0.0){
                    jobPlayer.addExp(exp);

                    for (Perk perk : entry.getValue()){


                    }
                }
            }
        }
    }
}
