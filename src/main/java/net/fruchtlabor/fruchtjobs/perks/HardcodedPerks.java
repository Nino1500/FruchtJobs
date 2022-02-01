package net.fruchtlabor.fruchtjobs.perks;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HardcodedPerks {

    public HashMap<Job, ArrayList<Perk>> perkMap;

    public HardcodedPerks() {
        perkMap = getPerkMap();
    }

    private ArrayList<Perk> getPerks(String jobname){
        ArrayList<Perk> perks = new ArrayList<>();
        switch (jobname){
            case "Farmer":
                perks.add(new Perk("perks.Farmer.01", "Goldig", 9, "Goldkarotten/Äpfel können droppen!", Material.GOLDEN_APPLE, "Farmer"));
                perks.add(new Perk("perks.Farmer.02", "Sense aus Stahl", 9, "Verringerter Durability-Verlust!", Material.IRON_HOE, "Farmer"));
                perks.add(new Perk("perks.Farmer.03", "Metalldetektor", 10, "Kann Kupfer, Eisen und Goldnuggets finden!", Material.IRON_SHOVEL, "Farmer"));
                perks.add(new Perk("perks.Farmer.04", "Erfahrener Sammler", 5, "Mehr JobExp!", Material.EXPERIENCE_BOTTLE, "Farmer"));
                perks.add(new Perk("perks.Farmer.05", "Doppeldrop", 12, "Crops droppen evtl. doppelt!", Material.OAK_SIGN, "Farmer"));
                perks.add(new Perk("perks.Farmer.06", "Was ist Hunger?", 20, "Du hast weniger Hunger!", Material.PORKCHOP, "Farmer"));
                perks.add(new Perk("perks.Farmer.07", "Rezept: Grasblock", 15, "Rezept um Grasblöcke zu craften!", Material.GRASS_BLOCK, "Farmer"));
                perks.add(new Perk("perks.Farmer.08", "Grasernte", 25, "Witherrosen, großes Gras, etc.!", Material.WITHER_ROSE, "Farmer"));
                perks.add(new Perk("perks.Farmer.09", "Rezept: Farmland", 15, "Rezept um Farmland zu craften!", Material.FARMLAND, "Farmer"));
                perks.add(new Perk("perks.Farmer.10", "Erfahrungssammler", 17, "Felder droppen manchmal EXP!", Material.EXPERIENCE_BOTTLE, "Farmer"));
                break;
            case "Fischer":
                perks.add(new Perk("perks.Fischer.01", "Rüstungsfischer", 10, "Kann Rüstungen fischen!", Material.GOLDEN_CHESTPLATE, "Fischer"));
                perks.add(new Perk("perks.Fischer.02", "Mending", 20, "Chance auf ein Mendingbuch!", Material.ENCHANTED_BOOK, "Fischer"));
                perks.add(new Perk("perks.Fischer.03", "Werkzeugfischer",15, "Fischt evtl. Werkzeug!", Material.IRON_PICKAXE, "Fischer"));
                perks.add(new Perk("perks.Fischer.04", "Waffenfischer", 10, "Fischt evtl. Waffen!", Material.STONE_SWORD, "Fischer"));
                perks.add(new Perk("perks.Fischer.05", "Erzfischer", 15, "Fischt evtl. Erze!", Material.IRON_NUGGET, "Fischer"));
                perks.add(new Perk("perks.Fischer.06", "Eierfischer", 25, "Fischt evtl. Spawneier!", Material.BEE_SPAWN_EGG, "Fischer"));
                perks.add(new Perk("perks.Fischer.07", "Bücherfischer", 15, "Fischt evtl. Verz. Bücher!", Material.ENCHANTED_BOOK, "Fischer"));
                perks.add(new Perk("perks.Fischer.08", "Kopffischer", 30, "Fischt evtl. Tierköpfe!", Material.PLAYER_HEAD, "Fischer"));
                perks.add(new Perk("perks.Fischer.09", "Geschickter Angler", 15, "Fischt mittlere Fische!", Material.FISHING_ROD, "Fischer"));
                perks.add(new Perk("perks.Fischer.10", "Meister Angler", 30, "Fischt die größten Fische!", Material.CARROT_ON_A_STICK, "Fischer"));
                break;
            case "Foerster":
                perks.add(new Perk("perks.Foerster.01", "Timber", 15, "Timber für kleine Bäume!", Material.IRON_AXE, "Foerster"));
                perks.add(new Perk("perks.Foerster.02", "Mega Timber", 50, "Für große Bäume! (benötigt Timber)", Material.GOLDEN_AXE, "Foerster"));
                perks.add(new Perk("perks.Foerster.03", "Doppeldrop Baumstämme", 25, "Droppt Stämme evtl. doppelt!", Material.NETHERITE_AXE, "Foerster"));
                perks.add(new Perk("perks.Foerster.04", "Laubbläser", 5, "Blätter onehitten, evtl. Goldäpfel!", Material.HOPPER, "Foerster"));
                perks.add(new Perk("perks.Foerster.05", "Schäfer", 18, "Schafe droppen mehr Wolle!", Material.SHEARS, "Foerster"));
                perks.add(new Perk("perks.Foerster.06", "Doppeldrop Viecher", 15, "Droppt Viecher Items evtl. doppelt!", Material.LEATHER, "Foerster"));
                perks.add(new Perk("perks.Foerster.07", "Erfahrungsbonus Viecher", 10, "Viecher droppen mehr Erfahrung!", Material.EXPERIENCE_BOTTLE, "Foerster"));
                break;
            case "Jaeger":
                perks.add(new Perk("perks.Jaeger.01", "Doppeldrop", 10, "Monster droppen evtl. doppelt!", Material.HOPPER, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.02", "Crit I (Schwert)", 5, "+5%", Material.WOODEN_SWORD, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.03", "Crit II (Schwert)", 8, "+8%", Material.STONE_SWORD, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.04", "Crit III (Schwert)", 12, "+12%", Material.IRON_SWORD, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.05", "Kopfjäger", 25, "Chance auf einen MobKopf!", Material.PLAYER_HEAD, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.06", "Tödlicher Schuss (Bogen)", 10, "Chance einen Mob zu onehitten!", Material.BOW, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.07", "Explosiver Schuss (Bogen)", 10, "Chance TNT auf das Ziel zu schießen!", Material.TNT, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.08", "Teleport Schuss (Bogen)", 10, "Teleportiert zum Ziel (Monster)!", Material.ENDER_PEARL, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.09", "Ausbluten (Schwert)", 10, "Schaden über Zeit!", Material.RED_DYE, "Jaeger"));
                perks.add(new Perk("perks.Jaeger.10", "Verlangsamen (Schwert & Bogen)", 10, "Verlangsamt Gegner!", Material.CHAINMAIL_BOOTS, "Jaeger"));
                break;
            case "Miner":
                perks.add(new Perk("perks.Miner.01", "Taschenofen", 12, "Schmilzt Erz direkt!", Material.IRON_INGOT, "Miner"));
                perks.add(new Perk("perks.Miner.02", "Tunnelgräber", 25, "Baut in der Farmwelt 2x1 ab (Stein)!", Material.OAK_DOOR, "Miner"));
                perks.add(new Perk("perks.Miner.03", "Glück des Miners", 20, "Erz kann anderes Erz droppen!", Material.HOPPER_MINECART, "Miner"));
                perks.add(new Perk("perks.Miner.04", "Nachtsicht", 8, "Nachtsicht in der Mine!", Material.TORCH, "Miner"));
                perks.add(new Perk("perks.Miner.05", "Doppeldrop Erze", 15, "Erze können doppelt droppen!", Material.HOPPER, "Miner"));
                perks.add(new Perk("perks.Miner.06", "Steintouch", 10, "Cobble wird als Stein gedroppt!", Material.STONE, "Miner"));
                perks.add(new Perk("perks.Miner.07", "Joberfahrung", 15, "Bei Erzen (5%) (5<->JobLevel+5)", Material.EXPERIENCE_BOTTLE, "Miner"));
                perks.add(new Perk("perks.Miner.08", "Instant", 15, "Erze werden direkt abgebaut!", Material.NETHERITE_PICKAXE, "Miner"));
                break;
            case "Schatzsucher":
                perks.add(new Perk("perks.Schatzsucher.01", "Verborgene Schätze I", 10, "Beim Abbau kann ein Schatz erscheinen!", Material.CHEST, "Schatzsucher"));
                perks.add(new Perk("perks.Schatzsucher.02", "Verborgene Schätze II", 20, "Andere Items (zusätzlich oder seperat)", Material.TRAPPED_CHEST, "Schatzsucher"));
                perks.add(new Perk("perks.Schatzsucher.03", "Verborgene Schätze III", 30, "Andere Items (zusätzlich oder seperat)", Material.GOLDEN_SHOVEL, "Schatzsucher"));
                perks.add(new Perk("perks.Schatzsucher.04", "Erfahrungssucher", 7, "Beim Abbau dropped manchmal Erfahrung!", Material.EXPERIENCE_BOTTLE, "Schatzsucher"));
                perks.add(new Perk("perks.Schatzsucher.05", "Glasmacher", 12, "Statt Sand wird Glas gedropped!", Material.GLASS, "Schatzsucher"));
                perks.add(new Perk("perks.Schatzsucher.06", "Steinbäcker", 15, "Clay wird direkt zu Ziegeln!", Material.BRICKS, "Schatzsucher"));
                break;
            case "Verzauberer":
                perks.add(new Perk("perks.Verzauberer.01", "Erfahrener Zauberer", 15, "EXP werden manchmal nicht verbraucht!", Material.ENCHANTED_BOOK, "Verzauberer"));
                perks.add(new Perk("perks.Verzauberer.02", "Doppeldrop verzaubern", 30, "Sehr kleine Chance Buch zu duplizieren!", Material.HOPPER, "Verzauberer"));
                perks.add(new Perk("perks.Verzauberer.03", "Rezept: Flugtrank I", 10, "Trank zum Fliegen 5m", Material.GLASS_BOTTLE, "Verzauberer"));
                perks.add(new Perk("perks.Verzauberer.04", "Rezept: Flugtrank II", 40, "Trank zum Fliegen 10m", Material.HONEY_BOTTLE, "Verzauberer"));
                perks.add(new Perk("perks.Verzauberer.05", "Glück des Verzauberns", 35, "Kann manchmal die Verzauberung erhöhen!", Material.ENCHANTING_TABLE, "Verzauberer"));
                perks.add(new Perk("perks.Verzauberer.06", "Entzaubern", 50, "Kann mit gewisser Chance (Tools) entzaubern!", Material.ANVIL, "Verzauberer"));
                break;
        }
        return perks;
    }

    public Perk getPerkByName(String name){
        for (Map.Entry<Job, ArrayList<Perk>> entry : perkMap.entrySet()){
            for (Perk perk : entry.getValue()){
                if(perk.getName().equalsIgnoreCase(name)){
                    return perk;
                }
            }
        }
        return null;
    }

    public ArrayList<Perk> getPerksByJob(String job){
        for (Map.Entry<Job, ArrayList<Perk>> entry : perkMap.entrySet()){
            if (entry.getKey().getName().equalsIgnoreCase(job)){
                return entry.getValue();
            }
        }
        return null;
    }

    private HashMap<Job, ArrayList<Perk>> getPerkMap() {
        HashMap<Job, ArrayList<Perk>> map = new HashMap<>();
        for (Job job : Jobs.jobs){
            map.put(job, getPerks(job.getName()));
        }
        return map;
    }
}
