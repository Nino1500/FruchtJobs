package net.fruchtlabor.fruchtjobs.commands;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.inventories.JobInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            if ((command.getName().equalsIgnoreCase("jobs") || command.getName().equalsIgnoreCase("job")) && player != null){ // /job
                if (strings.length >= 2){
                    if (strings[0].equalsIgnoreCase("add") && player.hasPermission("Jobs.add")){
                        Player playerToExec = Bukkit.getPlayer(strings[1]);
                        if (playerToExec != null){
                            String jobname = strings[2];
                            Job job = Jobs.getJobByName(jobname);
                            if (job != null){
                                Jobs.DATABASEMANAGER.addPlayer(playerToExec.getUniqueId(), job.getName(), 1, 0.0, 1);
                                String msg = Jobs.plugin.getConfig().getString("Messages.jobjoin").replace("{job}", jobname);
                                playerToExec.sendMessage(msg);
                                player.sendMessage("Der job wurde vergeben, nice!");
                                return true;
                            }else {
                                player.sendMessage("Job gibts nicht!");
                                return false;
                            }
                        }else {
                            player.sendMessage("Spieler gibts nicht!");
                            return false;
                        }
                    }else if(strings[0].equalsIgnoreCase("remove") && player.hasPermission("Jobs.remove")){
                        Player playerToExec = Bukkit.getPlayer(strings[1]);
                        if (playerToExec != null){
                            String jobname = strings[2];
                            Job job = Jobs.getJobByName(jobname);
                            if (job != null){
                                Jobs.DATABASEMANAGER.removePlayer(playerToExec.getUniqueId(), job.getName());
                                String msg = Jobs.plugin.getConfig().getString("Messages.jobleave").replace("{job}", jobname);
                                playerToExec.sendMessage(msg);
                                return true;
                            }else {
                                player.sendMessage("Job gibts nicht!");
                                return false;
                            }
                        }else {
                            player.sendMessage("Spieler gibts nicht!");
                            return false;
                        }
                    }
                }
                if (strings.length == 0){
                    JobInventory jobInventory = new JobInventory();
                    player.openInventory(jobInventory.getMainInstance(player));
                }
            }
        }
        return false;
    }
}
