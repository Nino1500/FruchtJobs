package net.fruchtlabor.fruchtjobs.inventories;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HandleInventories implements Listener {

    ArrayList<Player> playerTimeouts = new ArrayList<>();

    public HandleInventories() {
        clearTimerList();
    }

    public boolean isTimerPlayer(Player player){
        return playerTimeouts.contains(player);
    }

    public void clearTimerList(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Jobs.plugin, new Runnable() {
            @Override
            public void run() {
                playerTimeouts.clear();
            }
        },200L, 200L);
    }

    @EventHandler
    public void onDrag(InventoryClickEvent event){
        String title = event.getView().getTitle();
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        JobInventory jobInventory = new JobInventory();

        if (itemStack != null){
            if(itemStack.getItemMeta() == null){
                return;
            }
            String name = itemStack.getItemMeta().getDisplayName();

            if (title.equalsIgnoreCase("Jobs")){
                switch (name){
                    case "Jobs":
                        //open jobs inventory
                        jobInventory.getJobsPanel(player);
                        event.setCancelled(true);
                        break;
                    case "Statistiken":
                        // statistiken
                        event.setCancelled(true);
                        break;
                    case "BossBar":
                        if (isTimerPlayer(player)){
                            event.setCancelled(true);
                            return;
                        }
                        if (player.hasPermission(Jobs.plugin.getConfig().getString("Permissions.noBossBar"))){
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission unset "+Jobs.plugin.getConfig().getString("Permissions.noBossBar"));
                            player.sendMessage(ChatColor.RED+"[-]"+ChatColor.WHITE+" BossBar");
                            playerTimeouts.add(player);
                        }else{
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set "+Jobs.plugin.getConfig().getString("Permissions.noBossBar"));
                            player.sendMessage(ChatColor.GREEN+"[+]"+ChatColor.WHITE+" BossBar");
                            playerTimeouts.add(player);
                        }
                        event.setCancelled(true);
                        break;
                    default:
                        event.setCancelled(true);
                }

            }
            if (title.equalsIgnoreCase("JobsPanel")){
                for (Job job : Jobs.jobs){
                    if (name.equalsIgnoreCase(job.getName())){
                        player.openInventory(jobInventory.getJobPanel(job, player));
                    }
                }
                if ("zurück".equalsIgnoreCase(name)) {
                    player.openInventory(jobInventory.getMainInstance(player));
                }
                event.setCancelled(true);
            }

            if (title.contains(" - Items") || title.contains(" - Monster") || title.contains(" - Perks")){
                String jobn = title.split(" - ")[0];
                if ("zurück".equalsIgnoreCase(name)) {
                    for (Job job : Jobs.jobs){
                        if (job.getName().equalsIgnoreCase(jobn)){
                            player.openInventory(jobInventory.getJobPanel(job, player));
                        }
                    }
                } else if(isPerk(name)){
                    Perk perk = Jobs.hardcoded.getPerkByName(name);
                    JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), jobn);
                    if (jobPlayer != null){
                        player.openInventory(jobInventory.getPerkBuyGui(perk));
                    }else{
                        event.setCancelled(true);
                    }
                } else{
                    event.setCancelled(true);
                }
            }

            if (isPerk(title)){
                Perk perk = Jobs.hardcoded.getPerkByName(title);
                String job = perk.getJob();
                JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job);
                if (name.equalsIgnoreCase("zurück")){
                    player.openInventory(jobInventory.getJobPanel(Jobs.getJobByName(job), player));
                }
                if (jobPlayer != null){
                    if(name.equalsIgnoreCase("Perk kaufen")){
                        if (!player.hasPermission(perk.getPermission())){
                            if (jobPlayer.removePlvl(perk.getCost())){
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission set "+perk.getPermission());
                                event.setCancelled(true);
                            }else{
                                event.setCancelled(true);
                            }
                        }else{
                            event.setCancelled(true);
                        }
                    }else if(name.equalsIgnoreCase("Perk zurückgeben")){
                        if (player.hasPermission(perk.getPermission())){
                            if (player.hasPermission(perk.getPermission())){
                                jobPlayer.addPlvl(perk.getCost()-1);
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getName()+" permission unset "+perk.getPermission());
                                event.setCancelled(true);
                            }else{
                                event.setCancelled(true);
                            }
                        }else{
                            event.setCancelled(true);
                        }
                    } else if(name.equalsIgnoreCase("Perk aus/anschalten")){
                        if (player.hasPermission(perk.getPermission())){
                            if (perk.isDeactivated(player)){
                                perk.activate(player);
                                player.sendMessage(ChatColor.WHITE+perk.getName()+ ChatColor.DARK_GREEN +" angeschaltet!");
                            }else{
                                perk.deactivate(player);
                                player.sendMessage(ChatColor.WHITE+perk.getName()+ ChatColor.RED +" ausgeschaltet!");
                            }
                            event.setCancelled(true);
                        }else{
                            event.setCancelled(true);
                        }
                    }else{
                        event.setCancelled(true);
                    }
                }else{
                    event.setCancelled(true);
                }
            }

            /*
                Here handling the job inventories
             */

            if (titleIsJob(title) != null){
                Job job = titleIsJob(title);
                if (name.equalsIgnoreCase("Items")){
                    player.openInventory(jobInventory.getItemsInv(job));
                }
                else if (name.equalsIgnoreCase("Monster")){
                    player.openInventory(jobInventory.getMonsterInv(job));
                }
                else if (name.equalsIgnoreCase("Perks")){
                    JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());
                    if (jobPlayer != null){
                        player.openInventory(jobInventory.getPerkInventory(jobPlayer, job));
                    }else{
                        event.setCancelled(true);
                    }
                }
                else if (name.equalsIgnoreCase("zurück")){
                    jobInventory.getJobsPanel(player);
                }
                else {
                    event.setCancelled(true);
                }
            }
        }
    }

    public Job titleIsJob(String title){
        for (Job job : Jobs.jobs){
            if (job.getName().equalsIgnoreCase(title))
                return job;
        }
        return null;
    }

    public boolean isPerk(String name){
        return Jobs.hardcoded.getPerkByName(name) != null;
    }
}
