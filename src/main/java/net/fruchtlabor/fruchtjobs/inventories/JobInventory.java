package net.fruchtlabor.fruchtjobs.inventories;

import net.fruchtlabor.fruchtjobs.Jobs;
import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.JobPlayer;
import net.fruchtlabor.fruchtjobs.logs.MaterialsEntityLog;
import net.fruchtlabor.fruchtjobs.logs.MaterialsLog;
import net.fruchtlabor.fruchtjobs.perks.Perk;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class JobInventory {

    private final ItemStack holder = createItem(" ", "", Material.GRAY_STAINED_GLASS_PANE);

    public JobInventory() {
    }

    public Inventory getMainInstance(Player player){
        Inventory inventory = circle(Bukkit.createInventory(null,  27, "Jobs"));
        inventory.setItem(11, createItem("Jobs", "Hier sind alle - Jobs aufgelistet.", Material.BOOK));
        inventory.setItem(13, createItem("BossBar", "Hier, die BossBar deaktivieren.", Material.MAP));
        inventory.setItem(15, createItem("Statistiken", "Leitet dich auf - die Homepage weiter.", Material.REDSTONE));
        return inventory;
    }

    public void getJobsPanel(Player player){
        Inventory inventory = circle(Bukkit.createInventory(null, 27, "JobsPanel"));
        for (Job job : Jobs.jobs) {
            if (Jobs.DATABASEMANAGER.doesHaveJob(job, player)){
                inventory.addItem(addGlow(createItem(job.getName(), job.getDescription(), job.getGui())));
            }else{
                inventory.addItem(createItem(job.getName(), job.getDescription(), job.getGui()));
            }
        }
        inventory.setItem(18, createItem("zurück", "<- Jobs", Material.BARRIER));
        player.openInventory(inventory);
    }

    public static ItemStack addGlow(ItemStack item){
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public Inventory getJobPanel(Job job, Player player){
        JobPlayer jobPlayer = Jobs.DATABASEMANAGER.getByUUID(player.getUniqueId(), job.getName());
        Inventory inventory = circle(Bukkit.createInventory(null, 27, job.getName()));
        inventory.setItem(11, createItem("Items", "Die items - die du - farmen kannst.", Material.BOOK));
        inventory.setItem(13, createItem("Monster", "Die Monster - die du - töten kannst.", Material.ZOMBIE_HEAD));
        inventory.setItem(15, createItem("Perks", "Hier kannst du Perks - kaufen oder zurückgeben!", Material.BELL));
        inventory.setItem(18, createItem("zurück", "<- Jobs", Material.BARRIER));
        if (jobPlayer != null){
            inventory.setItem(0, addGlow(createItem("Stats", "Level: "+jobPlayer.lvl+"- PerkLevel: "+jobPlayer.getPlvl(), Material.OAK_SIGN)));
        }
        return inventory;
    }

    public Inventory getItemsInv(Job job){
        if (job.getName().equalsIgnoreCase("Verzauberer")){
            Inventory inventory = onlyTopAndBottom(Bukkit.createInventory(null, 54, job.getName() + " - Items"));
            for (MaterialsLog material : job.getItems()){
                inventory.addItem(createItem(material.getMaterial().name(), "Enchantment: "+material.getEnchantment().getKey().toString().split(":")[1]+" - Exp: "+material.getExperience()+" - Ab level: "+material.getAtLevel(), material.getMaterial()));
            }
            inventory.setItem(45, createItem("zurück", "<- Job", Material.BARRIER));
            return inventory;
        }else{
            Inventory inventory = circle(Bukkit.createInventory(null, 54, job.getName() + " - Items"));
            for (MaterialsLog material : job.getItems()){
                inventory.addItem(createItem(material.getMaterial().name(), "Exp: "+material.getExperience()+" - Ab level: "+material.getAtLevel(), material.getMaterial()));
            }
            inventory.setItem(45, createItem("zurück", "<- Job", Material.BARRIER));
            return inventory;
        }
    }

    public Inventory getMonsterInv(Job job){
        Inventory inventory = circle(Bukkit.createInventory(null, 54, job.getName() + " - Monster"));
        for (MaterialsEntityLog monster : job.getMonster()){
            ItemStack itemStack = Jobs.getHead(monster.getEntityType()).getHead();
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(monster.getEntityType().name());
            ArrayList<String> lore = new ArrayList<>();
            lore.add("Exp: "+monster.getExperience());
            lore.add("Ab level: "+monster.getAtLvl());
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            inventory.addItem(itemStack);
        }
        inventory.setItem(45, createItem("zurück", "<- Job", Material.BARRIER));
        return inventory;
    }

    public ItemStack createItem(String name, String lore, Material material){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null){
            meta.setDisplayName(name);
            ArrayList<String> newlore = new ArrayList<>(Arrays.asList(lore.split("-")));
            meta.setLore(newlore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public Inventory getPerkInventory(JobPlayer jobPlayer, Job job){
        Inventory inventory = circle(Bukkit.createInventory(null, 54, job.getName() + " - Perks"));
        for (Perk perk : Jobs.hardcoded.getPerksByJob(job.getName())){
            if (Bukkit.getPlayer(jobPlayer.uuid).hasPermission(perk.getPermission())){
                inventory.addItem(addGlow(createItem(perk.getName(), perk.getDescription()+"-Punkte: "+perk.getCost(), perk.getGui())));
            }else{
                inventory.addItem(createItem(perk.getName(), perk.getDescription()+"-Punkte: "+perk.getCost(), perk.getGui()));
            }
        }
        inventory.setItem(45, createItem("zurück", "<- Job", Material.BARRIER));
        return inventory;
    }

    public Inventory getPerkBuyGui(Perk perk){
        Inventory inventory = circle(Bukkit.createInventory(null, 27, perk.getName()));
        inventory.setItem(11, createItem("Perk kaufen", perk.getCost()+" Punkte", Material.GREEN_WOOL));
        inventory.setItem(13, createItem("Perk zurückgeben", perk.getCost()-1+" Punkte", Material.RED_WOOL));
        inventory.setItem(15, createItem("Perk aus/anschalten", "Schalte den Perk aus - (bis du neu einlogst)", Material.GRAY_WOOL));
        inventory.setItem(18, createItem("zurück", "<- Jobs", Material.BARRIER));
        return inventory;
    }

    public Inventory circle(Inventory inventory){
        switch (inventory.getSize()){
            case 9:
            case 18:
                return inventory;
            case 27:
                for (int i = 0; i < 9; i++) {
                    inventory.setItem(i, holder);
                }
                inventory.setItem(9, holder);
                inventory.setItem(17, holder);
                for (int i = 18; i < inventory.getSize(); i++) {
                    inventory.setItem(i, holder);
                }
                return inventory;
            case 36:
                for (int i = 0; i < 9; i++) {
                    inventory.setItem(i, holder);
                }
                inventory.setItem(9, holder);
                inventory.setItem(18, holder);
                inventory.setItem(17, holder);
                inventory.setItem(26, holder);
                for (int i = 27; i < inventory.getSize(); i++) {
                    inventory.setItem(i, holder);
                }
                return inventory;
            case 45:
                for (int i = 0; i < 9; i++) {
                    inventory.setItem(i, holder);
                }
                inventory.setItem(9, holder);
                inventory.setItem(18, holder);
                inventory.setItem(17, holder);
                inventory.setItem(26, holder);
                inventory.setItem(27, holder);
                inventory.setItem(35, holder);
                for (int i = 36; i < inventory.getSize(); i++) {
                    inventory.setItem(i, holder);
                }
                return inventory;
            case 54:
                for (int i = 0; i < 9; i++) {
                    inventory.setItem(i, holder);
                }
                inventory.setItem(9, holder);
                inventory.setItem(18, holder);
                inventory.setItem(17, holder);
                inventory.setItem(26, holder);
                inventory.setItem(27, holder);
                inventory.setItem(35, holder);
                inventory.setItem(36, holder);
                inventory.setItem(44, holder);
                for (int i = 45; i < inventory.getSize(); i++) {
                    inventory.setItem(i, holder);
                }
                return inventory;
        }
        return inventory;
    }
    public Inventory onlyTopAndBottom(Inventory inventory){
        if (inventory.getSize()>=54){
            for (int i = 0; i < 9; i++) {
                inventory.setItem(i, holder);
            }
            for (int i = 45; i < inventory.getSize(); i++) {
                inventory.setItem(i, holder);
            }
        }
        return inventory;
    }


}
