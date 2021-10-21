package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.Foerster;
import net.fruchtlabor.fruchtjobs.jobRelated.FruchtMonster;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Killing implements Listener {

    private HashMap<Integer, Double> map = new HashMap<>();

    @EventHandler
    public void onKill(EntityDeathEvent event){

        if (!Jobs.canBuild(event.getEntity().getKiller(), event.getEntity().getLocation())){
            return;
        }

        if (map.containsKey(event.getEntity().getEntityId())){
            if (map.get(event.getEntity().getEntityId()) >= event.getEntity().getHealth() / 2){
                map.remove(event.getEntity().getEntityId());
                if (event.getEntity().getKiller() != null) {
                    Player player = event.getEntity().getKiller();

                    for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()) {
                        Job job = entry.getKey();
                        JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());
                        if (jobPlayer != null) {

                            double exp = 0.0;

                            /*if (job instanceof Jaeger){
                                Jaeger jaeger = (Jaeger) job;
                                exp = jaeger.getExp(event.getEntity(), jobPlayer.lvl);
                            }*/
                            if(job instanceof Foerster){
                                Foerster foerster = (Foerster) job;
                                exp = foerster.getExp(event.getEntity(), jobPlayer.lvl);
                            }
                            else {
                                return;
                            }
                            if(exp > 0.0){
                                jobPlayer.addExp(exp);
                                for (Perk perk : entry.getValue()){
                                    if (player.hasPermission(perk.getPermission())){
                                        if (!perk.isDeactivated(player)){
                                            executePerk(perk, jobPlayer, player, exp, event.getDrops(), event.getDroppedExp());
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

    public void executePerk(Perk perk, JobPlayer jobPlayer, Player player, double exp, Collection<ItemStack> drops, int expdrops){
        if (perk.getName().equalsIgnoreCase("Doppeldrop Viecher")){
            if (chanceToHundred(10)){
                for (ItemStack itemStack : drops){
                    player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
                }
            }
        }
        else if(perk.getName().equalsIgnoreCase("Erfahrungsbonus Viecher")){
            if (chanceToHundred(8)){
                player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(expdrops);
            }
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event){

        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            if (!Jobs.canBuild(player, event.getEntity().getLocation())){
                return;
            }
        }

        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()){
                Job job = entry.getKey();
                JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());
                if (jobPlayer != null){
                    if (/*job instanceof Jaeger ||*/ job instanceof Foerster){
                        for (FruchtMonster monster : job.getMonster()){
                            if (monster.entity.equals(event.getEntity().getType())){
                                if (event.getDamager() instanceof Player){
                                    if (map.containsKey(event.getEntity().getEntityId())){
                                        map.put(event.getEntity().getEntityId(), map.get(event.getEntity().getEntityId())+event.getDamage());
                                    }else{
                                        map.put(event.getEntity().getEntityId(), event.getDamage());
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
}
