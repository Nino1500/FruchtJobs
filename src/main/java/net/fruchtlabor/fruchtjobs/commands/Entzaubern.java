package net.fruchtlabor.fruchtjobs.commands;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.perklisteners.Enchanting;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Entzaubern implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (command.getName().equalsIgnoreCase("entzaubern")){
                if (player.hasPermission("perks.Verzauberer.06")){
                    Perk perk = Jobs.hardcoded.getPerkByName("Entzaubern");
                    if (!perk.isDeactivated(player)){
                        ItemStack itemStack = player.getInventory().getItemInMainHand();
                        if (itemStack.getType() != Material.AIR){
                            if (itemStack.getEnchantments().size() > 0){
                                if (check(itemStack.getType())){
                                    ItemMeta meta = itemStack.getItemMeta();
                                    Damageable dmg = (Damageable) meta;
                                    if (dmg.hasDamage()){
                                        player.sendMessage(ChatColor.LIGHT_PURPLE+"[Entzaubern] "+ ChatColor.WHITE+"Hat Gebrauchsspuren!");
                                        return false;
                                    }
                                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                                    EnchantmentStorageMeta storage = (EnchantmentStorageMeta) book.getItemMeta();
                                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
                                    for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()){
                                        if (chanceToHundred(10)){
                                            Enchantment en = Enchantment.getByKey(entry.getKey().getKey());
                                            int lvl = new Random().nextInt(entry.getValue());
                                            storage.addStoredEnchant(en, lvl, false);
                                        }
                                    }
                                    book.setItemMeta(storage);
                                    if (book.getEnchantments().size()>0){
                                        player.getInventory().addItem(book);
                                        return true;
                                    }else{
                                        player.sendMessage(ChatColor.LIGHT_PURPLE+"[Entzaubern] "+ ChatColor.WHITE+"Da hast du nicht richtig entzaubert (10% Chance pro Enchantment)!");
                                        return true;
                                    }
                                }else{
                                    player.sendMessage(ChatColor.LIGHT_PURPLE+"[Entzaubern] "+ ChatColor.WHITE+"Schwert|Bogen|Spitzhacke|Axt|Angel|Sense|Schaufel!");
                                    return false;
                                }
                            }else{
                                player.sendMessage(ChatColor.LIGHT_PURPLE+"[Entzaubern] "+ ChatColor.WHITE+"Das item muss verzaubert sein!");
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean check(Material material){
        String blank = material.toString().toLowerCase();
        if (blank.contains("pickaxe") || blank.contains("axe") || blank.contains("shovel") || blank.contains("hoe") || blank.contains("sword")){
            return true;
        }
        else return material.equals(Material.BOW) || material.equals(Material.FISHING_ROD);
    }
    public boolean chanceToHundred(int chance){
        Random random = new Random();
        if(random.nextDouble() < chance){
            return true;
        }
        return false;
    }
}
