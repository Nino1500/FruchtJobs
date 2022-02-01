package net.fruchtlabor.fruchtjobs;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.enums.CategoryEnum;
import me.arcaniax.hdb.object.head.Head;
import net.fruchtlabor.fruchtjobs.codedJobs.CodedJobs;
import net.fruchtlabor.fruchtjobs.commands.Entzaubern;
import net.fruchtlabor.fruchtjobs.commands.JobCommand;
import net.fruchtlabor.fruchtjobs.commands.Jobedit;
import net.fruchtlabor.fruchtjobs.database.Database;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.database.DatabaseManager;
import net.fruchtlabor.fruchtjobs.inventories.HandleInventories;
import net.fruchtlabor.fruchtjobs.otherlisteners.DenyVillager;
import net.fruchtlabor.fruchtjobs.perklisteners.*;
import net.fruchtlabor.fruchtjobs.perks.HardcodedPerks;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Jobs extends JavaPlugin {
    public static List<Job> jobs = new ArrayList<>();
    public static Plugin plugin;
    public static DatabaseManager DATABASEMANAGER;
    public static Economy econ = null;
    public static HardcodedPerks hardcoded;
    public static WorldGuardPlugin worldGuard;

    @Override
    public void onEnable() {

        worldGuard = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

        plugin = this;

        setUpConfig();

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            System.out.println("§cVAULT FEHLT");
            return;
        }

        jobs = new CodedJobs().getJobs();

        Database database = new Database(this);


        for (Job job : jobs) {
            database.createTable(job.getName());
        }

        database.createLogTable();
        database.createMaterialsTable();
        database.createMaterialsEntityTable();

        DATABASEMANAGER = new DatabaseManager(this);

        reloadJobs();

        initGrasBlock();
        initFarmLand();

        hardcoded = new HardcodedPerks();

        Bukkit.getPluginManager().registerEvents(new Destroy(plugin), plugin);
        HandleInventories handleInventories = new HandleInventories();
        Bukkit.getPluginManager().registerEvents(handleInventories, plugin);
        Bukkit.getPluginManager().registerEvents(new Fishing(), plugin);
        Bukkit.getPluginManager().registerEvents(new Killing(), plugin);
        Bukkit.getPluginManager().registerEvents(new FarmerListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new Enchanting(), plugin);
        Bukkit.getPluginManager().registerEvents(new DenyVillager(), plugin);
        Bukkit.getPluginManager().registerEvents(new Move(), plugin);

        this.getCommand("jobs").setExecutor(new JobCommand());
        this.getCommand("job").setExecutor(new JobCommand());
        this.getCommand("entzaubern").setExecutor(new Entzaubern());
        this.getCommand("jobedit").setExecutor(new Jobedit());


    }

    public static boolean canBuild(Player p, Location l) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(p);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        return query.testState(BukkitAdapter.adapt(l), localPlayer, Flags.BUILD);
    }

    public static void reloadJobs(){
        Jobs.DATABASEMANAGER.fillMatLog();
        for (Job job : jobs) {
            job.setItems(DATABASEMANAGER.getMatList(job));
            job.setMonster(DATABASEMANAGER.getEntityList(job));
        }
    }

    public void initGrasBlock(){
        ItemStack item1 = new ItemStack(Material.GRASS_BLOCK);
        NamespacedKey key1 = new NamespacedKey(this, "Grasblock");
        ShapedRecipe recipe1 = new ShapedRecipe(key1, item1);
        recipe1.shape(" D ", " G ");
        recipe1.setIngredient('D', Material.DIRT);
        recipe1.setIngredient('G', Material.GRASS);
        Bukkit.addRecipe(recipe1);
    }
    public void initFarmLand(){
        ItemStack item1 = new ItemStack(Material.FARMLAND);
        NamespacedKey key1 = new NamespacedKey(this, "Farmland");
        ShapedRecipe recipe1 = new ShapedRecipe(key1, item1);
        recipe1.shape(" G ", " W ");
        recipe1.setIngredient('G', Material.GRASS_BLOCK);
        recipe1.setIngredient('W', Material.POTION);
        Bukkit.addRecipe(recipe1);
    }

    public static Job getJobByName(String name) {
        for (Job job : jobs) {
            if (job.getName().equalsIgnoreCase(name)) {
                return job;
            }
        }
        return null;
    }

    private void setUpConfig(){
        File config = new File(plugin.getDataFolder()+File.separator+ "config.yml");
        FileConfiguration data_config = YamlConfiguration.loadConfiguration(config);
        if(!config.exists()){
            plugin.saveResource("config.yml", false);
        }else{
            try {
                data_config.save(config);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static Head getHead(EntityType mob){
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        List<Head> animals = api.getHeads(CategoryEnum.ANIMALS);
        List<Head> mobs = api.getHeads(CategoryEnum.MONSTERS);
        if (!isMonster(mob)){
            for (Head head : animals){
                if (head.name.equalsIgnoreCase(mob.getName()))
                    return head;
            }
        }else{
            for (Head head : mobs){
                if (head.name.equalsIgnoreCase(mob.getName()))
                    return head;
            }
        }
        return api.getHeads(CategoryEnum.ALPHABET).get(new Random().nextInt(100));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static boolean isMonster(EntityType entityType){
        return Monster.class.isAssignableFrom(entityType.getEntityClass());
    }

    public static Timestamp getTimeStamp(){
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    public static boolean timeBetween(Timestamp old, Timestamp current, int min){
        int yeardiff = old.getYear()-current.getYear();
        int monthdiff = old.getMonth()-current.getMonth();
        int daydiff = old.getDay()-current.getDay();
        int hourdiff = old.getHours()-current.getHours();
        if (yeardiff>0){
            return true;
        }else{
            if (monthdiff>0){
                return true;
            }else{
                if (daydiff>0){
                    return true;
                }else{
                    int mindiff = hourdiff*60;
                    mindiff += old.getMinutes()-current.getMinutes();
                    if (mindiff < 0)
                        mindiff *= -1;
                    return min <= mindiff;
                }
            }
        }
    }

}
