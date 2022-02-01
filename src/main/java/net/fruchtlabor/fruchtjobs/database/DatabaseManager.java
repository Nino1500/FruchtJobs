package net.fruchtlabor.fruchtjobs.database;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.codedItems.CodedItems;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import net.fruchtlabor.fruchtjobs.logs.SimpleLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DatabaseManager {
    Plugin plugin;
    private final ArrayList<JobPlayer> players;
    private final HashMap<String, ArrayList<SimpleLocation>> log;
    private final HashMap<Job, ArrayList<MaterialsLog>> materialsMap;
    private final HashMap<Job, ArrayList<MaterialsEntityLog>> materialsEntityMap;

    public DatabaseManager(Plugin plugin) {
        this.plugin = plugin;
        players = new ArrayList<>();
        log = new HashMap<>();
        materialsMap = new HashMap<>();
        materialsEntityMap = new HashMap<>();
        getAllPlayers();
        getLog();
        safePeriodic();
        fillMatLog();
    }

    private void safePeriodic(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override public void run() {
                safeAllPlayers();
                safeLog();
            }
        }, 0L, 1200L);
    }

    public void fillMatLog(){
        for (Job job : Jobs.jobs){
            materialsMap.put(job, getJobMaterials(job));
            materialsEntityMap.put(job, getJobMonsters(job));
        }
    }

    public boolean addPlayer(UUID uuid, String jobname, int lvl, double exp, int plvl){
        JobPlayer newplayer = new JobPlayer(jobname, lvl, exp, uuid, plvl);
        if(getByUUID(uuid, jobname) == null){
            players.add(newplayer);
            return true;
        }
        return false;
    }

    public void addLogEntry(SimpleLocation sloc){
        if (!log.containsKey(sloc.getWorld())){
            ArrayList<SimpleLocation> simpleLocations = new ArrayList<>();
            simpleLocations.add(sloc);
            log.put(sloc.getWorld(), simpleLocations);
        }else{
            log.get(sloc.getWorld()).add(sloc);
        }
    }

    public boolean isLogged(Location location){
        if (log.containsKey(location.getWorld().getName())){
            ArrayList<SimpleLocation> list = log.get(location.getWorld().getName());
            for (SimpleLocation simpleLocation : list){
                if (simpleLocation.getRawlocation().equalsIgnoreCase(location.toString())){
                    CodedItems codedItems = new CodedItems();
                    Material material = Material.matchMaterial(simpleLocation.getMaterial());
                    if (codedItems.isCrop(location.getBlock().getBlockData())){
                        return !Jobs.timeBetween(simpleLocation.getTimestamp(), Jobs.getTimeStamp(), 10);
                    }
                    if (codedItems.isLog(material)){
                        return !Jobs.timeBetween(simpleLocation.getTimestamp(), Jobs.getTimeStamp(), 3);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean doesHaveJob(Job job, Player player){
        for (JobPlayer jobPlayer : players){
            if (jobPlayer.job.equalsIgnoreCase(job.getName()) && jobPlayer.uuid.equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public boolean removePlayer(UUID uuid, String jobname){
        return true;
    }

    public ArrayList<JobPlayer> getPlayerJobs(UUID uuid){
        ArrayList<JobPlayer> list = new ArrayList<>();
        for (JobPlayer jobPlayer : players){
            if(jobPlayer.uuid.equals(uuid)){
                list.add(jobPlayer);
            }
        }
        return list;
    }

    public JobPlayer getByUUID(UUID uuid, String jobname){
        for (JobPlayer jobPlayer : players){
            if(jobPlayer.getUuid().equals(uuid) && jobPlayer.getJob().equalsIgnoreCase(jobname)){
                return jobPlayer;
            }
        }
        return null;
    }

    private void getAllPlayers(){
        Database dbController = new Database(plugin);
        for (Job job : Jobs.jobs){
            ArrayList<JobPlayer> list = dbController.getPlayersByJob(job.getName());
            if(list != null){
                players.addAll(list);
            }
        }
    }

    public void safeAllPlayers(){
        Database dbController = new Database(plugin);
        ArrayList<JobPlayer> insertList = new ArrayList<>();
        for (Job job : Jobs.jobs){
            for (JobPlayer jobPlayer : players){
                if(job.getName().equalsIgnoreCase(jobPlayer.getJob())){
                    insertList.add(jobPlayer);
                }
            }
            dbController.insertAllPlayers(insertList, job.getName());
            insertList.clear();
        }
    }

    public void safeLog(){
        Database db = new Database(plugin);
        db.insertAllLocations(log);
    }

    public void getLog(){
        Database db = new Database(plugin);
        ArrayList<SimpleLocation> list = db.getAllLocationsOfLog();
        for (SimpleLocation location : list){
            if (log.containsKey(location.getWorld())){
                log.get(location.getWorld()).add(location);
            }else {
                ArrayList<SimpleLocation> list1 = new ArrayList<>();
                list1.add(location);
                log.put(location.getWorld(), list1);
            }
        }
    }

    private ArrayList<MaterialsLog> getJobMaterials(Job job){
        Database db = new Database(plugin);
        return db.getMaterialLogByJob(job);
    }

    private ArrayList<MaterialsEntityLog> getJobMonsters(Job job){
        Database db = new Database(plugin);
        return db.getEntityLogByJob(job);
    }

    public void changeMaterialsLog(Material material, double exp){
        Database db = new Database(plugin);
        db.changeMaterialsLog(material, exp);
    }

    public void changeEntityLog(EntityType entityType, double exp){
        Database db = new Database(plugin);
        db.changeEntityLog(entityType, exp);
    }

    public boolean addMaterialsLog(MaterialsLog materialsLog){
        Database db = new Database(plugin);
        for (MaterialsLog mat : materialsLog.getJob().getItems()){
            if (mat.getMaterial().equals(materialsLog.getMaterial()) && mat.getType().equals(materialsLog.getType())){
                return false;
            }
        }
        materialsLog.getJob().getItems().add(materialsLog);
        db.insertMaterialsLog(materialsLog);
        return true;
    }

    public boolean addMatEntityLog(MaterialsEntityLog materialsEntityLog){
        Database db = new Database(plugin);
        for (MaterialsEntityLog entityLog : materialsEntityLog.getJob().getMonster()){
            if (entityLog.getEntityType().equals(materialsEntityLog.getEntityType())){
                return false;
            }
        }
        materialsEntityLog.getJob().getMonster().add(materialsEntityLog);
        db.insertEntitiesLog(materialsEntityLog);
        return true;
    }

    public ArrayList<MaterialsLog> getMatList(Job job){
        for (Map.Entry<Job, ArrayList<MaterialsLog>> entry : this.materialsMap.entrySet()){
            if (job.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<MaterialsEntityLog> getEntityList(Job job){
        for (Map.Entry<Job, ArrayList<MaterialsEntityLog>> entry : this.materialsEntityMap.entrySet()){
            if (job.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return new ArrayList<>();
    }

}
