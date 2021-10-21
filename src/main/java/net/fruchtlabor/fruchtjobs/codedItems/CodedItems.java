package net.fruchtlabor.fruchtjobs.codedItems;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.enums.CategoryEnum;
import me.arcaniax.hdb.object.head.Head;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CodedItems {

    public ItemStack getTool(){
        ArrayList<ItemStack> tools = new ArrayList<>();
        tools.addAll(getListOfEnchantedItems(Material.DIAMOND_PICKAXE));
        tools.addAll(getListOfEnchantedItems(Material.DIAMOND_AXE));
        tools.addAll(getListOfEnchantedItems(Material.DIAMOND_SHOVEL));
        tools.addAll(getListOfEnchantedItems(Material.DIAMOND_HOE));
        tools.addAll(getListOfEnchantedItems(Material.IRON_PICKAXE));
        tools.addAll(getListOfEnchantedItems(Material.IRON_AXE));
        tools.addAll(getListOfEnchantedItems(Material.IRON_SHOVEL));
        tools.addAll(getListOfEnchantedItems(Material.IRON_HOE));
        return tools.get(new Random().nextInt(tools.size()));
    }
    public ItemStack getArmor(){
        ArrayList<ItemStack> armor = new ArrayList<>();
        armor.addAll(getListOfEnchantedItems(Material.DIAMOND_HELMET));
        armor.addAll(getListOfEnchantedItems(Material.DIAMOND_CHESTPLATE));
        armor.addAll(getListOfEnchantedItems(Material.DIAMOND_LEGGINGS));
        armor.addAll(getListOfEnchantedItems(Material.DIAMOND_BOOTS));
        armor.addAll(getListOfEnchantedItems(Material.IRON_HELMET));
        armor.addAll(getListOfEnchantedItems(Material.IRON_CHESTPLATE));
        armor.addAll(getListOfEnchantedItems(Material.IRON_LEGGINGS));
        armor.addAll(getListOfEnchantedItems(Material.IRON_BOOTS));
        return armor.get(new Random().nextInt(armor.size()));
    }
    public ItemStack getOre(){
        ArrayList<ItemStack> ore = new ArrayList<>();
        ore.add(new ItemStack(Material.GOLD_ORE));
        ore.add(new ItemStack(Material.GOLD_ORE));
        ore.add(new ItemStack(Material.GOLD_ORE));
        ore.add(new ItemStack(Material.IRON_ORE));
        ore.add(new ItemStack(Material.IRON_ORE));
        ore.add(new ItemStack(Material.IRON_ORE));
        ore.add(new ItemStack(Material.IRON_ORE));
        ore.add(new ItemStack(Material.IRON_ORE));
        ore.add(new ItemStack(Material.COPPER_ORE));
        ore.add(new ItemStack(Material.DIAMOND_ORE));
        ore.add(new ItemStack(Material.EMERALD_ORE));
        ore.add(new ItemStack(Material.LAPIS_ORE));
        ore.add(new ItemStack(Material.REDSTONE_ORE));
        ore.add(new ItemStack(Material.REDSTONE_ORE));
        ore.add(new ItemStack(Material.REDSTONE_ORE));
        ore.add(new ItemStack(Material.REDSTONE_ORE));
        ore.add(new ItemStack(Material.REDSTONE_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_REDSTONE_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_LAPIS_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.COAL_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_COPPER_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_DIAMOND_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_IRON_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_EMERALD_ORE));
        ore.add(new ItemStack(Material.DEEPSLATE_GOLD_ORE));
        return ore.get(new Random().nextInt(ore.size()));
    }
    public ItemStack getIngot(){
        ArrayList<ItemStack> bar = new ArrayList<>();
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.GOLD_INGOT));
        bar.add(new ItemStack(Material.GOLD_INGOT));
        bar.add(new ItemStack(Material.GOLD_INGOT));
        bar.add(new ItemStack(Material.GOLD_INGOT));
        bar.add(new ItemStack(Material.GOLD_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.COPPER_INGOT));
        bar.add(new ItemStack(Material.NETHERITE_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        bar.add(new ItemStack(Material.IRON_INGOT));
        return bar.get(new Random().nextInt(bar.size()));
    }
    public ItemStack getBook(){
        Enchantment[] enchantments = Enchantment.values();
        Enchantment randomEnchantment = enchantments[new Random().nextInt(enchantments.length)];
        int randomLevel = new Random().nextInt(randomEnchantment.getMaxLevel());
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        itemStack.addUnsafeEnchantment(randomEnchantment, randomLevel);
        return itemStack;
    }
    public Head getHead(){
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        List<Head> animals = api.getHeads(CategoryEnum.ANIMALS);
        return animals.get(new Random().nextInt(animals.size()));
    }
    public ItemStack getEgg(){
        ArrayList<ItemStack> eggs = new ArrayList<>();
        eggs.add(new ItemStack(Material.BEE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.SHEEP_SPAWN_EGG));
        eggs.add(new ItemStack(Material.COW_SPAWN_EGG));
        eggs.add(new ItemStack(Material.FOX_SPAWN_EGG));
        eggs.add(new ItemStack(Material.CHICKEN_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PIG_SPAWN_EGG));
        eggs.add(new ItemStack(Material.RABBIT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.SQUID_SPAWN_EGG));
        eggs.add(new ItemStack(Material.TURTLE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.GLOW_SQUID_SPAWN_EGG));
        eggs.add(new ItemStack(Material.DONKEY_SPAWN_EGG));
        eggs.add(new ItemStack(Material.HORSE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.CAT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PARROT_SPAWN_EGG));
        eggs.add(new ItemStack(Material.MULE_SPAWN_EGG));
        eggs.add(new ItemStack(Material.DOLPHIN_SPAWN_EGG));
        eggs.add(new ItemStack(Material.LLAMA_SPAWN_EGG));
        eggs.add(new ItemStack(Material.PANDA_SPAWN_EGG));
        eggs.add(new ItemStack(Material.WOLF_SPAWN_EGG));
        eggs.add(new ItemStack(Material.GOAT_SPAWN_EGG));
        return eggs.get(new Random().nextInt(eggs.size()));
    }
    public ItemStack getFish(int min, int kg_cap){
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(Material.TROPICAL_FISH));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.COD));
        list.add(new ItemStack(Material.PUFFERFISH));
        list.add(new ItemStack(Material.PUFFERFISH));
        list.add(new ItemStack(Material.SALMON));
        list.add(new ItemStack(Material.SALMON));
        list.add(new ItemStack(Material.SALMON));
        list.add(new ItemStack(Material.SALMON));
        list.add(new ItemStack(Material.SALMON));
        for (ItemStack itemStack : list){
            ItemMeta meta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Gewicht: " + getRandomNumber(min, kg_cap) + " kg");
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return list.get(new Random().nextInt(list.size()));
    }
    public ItemStack getWeapon(){
        ArrayList<ItemStack> weapons = new ArrayList<>();
        weapons.addAll(getListOfEnchantedItems(Material.DIAMOND_SWORD));
        weapons.addAll(getListOfEnchantedItems(Material.BOW));
        weapons.addAll(getListOfEnchantedItems(Material.CROSSBOW));
        weapons.addAll(getListOfEnchantedItems(Material.IRON_SWORD));
        weapons.addAll(getListOfEnchantedItems(Material.TRIDENT));
        return weapons.get(new Random().nextInt(weapons.size()));
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public ArrayList<ItemStack> getListOfEnchantedItems(Material material){
        Enchantment[] enchantments = Enchantment.values();
        ArrayList<ItemStack> list = new ArrayList<>();
        for (Enchantment enchantment : enchantments){
            ItemStack itemStack = new ItemStack(material);
            if (enchantment.canEnchantItem(itemStack)){
                for (int i = 0; i < enchantment.getMaxLevel(); i++) {
                    itemStack.addEnchantment(enchantment, i);
                    list.add(itemStack);
                }
            }
        }
        return list;
    }
    ///

    public boolean isCrop(BlockData blockData){
        return blockData instanceof Ageable;
    }
    public boolean isLog(Material material){
        //TODO AAAA
        return true;
    }
    public ArrayList<ItemStack> getChest(int number){
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        if (number == 1){
            itemStacks.add(new ItemStack(Material.GOLD_NUGGET, 1));
            itemStacks.add(new ItemStack(Material.GOLD_NUGGET, 2));
            itemStacks.add(new ItemStack(Material.GOLD_NUGGET, 3));
            itemStacks.add(new ItemStack(Material.IRON_NUGGET, 7));
            itemStacks.add(new ItemStack(Material.IRON_NUGGET, 4));
            itemStacks.add(new ItemStack(Material.IRON_NUGGET, 3));

        }
        if (number == 2){

        }
        if (number == 3){

        }
        return itemStacks;
    }


}
