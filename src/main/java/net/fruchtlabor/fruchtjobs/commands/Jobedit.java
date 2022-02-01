package net.fruchtlabor.fruchtjobs.commands;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.logs.MaterialType;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

// /jobedit create material EXP JOB ATLVL TYPE (taking item in hand - if enchantedbook look up and extract)
// /jobedit create mob mob EXP JOB ATLVL TYPE

public class Jobedit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            if (command.getName().equalsIgnoreCase("jobedit")){
                if (player.hasPermission("jobedit.edit")){
                    if(strings.length == 0){
                        player.sendMessage("/jobedit create material exp JOB ATLVL TYPE | - ITEM AUS DER HAND");
                        player.sendMessage("/jobedit create mob ENTITY exp JOB ATLVL TYPE | -ENTITY = NAME");
                        player.sendMessage("/jobedit change material exp | - ITEM AUS DER HAND");
                        player.sendMessage("/jobedit change mob ENTITY exp | - ENTITY = NAME");
                        return true;
                    }
                    if (strings.length == 2){
                        if (strings[1].equalsIgnoreCase("reload")){
                            Jobs.reloadJobs();
                        }
                        return true;
                    }
                    if (strings.length == 4) {
                        if (strings[1].equalsIgnoreCase("show")) {
                            if (strings[2].equalsIgnoreCase("job")) {
                                Job job = Jobs.getJobByName(strings[3]);
                                if (job != null) {
                                    for (MaterialsLog materialsLog : Jobs.DATABASEMANAGER.getMatList(job)) {
                                        player.sendMessage(materialsLog.getMaterial().name());
                                    }
                                    for (MaterialsEntityLog materialsEntityLog : Jobs.DATABASEMANAGER.getEntityList(job)) {
                                        player.sendMessage(materialsEntityLog.getEntityType().name());
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                    if (strings.length >= 5){
                        if (strings[0].equalsIgnoreCase("create")){
                            if (strings[1].equalsIgnoreCase("material")){
                                double exp = 0.0;
                                try {
                                    exp = Double.parseDouble(strings[2]);
                                }catch (NumberFormatException e){
                                    player.sendMessage("EXP muss eine Zahl sein");
                                    return false;
                                }
                                Job job = Jobs.getJobByName(strings[3]);
                                if (job == null){
                                    player.sendMessage("Job gibts nicht!");
                                    return false;
                                }
                                int atlvl = Integer.parseInt(strings[4]);
                                MaterialType materialType = MaterialType.valueOf(strings[5]);
                                ItemStack itemStack = player.getInventory().getItemInMainHand();
                                if (itemStack.getType() == Material.AIR){
                                    player.sendMessage("Item in Hand nehmen!");
                                    return false;
                                }
                                if (itemStack.getType().equals(Material.ENCHANTED_BOOK)){
                                    if (itemStack.getEnchantments().size() == 1){
                                        for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()){
                                            Enchantment enchantment = entry.getKey();
                                            int ench_lvl = entry.getValue();
                                            MaterialsLog materialsLog = new MaterialsLog(itemStack.getType(), job, exp, materialType, atlvl, enchantment, ench_lvl);
                                            if(Jobs.DATABASEMANAGER.addMaterialsLog(materialsLog)){
                                                player.sendMessage("Hinzugefügt!");
                                                return true;
                                            }
                                        }
                                    }else{
                                        player.sendMessage("Nur eine Verzauberung für den Enchanter pro Buch!");
                                        player.sendMessage("Alle Enchants werden so oder so zusammengezählt!");
                                    }
                                }else{
                                    Material material = itemStack.getType();
                                    MaterialsLog matlog = new MaterialsLog(material, job, exp, materialType, atlvl);
                                    if(Jobs.DATABASEMANAGER.addMaterialsLog(matlog)){
                                        player.sendMessage("Hinzugefügt!");
                                        return true;
                                    }
                                }
                            }else if (strings[1].equalsIgnoreCase("mob")){
                                EntityType entityType = EntityType.valueOf(strings[2]);
                                double exp = 0.0;
                                try {
                                    exp = Double.parseDouble(strings[3]);
                                }catch (NumberFormatException e){
                                    player.sendMessage("EXP muss eine Zahl sein");
                                    return false;
                                }
                                Job job = Jobs.getJobByName(strings[4]);
                                int atlvl = Integer.parseInt(strings[5]);
                                MaterialType materialType = MaterialType.valueOf(strings[6]);
                                MaterialsEntityLog entityLog = new MaterialsEntityLog(entityType, job, exp, materialType, atlvl);
                                if (Jobs.DATABASEMANAGER.addMatEntityLog(entityLog)){
                                    player.sendMessage("Hinzugefügt!");
                                    return true;
                                }
                            }else if(strings[1].equalsIgnoreCase("change")){
                                if (strings[2].equalsIgnoreCase("material")){
                                    double expto = 0.0;
                                    try {
                                        expto = Double.parseDouble(strings[3]);
                                    }catch (NumberFormatException e){
                                        player.sendMessage("EXP muss eine Zahl sein");
                                        return false;
                                    }
                                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                                    if (itemStack.getType() == Material.AIR){
                                        player.sendMessage("Item in hand nehmen!");
                                        return false;
                                    }
                                    Jobs.DATABASEMANAGER.changeMaterialsLog(itemStack.getType(), expto);
                                    Jobs.reloadJobs();
                                    return true;
                                }else if (strings[2].equalsIgnoreCase("mob")) {
                                    EntityType entityType = EntityType.valueOf(strings[3]);
                                    double expto = 0.0;
                                    try {
                                        expto = Double.parseDouble(strings[4]);
                                    }catch (NumberFormatException e){
                                        player.sendMessage("EXP muss eine Zahl sein");
                                        return false;
                                    }
                                    Jobs.DATABASEMANAGER.changeEntityLog(entityType, expto);
                                    Jobs.reloadJobs();
                                    return true;
                                }else{
                                    return false;
                                }
                            }else {
                                return false;
                            }
                        }else{
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}
