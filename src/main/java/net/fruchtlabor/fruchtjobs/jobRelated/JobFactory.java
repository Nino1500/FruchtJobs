package net.fruchtlabor.fruchtjobs.jobRelated;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedJobs.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class JobFactory {

    public static Job getJob(String name) {
        if (name.equalsIgnoreCase("Farmer")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            items.add(new FruchtMaterial(Material.WHEAT, 1.0, 1));
            items.add(new FruchtMaterial(Material.BEETROOT, 1.0, 1));
            items.add(new FruchtMaterial(Material.CARROT, 1.0, 1));
            items.add(new FruchtMaterial(Material.POTATO, 1.0, 1));
            items.add(new FruchtMaterial(Material.MELON, 1.0, 1));
            items.add(new FruchtMaterial(Material.PUMPKIN, 1.0, 1));
            items.add(new FruchtMaterial(Material.CACTUS, 1.0, 1));
            items.add(new FruchtMaterial(Material.COCOA_BEANS, 1.0, 1));
            return new Farmer(100, name, ChatColor.YELLOW, Material.IRON_HOE, "Ich bin ein Farmer ...", "Jobs.Farmer.access", items, monsters);
        } else if (name.equalsIgnoreCase("Fischer")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            items.add(new FruchtMaterial(Material.TROPICAL_FISH, 2.0, 1));
            items.add(new FruchtMaterial(Material.COD, 2.0, 1));
            items.add(new FruchtMaterial(Material.PUFFERFISH, 2.0, 1));
            items.add(new FruchtMaterial(Material.SALMON, 2.0, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 2.0, 1));
            items.add(new FruchtMaterial(Material.NAME_TAG, 2.0, 1));
            items.add(new FruchtMaterial(Material.SADDLE, 2.0, 1));
            items.add(new FruchtMaterial(Material.NAUTILUS_SHELL, 2.0, 1));
            items.add(new FruchtMaterial(Material.FISHING_ROD, 2.0, 1));
            return new Fischer(100,name, ChatColor.AQUA, Material.FISHING_ROD, "Ich angle angle angle ...", "Jobs.Fischer.access", items, monsters);
        } else if (name.equalsIgnoreCase("Foerster")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            monsters.add(new FruchtMonster(EntityType.COW, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.SHEEP, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.HORSE, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.CHICKEN, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.RABBIT, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.SQUID, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.LLAMA, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.DONKEY, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.DOLPHIN, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.TURTLE, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.MUSHROOM_COW, 1.0, 1));
            monsters.add(new FruchtMonster(EntityType.FOX, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.CAT, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.WOLF, 1.0, 1));
            items.add(new FruchtMaterial(Material.OAK_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.BIRCH_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.SPRUCE_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.JUNGLE_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.ACACIA_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.DARK_OAK_LOG,  1.0, 1));
            items.add(new FruchtMaterial(Material.WHITE_WOOL, 1.0, 1));
            return new Foerster(100,name, ChatColor.DARK_GREEN, Material.IRON_AXE, "Ich hacke hacke hacke ...", "Jobs.Foerster.access", items, monsters);
        } /*else if (name.equalsIgnoreCase("Jaeger")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            monsters.add(new FruchtMonster(EntityType.ZOMBIE, 1.0, 1));
            monsters.add(new FruchtMonster(EntityType.SKELETON, 1.0, 1));
            monsters.add(new FruchtMonster(EntityType.PIGLIN, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.SPIDER, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.CAVE_SPIDER, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.CREEPER, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.SLIME, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.MAGMA_CUBE, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.BLAZE, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.GHAST, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.WITHER_SKELETON, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.HOGLIN, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.HUSK, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.ENDERMAN, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.DROWNED, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.WITHER, 1.0, 1));
            monsters.add(new FruchtMonster( EntityType.ENDER_DRAGON, 1.0, 1));
            return new Jaeger(100,name, ChatColor.LIGHT_PURPLE, Material.BOW, "Ich jage jage jage ...", "Jobs.Jaeger.access", items, monsters);
        }*/ else if (name.equalsIgnoreCase("Miner")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            items.add(new FruchtMaterial(Material.STONE, 1.0, 1));
            items.add(new FruchtMaterial(Material.ANDESITE, 1.0, 1));
            items.add(new FruchtMaterial(Material.GRANITE, 1.0, 1));
            items.add(new FruchtMaterial(Material.DIORITE, 1.0, 1));
            items.add(new FruchtMaterial(Material.COAL_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.IRON_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.GOLD_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.NETHER_GOLD_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.DEEPSLATE_GOLD_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.EMERALD_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.DIAMOND_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.COPPER_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.LAPIS_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.NETHER_QUARTZ_ORE, 1.0, 1));
            items.add(new FruchtMaterial(Material.GLOWSTONE, 1.0, 1));
            items.add(new FruchtMaterial(Material.ANCIENT_DEBRIS, 1.0, 1));
            items.add(new FruchtMaterial(Material.MOSSY_COBBLESTONE, 1.0, 1));
            items.add(new FruchtMaterial(Material.OBSIDIAN, 1.0, 1));
            return new Miner(100,name, ChatColor.GRAY, Material.IRON_PICKAXE, "Stein Stein Stein ...", "Jobs.Miner.access", items, monsters);
        } else if (name.equalsIgnoreCase("Schatzsucher")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            items.add(new FruchtMaterial(Material.SAND, 1.0, 1));
            items.add(new FruchtMaterial(Material.RED_SAND, 1.0, 1));
            items.add(new FruchtMaterial(Material.GRASS_BLOCK, 1.0, 1));
            items.add(new FruchtMaterial(Material.DIRT, 1.0, 1));
            items.add(new FruchtMaterial(Material.MYCELIUM, 1.0, 1));
            items.add(new FruchtMaterial(Material.PODZOL, 1.0, 1));
            items.add(new FruchtMaterial(Material.GRAVEL, 1.0, 1));
            items.add(new FruchtMaterial(Material.SOUL_SAND, 1.0, 1));
            items.add(new FruchtMaterial(Material.CLAY, 1.0, 1));
            return new Schatzsucher(100,name, ChatColor.GOLD, Material.IRON_SHOVEL, "Ne Buddel voll Rum?", "Jobs.Schatzsucher.access", items, monsters);
        } else if (name.equalsIgnoreCase("Verzauberer")) {
            ArrayList<FruchtMaterial> items = new ArrayList<>();
            ArrayList<FruchtMonster> monsters = new ArrayList<>();
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.ARROW_DAMAGE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.ARROW_FIRE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.ARROW_INFINITE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.ARROW_KNOCKBACK, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.BINDING_CURSE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.VANISHING_CURSE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.CHANNELING, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DAMAGE_ALL, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DAMAGE_ARTHROPODS, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DAMAGE_UNDEAD, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DEPTH_STRIDER, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DIG_SPEED, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.DURABILITY, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.FIRE_ASPECT, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.FROST_WALKER, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.IMPALING, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.KNOCKBACK, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.LOOT_BONUS_BLOCKS, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.LOOT_BONUS_MOBS, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.LOYALTY, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.LUCK, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.LURE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.MULTISHOT, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.OXYGEN, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PIERCING, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PROTECTION_ENVIRONMENTAL, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PROTECTION_EXPLOSIONS, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PROTECTION_FALL, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PROTECTION_FIRE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.PROTECTION_PROJECTILE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.QUICK_CHARGE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.RIPTIDE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.SILK_TOUCH, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.SOUL_SPEED, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.SWEEPING_EDGE, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.THORNS, 1));
            items.add(new FruchtMaterial(Material.ENCHANTED_BOOK, 1.0, 1, Enchantment.WATER_WORKER, 1));
            return new Verzauberer(100, name, ChatColor.DARK_PURPLE, Material.BOOK, "Hex hex hex ...","Jobs.Verzauberer.access", items, monsters);
        }
        else {
            return null;
        }
    }

    public static ArrayList<String> getDisplayItems(ArrayList<?> toList) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Info: [Nummerierung] [Material] [Experience] [nötiges Level]");
        for (int i = 0; i < toList.size(); i++) {
            if (toList.get(i) instanceof FruchtMaterial){
                FruchtMaterial material = (FruchtMaterial) toList.get(i);
                list.add(
                        "["+i+"] ["+material.getMaterial().name()+"] ["+material.getExp()+"] ["+material.getAtLvl()+"]"
                );
            }
            if (toList.get(i) instanceof FruchtMonster){
                FruchtMonster monster = (FruchtMonster) toList.get(i);
                list.add(
                        "["+i+"] ["+monster.getEntity().name()+"] ["+monster.getExp()+"] ["+monster.getAtLvl()+"]"
                );
            }
        }
        return list;
    }

}
