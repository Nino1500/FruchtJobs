package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedItems.CodedItems;
import net.fruchtlabor.fruchtjobs.codedJobs.Fischer;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Fishing implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent event) {

        if (!Jobs.canBuild(event.getPlayer(), event.getPlayer().getLocation())){
            return;
        }

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY){
            Player player = event.getPlayer();

            for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()) {

                Job job = entry.getKey();
                JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());

                if (jobPlayer != null){

                    double exp = 0.0;

                    if (job instanceof Fischer){
                        Fischer fischer = (Fischer) job;
                        if (event.getCaught() instanceof Item){
                            Item item = (Item) event.getCaught();
                            exp = fischer.getExp(item.getItemStack().getType(), jobPlayer.lvl);
                            if(exp > 0.0){
                                jobPlayer.addExp(exp);
                                boolean hasPerk = false;
                                for (Perk perk : entry.getValue()){
                                    if (player.hasPermission(perk.getPermission())){
                                        hasPerk = true;
                                        if (!perk.isDeactivated(player)){
                                            executePerk(perk, jobPlayer, player, exp, event.getCaught());
                                        }
                                    }
                                }
                                if (!hasPerk){
                                    CodedItems codedItems = new CodedItems();
                                    item.setItemStack(codedItems.getFish(1,10));
                                }
                            }else{
                                CodedItems codedItems = new CodedItems();
                                item.setItemStack(codedItems.getFish(1,10));
                            }
                        }
                    }
                }
            }
        }
    }

    public void executePerk(Perk perk, JobPlayer jobPlayer, Player player, double exp, Entity entity){
        CodedItems codedItems = new CodedItems();
        Item item = (Item) entity;
        if (perk.getName().equalsIgnoreCase("Rüstungsfischer")){
            if (chanceToHundred(1)){
                item.setItemStack(codedItems.getArmor());
            }
        }else if(perk.getName().equalsIgnoreCase("Mending")){
            if (specifyChance(1000, 1)){
                ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                itemStack.addUnsafeEnchantment(Enchantment.MENDING, 1);
                item.setItemStack(itemStack);
            }
        }else if(perk.getName().equalsIgnoreCase("Werkzeugfischer")){
            if (chanceToHundred(1)){
                item.setItemStack(codedItems.getTool());
            }
        }else if(perk.getName().equalsIgnoreCase("Waffenfischer")){
            if (chanceToHundred(1)){
                item.setItemStack(codedItems.getWeapon());
            }
        }else if(perk.getName().equalsIgnoreCase("Erzfischer")){
            if (chanceToHundred(1)){
                if (chanceToHundred(50)){
                    item.setItemStack(codedItems.getIngot());
                }else{
                    item.setItemStack(codedItems.getOre());
                }
            }
        }else if(perk.getName().equalsIgnoreCase("Eierfischer")){
            if (specifyChance(200, 1)){
                item.setItemStack(codedItems.getEgg());
            }
        }else if(perk.getName().equalsIgnoreCase("Bücherfischer")){
            if (chanceToHundred(1)){
                item.setItemStack(codedItems.getBook());
            }
        }else if(perk.getName().equalsIgnoreCase("Kopffischer")){
            if (specifyChance(250, 1)){
                item.setItemStack(codedItems.getHead().getHead());
            }
        }else if(perk.getName().equalsIgnoreCase("Geschickter Angler")){
            item.setItemStack(codedItems.getFish(11,25));
        }else if(perk.getName().equalsIgnoreCase("Meister Angler")){
            item.setItemStack(codedItems.getFish(26,50));
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
