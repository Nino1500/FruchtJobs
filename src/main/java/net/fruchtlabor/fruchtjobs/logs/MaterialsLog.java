package net.fruchtlabor.fruchtjobs.logs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class MaterialsLog {
    private Material material;
    private Job job;
    private double experience;
    private MaterialType type;
    private int atLevel;
    private Enchantment enchantment;
    private int enchant_level;

    public MaterialsLog(Material material, Job job, double experience, MaterialType type, int atLevel, Enchantment enchantment, int enchant_level) {
        this.material = material;
        this.job = job;
        this.experience = experience;
        this.type = type;
        this.atLevel = atLevel;
        this.enchantment = enchantment;
        this.enchant_level = enchant_level;
    }

    public MaterialsLog(Material material, Job job, double experience, MaterialType type, int atLevel) {
        this.material = material;
        this.job = job;
        this.experience = experience;
        this.type = type;
        this.atLevel = atLevel;
    }

    public Material getMaterial() {
        return material;
    }

    public Job getJob() {
        return job;
    }

    public double getExperience() {
        return experience;
    }

    public MaterialType getType() {
        return type;
    }

    public int getAtLevel() {
        return atLevel;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getEnchant_level() {
        return enchant_level;
    }

    @Override
    public String toString() {
        return "MaterialsLog{" +
                "material=" + material +
                ", job=" + job +
                ", experience=" + experience +
                ", type=" + type +
                ", atLevel=" + atLevel +
                ", enchantment=" + enchantment +
                ", enchant_level=" + enchant_level +
                '}';
    }
}
