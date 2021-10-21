package net.fruchtlabor.fruchtjobs.jobRelated;

import net.fruchtlabor.fruchtjobs.Jobs;
import org.bukkit.Bukkit;

import java.util.UUID;

public class JobPlayer {

    public String job;
    public int lvl;
    public double exp;
    public UUID uuid;
    public int plvl;

    public JobPlayer(String job, int lvl, double exp, UUID uuid, int plvl) {
        this.job = job;
        this.lvl = lvl;
        this.exp = exp;
        this.uuid = uuid;
        this.plvl = plvl;
    }

    public String getJob() {
        return job;
    }

    public int getLvl() {
        return lvl;
    }

    public double getExp() {
        return exp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addExp(double experience) {
        if (Bukkit.getPlayer(uuid).hasPermission("Jobs.boost.05")){
            experience += experience / 2;
        }
        if (Bukkit.getPlayer(uuid).hasPermission("Jobs.boost.2")){
            experience += experience * 2;
        }
        if (Bukkit.getPlayer(uuid).hasPermission("Jobs.boost.10")){
            experience += experience * 10;
        }
        if (Bukkit.getPlayer(uuid).hasPermission("Jobs.boost.100")){
            experience += experience * 100;
        }
        this.exp += experience;
        int needed = 150 * lvl + (lvl * lvl * 4);
        while (this.exp >= needed){
            addLvl(1);
            this.exp = this.exp - needed;
            needed = 150 * lvl + (lvl * lvl * 4);
        }
        if (!Bukkit.getPlayer(uuid).hasPermission(Jobs.plugin.getConfig().getString("Permissions.noBossBar"))){
            BossBarManager.checkPlayer(this, this.exp, needed);
        }
        Jobs.econ.depositPlayer(Bukkit.getOfflinePlayer(uuid), experience/10);
    }


    public void addLvl(int lvl) {
        this.lvl += lvl;
        addPlvl(lvl);
        try {
            String msg = Jobs.plugin.getConfig().getString("Messages.levelup");
            msg = msg.replace("{job}", job);
            msg = msg.replace("{level}", this.lvl+"");
            Bukkit.getPlayer(uuid).sendMessage(msg);
        }catch (Exception e){
            System.out.println("kleine Exception im jobsplayer");
        }
    }

    public void addPlvl(int perklvl) {
        this.plvl += perklvl;
    }

    public int getPlvl() {
        return plvl;
    }

    public boolean removePlvl(int points) {
        if (this.plvl - points >= 0) {
            this.plvl = this.plvl - points;
            return true;
        } else {
            return false;
        }
    }
}
