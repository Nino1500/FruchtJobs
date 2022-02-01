package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.Farmer;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class FarmerListener implements Listener {
    @EventHandler
    public void onHoe(PlayerInteractEvent event){

        if (event.getClickedBlock() == null){
            return;
        }
        if (!Jobs.canBuild(event.getPlayer(), event.getClickedBlock().getLocation())){
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block == null){
                return;
            }
            BlockData blockData = block.getBlockData();
            if (isHoe(player.getInventory().getItemInMainHand().getType()) && blockData instanceof Ageable) {
                for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()) {

                    Job job = entry.getKey();
                    JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());
                    if (cropStuff(block.getType(), job)){
                        if (jobPlayer != null) {
                            double exp = 0.0;

                            if (job instanceof Farmer){
                                Farmer farmer = (Farmer) job;

                                Ageable crop = (Ageable) block.getBlockData();
                                if (crop.getAge() == crop.getMaximumAge()){
                                    Location location = block.getLocation();
                                    for (ItemStack itemStack : block.getDrops()){
                                        location.getWorld().dropItemNaturally(location, itemStack);
                                    }
                                    if (player.hasPermission("perks.Farmer.02") && !Jobs.hardcoded.getPerkByName("Sense aus Stahl").isDeactivated(player)){
                                        if (!chanceToHundred(10)){
                                            durability(player);
                                        }
                                    }else{
                                        durability(player);
                                    }
                                    crop.setAge(0);
                                    block.setBlockData(crop);
                                    exp += farmer.getExp(block.getType(), jobPlayer.lvl);
                                }
                                if(exp > 0.0){
                                    jobPlayer.addExp(exp);
                                    for (Perk perk : entry.getValue()){
                                        if (player.hasPermission(perk.getPermission())){
                                            if (!perk.isDeactivated(player)){
                                                executePerk(perk, jobPlayer, player, block.getLocation(), exp, block.getDrops());
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

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.hasPermission("perks.Farmer.06") && !Jobs.hardcoded.getPerkByName("Was ist Hunger?").isDeactivated(player)){
                if (chanceToHundred(25)){
                    event.setCancelled(true);
                }
            }
        }
    }

    public void executePerk(Perk perk, JobPlayer jobPlayer, Player player, Location location, double exp, Collection<ItemStack> drops){
        if (perk.getName().equalsIgnoreCase("Goldig")){
            if (chanceToHundred(1)){
                if (chanceToHundred(50)){
                    location.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLDEN_APPLE));
                }else{
                    location.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLDEN_CARROT));
                }
            }
        }else if(perk.getName().equalsIgnoreCase("Metalldetektor")){
            if (chanceToHundred(1)){
                ArrayList<Material> list = new ArrayList<>();
                list.add(Material.GOLD_INGOT);
                list.add(Material.IRON_INGOT);
                list.add(Material.COPPER_INGOT);
                location.getWorld().dropItemNaturally(location, new ItemStack(list.get(new Random().nextInt(list.size()))));
            }
        }else if (perk.getName().equalsIgnoreCase("Erfahrener Sammler")){
            if (chanceToHundred(5)){
                jobPlayer.addExp(exp/2);
            }
        }else if (perk.getName().equalsIgnoreCase("Doppeldrop")){
            if (chanceToHundred(2)){
                ItemStack[] itemStacks = (ItemStack[]) drops.toArray();
                for (ItemStack itemStack : itemStacks){
                    location.getWorld().dropItemNaturally(location, itemStack);
                }
            }
        }else if (perk.getName().equalsIgnoreCase("Erfahrungssammler")){
            if (chanceToHundred(2)){
                location.getWorld().spawn(location, ExperienceOrb.class).setExperience(5);
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

    public boolean cropStuff(Material material, Job job){
        for (MaterialsLog mat : job.getItems()){
            if (mat.getMaterial() == material)
                return true;
        }
        return false;
    }

    public void durability(Player player) {
        if (isHoe(player.getInventory().getItemInMainHand().getType())) {
            ItemStack item = player.getInventory().getItemInMainHand();
            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(1);
            item.setItemMeta((ItemMeta) meta);
            if (meta.getDamage() < 2) {
                player.getInventory().getItemInMainHand().setType(Material.AIR);
                player.updateInventory();
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 0.7f);
            }
        }
    }

    public boolean isHoe(Material material){
        ArrayList<Material> list = new ArrayList<>();
        list.add(Material.WOODEN_HOE);
        list.add(Material.STONE_HOE);
        list.add(Material.IRON_HOE);
        list.add(Material.GOLDEN_HOE);
        list.add(Material.DIAMOND_HOE);
        list.add(Material.NETHERITE_HOE);
        return list.contains(material);
    }
}
