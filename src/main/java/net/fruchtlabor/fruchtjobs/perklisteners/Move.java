package net.fruchtlabor.fruchtjobs.perklisteners;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Move implements Listener {

    ArrayList<Player> players = new ArrayList<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event){

        if (!Jobs.canBuild(event.getPlayer(), event.getPlayer().getLocation())){
            return;
        }

        Location location = event.getFrom();

        Player player = event.getPlayer();

        Perk perk = Jobs.hardcoded.getPerkByName("Nachtsicht");

        if (player.hasPermission(perk.getPermission())){

            if (location.getY() < 35){

                JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(event.getPlayer().getUniqueId(), "Miner");

                if (jobPlayer != null){

                    if (!perk.isDeactivated(player)){
                        players.add(player);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1, Integer.MAX_VALUE));
                    }

                }

            }else{

                if (players.contains(player) || perk.isDeactivated(player)){
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                }

            }

        }
    }
}
