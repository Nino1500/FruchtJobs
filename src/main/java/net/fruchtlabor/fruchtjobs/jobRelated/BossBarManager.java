package net.fruchtlabor.fruchtjobs.jobRelated;

import net.fruchtlabor.fruchtjobs.Jobs;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class BossBarManager {

    public static HashMap<Player, BossBar> bossBars = new HashMap<>();

    public static void checkPlayer(JobPlayer jobPlayer, double exp, double needed){
        Player player = Bukkit.getPlayer(jobPlayer.uuid);
        double progress = (float)(exp / needed);
        if (!bossBars.containsKey(player)){
            BossBar bossBar = Bukkit.createBossBar(jobPlayer.job + " - " + exp + " / " + needed, BarColor.GREEN, BarStyle.SOLID);
            bossBar.addPlayer(player);
            bossBar.setProgress(progress);
            bossBars.put(player, bossBar);
            removeStuff(player);
        }else{
            bossBars.get(player).setTitle(jobPlayer.job + " - " + exp + " / " + needed);
            bossBars.get(player).setProgress(progress);
        }
    }

    public static void removeStuff(Player player){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Jobs.plugin, new Runnable() {
            @Override
            public void run() {
                BossBar bossBar = bossBars.get(player);
                if (bossBar != null){
                    bossBar.removePlayer(player);
                    bossBars.remove(player);
                }
            }
        }, 200L);
    }
}
