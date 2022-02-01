package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedItems.CodedItems;
import net.fruchtlabor.fruchtjobs.codedJobs.*;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.SimpleLocation;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Destroy implements Listener {
    Plugin plugin;

    public Destroy(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        if (!Jobs.canBuild(event.getPlayer(), event.getBlock().getLocation())){
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)){
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (Jobs.DATABASEMANAGER.isLogged(block.getLocation())){
            return;
        }else{
            Jobs.DATABASEMANAGER.addLogEntry(new SimpleLocation(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), block.getWorld().getName(), player.getUniqueId(), block.getType().name(), block.getLocation().toString(), Jobs.getTimeStamp()));
        }

        for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()){
            Job job = entry.getKey();
            JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());

            if (jobPlayer != null){

                double exp = 0.0;

                if(job instanceof Miner && isPickAxe(player)){
                    Miner miner = (Miner) job;
                    exp = miner.getExp(event.getBlock().getType(), jobPlayer.lvl);
                }
                else if(job instanceof Farmer){
                    Farmer farmer = (Farmer) job;
                    if (event.getBlock().getBlockData() instanceof Ageable){
                        break;
                    }else{
                        exp = farmer.getExp(event.getBlock().getType(), jobPlayer.lvl);
                    }
                }
                else if(job instanceof Schatzsucher && isShovel(player)){
                    Schatzsucher schatzsucher = (Schatzsucher) job;
                    exp = schatzsucher.getExp(event.getBlock().getType(), jobPlayer.lvl);
                }
                else if(job instanceof Foerster && isAxe(player)){
                    Foerster foerster = (Foerster) job;
                    exp = foerster.getExp(event.getBlock().getType(), jobPlayer.lvl);
                }
                else {
                    continue;
                }
                //adding exp of the block if exp is present
                if(exp > 0){

                    jobPlayer.addExp(exp);

                    for (Perk perk : entry.getValue()){
                        if (player.hasPermission(perk.getPermission())){
                            if (!perk.isDeactivated(player)){

                                Location location = event.getBlock().getLocation();

                                if (perk.getName().equalsIgnoreCase("Grasernte") && location.getBlock().getType().equals(Material.GRASS)){
                                    if (chanceToHundred(2)){
                                        ArrayList<Material> list = new ArrayList<>();
                                        list.add(Material.TALL_GRASS);
                                        list.add(Material.TALL_GRASS);
                                        list.add(Material.WITHER_ROSE);
                                        location.getWorld().dropItemNaturally(location, new
                                                ItemStack(list.get(new Random().nextInt(list.size()))));
                                    }
                                }else if (perk.getName().equalsIgnoreCase("Timber")){
                                    Perk mega_timber = Jobs.hardcoded.getPerkByName("Mega Timber");
                                    Perk doubledrop = Jobs.hardcoded.getPerkByName("Doppeldrop Baumstämme");
                                    boolean doubleit = false;
                                    if (!doubledrop.isDeactivated(player) && player.hasPermission(doubledrop.getPermission())){
                                        doubleit = true;
                                    }
                                    if (!perk.isDeactivated(player) && player.hasPermission(mega_timber.getPermission()) && !mega_timber.isDeactivated(player)){
                                        if (chanceToHundred(10)){
                                            if (doubleit){
                                                int am = 0;
                                                int sizee = cutdownTreeMega(location);
                                                for (int i = 0; i < sizee; i++) {
                                                    if (chanceToHundred(10)){
                                                        am += 1;
                                                    }
                                                }
                                                for (int i = 0; i < am; i++) {
                                                    location.getWorld().dropItemNaturally(location, new ItemStack(location.getBlock().getType()));
                                                }
                                                jobPlayer.addExp(exp * am);
                                            }else{
                                                jobPlayer.addExp(exp * cutdownTreeMega(location));
                                            }
                                        }
                                    }else if(!perk.isDeactivated(player)){
                                        if (chanceToHundred(10)){
                                            if (doubleit){
                                                int am = 0;
                                                int sizee = cutDownTree(location);
                                                for (int i = 0; i < sizee; i++) {
                                                    if (chanceToHundred(10)){
                                                        am += 1;
                                                    }
                                                }
                                                for (int i = 0; i < am; i++) {
                                                    location.getWorld().dropItemNaturally(location, new ItemStack(location.getBlock().getType()));
                                                }
                                                jobPlayer.addExp(exp * am);
                                            }else{
                                                jobPlayer.addExp(exp * cutDownTree(location));
                                            }
                                        }
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Doppeldrop Baumstämme")
                                        && jobPlayer.getJob().equalsIgnoreCase("Foerster")){
                                    if (chanceToHundred(10)){
                                        location.getWorld().dropItemNaturally(location, new ItemStack(location.getBlock().getType()));
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Laubbläser")){
                                    if (isLeaves(location.getBlock().getType())){
                                        location.getBlock().breakNaturally(new ItemStack(Material.SHEARS));
                                        if (chanceToHundred(1)){
                                            location.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLDEN_APPLE));
                                        }
                                        if (chanceToHundred(5)){
                                            location.getWorld().dropItemNaturally(location, new ItemStack(Material.GOLD_NUGGET));
                                        }
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Taschenofen")){
                                    Perk doubledrop = Jobs.hardcoded.getPerkByName("Doppeldrop Erze");
                                    if (isSmeltable(location.getBlock().getType())){
                                        event.setDropItems(false);
                                        for (ItemStack itemStack : event.getBlock().getDrops()) {
                                            location.getWorld().dropItemNaturally(location, new ItemStack(getBars(itemStack.getType()), itemStack.getAmount()));
                                            if (!doubledrop.isDeactivated(player) && player.hasPermission(doubledrop.getPermission())){
                                                if (chanceToHundred(10)){
                                                    location.getWorld().dropItemNaturally(location, new ItemStack(getBars(itemStack.getType()), itemStack.getAmount()));
                                                }
                                            }
                                        }
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Tunnelgräber")){
                                    List<String> worlds = Jobs.plugin.getConfig().getStringList("Worlds.allowed");
                                    if (block.getType().equals(Material.STONE)
                                            || block.getType().equals(Material.ANDESITE)
                                            || block.getType().equals(Material.GRANITE)
                                            || block.getType().equals(Material.DIORITE)){
                                        if (worlds.contains(location.getWorld().getName())){
                                            Location loc = location;
                                            loc.setY(loc.getY()-1);
                                            if (loc.getBlock().getType().equals(Material.STONE)
                                                    || loc.getBlock().getType().equals(Material.ANDESITE)
                                                    || loc.getBlock().getType().equals(Material.GRANITE)
                                                    || loc.getBlock().getType().equals(Material.DIORITE)) {
                                                loc.getBlock().breakNaturally(new ItemStack(Material.NETHERITE_PICKAXE));
                                            }
                                        }
                                    }

                                }else if(perk.getName().equalsIgnoreCase("Glück des Miners")){
                                    if (chanceToHundred(5)){
                                        CodedItems codedItems = new CodedItems();
                                        location.getWorld().dropItemNaturally(location, codedItems.getOre());
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Doppeldrop Erze")){
                                    Perk taschenofen = Jobs.hardcoded.getPerkByName("Taschenofen");
                                    if (!player.hasPermission(taschenofen.getPermission()) && taschenofen.isDeactivated(player)){
                                        if (chanceToHundred(10)){
                                            if (isOre(location.getBlock().getType())){
                                                location.getWorld().dropItemNaturally(location, new ItemStack(location.getBlock().getType()));
                                            }
                                        }
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Joberfahrung")){
                                    if (isOre(event.getBlock().getType())){
                                        if (chanceToHundred(5)){
                                            jobPlayer.addExp(ThreadLocalRandom.current().nextDouble(5, jobPlayer.lvl+5));
                                        }
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Steintouch")){
                                    if(block.getType().equals(Material.COBBLESTONE)){
                                        event.setDropItems(false);
                                        location.getWorld().dropItemNaturally(location, new ItemStack(Material.STONE));
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Verborgene Schätze I")){
                                    if (specifyChance(200, 1)){
                                        location.getBlock().setType(Material.CHEST);
                                        Chest chest = (Chest) location.getBlock().getState();
                                        CodedItems codedItems = new CodedItems();
                                        for (ItemStack itemStack : codedItems.getChest(1)){
                                            chest.getBlockInventory().addItem(itemStack);
                                        }
                                        event.setCancelled(true);
                                        break;
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Verborgene Schätze II")){
                                    if (specifyChance(300, 1)){
                                        location.getBlock().setType(Material.CHEST);
                                        Chest chest = (Chest) location.getBlock().getState();
                                        CodedItems codedItems = new CodedItems();
                                        for (ItemStack itemStack : codedItems.getChest(2)){
                                            chest.getBlockInventory().addItem(itemStack);
                                        }
                                        event.setCancelled(true);
                                        break;
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Verborgene Schätze III")){
                                    if (specifyChance(400, 1)){
                                        location.getBlock().setType(Material.CHEST);
                                        Chest chest = (Chest) location.getBlock().getState();
                                        CodedItems codedItems = new CodedItems();
                                        for (ItemStack itemStack : codedItems.getChest(3)){
                                            chest.getBlockInventory().addItem(itemStack);
                                        }
                                        event.setCancelled(true);
                                        break;
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Erfahrungssucher")){
                                    if (chanceToHundred(5)){
                                        location.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience(new Random().nextInt(10));
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Glasmacher")){
                                    if (block.getType().equals(Material.SAND) || block.getType().equals(Material.RED_SAND)){
                                        event.setCancelled(true);
                                        block.setType(Material.AIR);
                                        location.getWorld().dropItemNaturally(location, new ItemStack(Material.GLASS));
                                    }
                                }else if(perk.getName().equalsIgnoreCase("Steinbäcker")){
                                    for (ItemStack item : block.getDrops()){
                                        location.getWorld().dropItemNaturally(location, new ItemStack(Material.BRICK, item.getAmount()));
                                    }
                                    event.setCancelled(true);
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onHit(PlayerInteractEvent event){

        Block block = event.getClickedBlock();
        if (block == null){
            return;
        }
        if (!Jobs.canBuild(event.getPlayer(), event.getClickedBlock().getLocation())){
            return;
        }

        Player player = event.getPlayer();
        if (isOre(event.getMaterial())){
            Perk perk = Jobs.hardcoded.getPerkByName("Instant");
            if (player.hasPermission(perk.getPermission()) && !perk.isDeactivated(player)){
                if (isPickAxe(player)){
                    durability(player);
                    event.getClickedBlock().breakNaturally(new ItemStack(Material.NETHERITE_PICKAXE));
                }
            }
        }
    }
    public void durability(Player player) {
        if (isPickAxe(player)) {
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
    @EventHandler
    public void onRecipe(CraftItemEvent event){
        if (event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            if (event.getRecipe().getResult().getType().equals(Material.GRASS_BLOCK)){
                if (player.hasPermission("perks.Farmer.07")){
                    if (Jobs.hardcoded.getPerkByName("Rezept: Grasblock").isDeactivated(player)){
                        event.setCancelled(true);
                    }
                }else{
                    event.setCancelled(true);
                }
            }
            if (event.getRecipe().getResult().getType().equals(Material.FARMLAND)){
                if (player.hasPermission("perks.Farmer.09")){
                    if (Jobs.hardcoded.getPerkByName("Rezept: Farmland").isDeactivated(player)){
                        event.setCancelled(true);
                    }
                }else{
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onShear(PlayerShearEntityEvent event){

        if (!Jobs.canBuild(event.getPlayer(), event.getEntity().getLocation())){
            return;
        }

        if (event.getEntity() instanceof Sheep){
            for (Map.Entry<Job, ArrayList<Perk>> entry : Jobs.hardcoded.perkMap.entrySet()){

                if (entry.getKey() instanceof Farmer){

                    JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(event.getPlayer().getUniqueId(), entry.getKey().getName());

                    if (jobPlayer != null){

                        double exp = 0.0;

                        exp = ((Farmer) entry.getKey()).getExp(Material.WHITE_WOOL, jobPlayer.lvl);

                        if (exp > 0){
                            jobPlayer.addExp(exp);

                            for (Perk perk : entry.getValue()){
                                if (perk.getName().equalsIgnoreCase("Schäfer") && event.getPlayer().hasPermission(perk.getPermission()) && !perk.isDeactivated(event.getPlayer())){
                                    if (chanceToHundred(30)){
                                        event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), event.getItem());
                                    }
                                }
                            }
                        }
                    }
                    break;
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
    public boolean isAxe(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return itemStack.getType().toString().toLowerCase().contains("axe");
    }
    public boolean isPickAxe(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return itemStack.getType().toString().toLowerCase().contains("pickaxe");
    }
    public boolean isShovel(Player player){
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return itemStack.getType().toString().toLowerCase().contains("shovel");
    }

    private int cutDownTree(Location location) {
        List<Block> blocks = new LinkedList<>();

        for (int i = location.getBlockY(); i < location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()); i++) {
            Location l = location.add(0, 1, 0);
            if (isLog(location.getBlock().getType()))
                blocks.add(l.getBlock());
            else
                break;
        }
        for (Block block : blocks) {
            block.breakNaturally(new ItemStack(Material.DIAMOND_AXE));
        }
        return blocks.size();
    }

    private boolean isOre(Material material){
        return material.equals(Material.COAL_ORE)
                || material.equals(Material.DEEPSLATE_COAL_ORE)
                || material.equals(Material.IRON_ORE)
                || material.equals(Material.DEEPSLATE_IRON_ORE)
                || material.equals(Material.COPPER_ORE)
                || material.equals(Material.DEEPSLATE_COPPER_ORE)
                || material.equals(Material.GOLD_ORE)
                || material.equals(Material.DEEPSLATE_GOLD_ORE)
                || material.equals(Material.NETHER_GOLD_ORE)
                || material.equals(Material.REDSTONE_ORE)
                || material.equals(Material.DEEPSLATE_REDSTONE_ORE)
                || material.equals(Material.LAPIS_ORE)
                || material.equals(Material.DEEPSLATE_LAPIS_ORE)
                || material.equals(Material.DIAMOND_ORE)
                || material.equals(Material.DEEPSLATE_DIAMOND_ORE)
                || material.equals(Material.EMERALD_ORE)
                || material.equals(Material.DEEPSLATE_EMERALD_ORE);
    }

    private boolean isSmeltable(Material material){
        return  material.equals(Material.IRON_ORE)
                || material.equals(Material.DEEPSLATE_IRON_ORE)
                || material.equals(Material.COPPER_ORE)
                || material.equals(Material.DEEPSLATE_COPPER_ORE)
                || material.equals(Material.GOLD_ORE)
                || material.equals(Material.DEEPSLATE_GOLD_ORE)
                || material.equals(Material.NETHER_GOLD_ORE);
    }

    private Material getBars(Material material){
        if (material.equals(Material.RAW_GOLD)){
            return Material.GOLD_INGOT;
        }
        else if (material.equals(Material.RAW_COPPER)){
            return Material.COPPER_INGOT;
        }
        else if (material.equals(Material.RAW_IRON)){
            return Material.IRON_INGOT;
        }
        else{
            return null;
        }
    }

    private int cutdownTreeMega(Location location){
        Set<Block> treeBlocks = getTree(location.getBlock(), trees());
        for (Block treeBlock : treeBlocks){
            treeBlock.breakNaturally();
        }
        return treeBlocks.size();
    }

    private boolean isLog(Material material){
        return material == Material.ACACIA_LOG
                || material == Material.OAK_LOG
                || material == Material.JUNGLE_LOG
                || material == Material.SPRUCE_LOG
                || material == Material.BIRCH_LOG
                || material == Material.DARK_OAK_LOG
                || material == Material.WARPED_STEM
                || material == Material.CRIMSON_STEM;
    }

    private boolean isLeaves(Material material){
        return material == Material.ACACIA_LEAVES
                || material == Material.OAK_LEAVES
                || material == Material.JUNGLE_LEAVES
                || material == Material.SPRUCE_LEAVES
                || material == Material.BIRCH_LEAVES
                || material == Material.DARK_OAK_LEAVES;
    }

    public Set<Block> getTree(Block start, List<Material> allowedMaterials) {
        return getNearbyBlocks(start, allowedMaterials, new HashSet<Block>());
    }

    private Set<Block> getNearbyBlocks(Block start, List<Material> allowedMaterials, HashSet<Block> blocks) {
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                for (int z = -1; z < 2; z++) {
                    Block block = start.getLocation().clone().add(x, y, z).getBlock();
                    if (block != null && !blocks.contains(block) && allowedMaterials.contains(block.getType())) {
                            blocks.add(block);
                            blocks.addAll(getNearbyBlocks(block, allowedMaterials, blocks));
                    }
                }
            }
        }
        return blocks;
    }

    public List<Material> trees(){
        List<Material> list = new ArrayList<>();
        list.add(Material.ACACIA_LOG);
        list.add(Material.BIRCH_LOG);
        list.add(Material.DARK_OAK_LOG);
        list.add(Material.JUNGLE_LOG);
        list.add(Material.OAK_LOG);
        list.add(Material.SPRUCE_LOG);
        list.add(Material.WARPED_STEM);
        list.add(Material.CRIMSON_STEM);
        return list;
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
