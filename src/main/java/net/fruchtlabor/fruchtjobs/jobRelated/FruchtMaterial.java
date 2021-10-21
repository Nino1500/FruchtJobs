package net.fruchtlabor.fruchtjobs.jobRelated;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class FruchtMaterial {
    public Material material;
    public double exp;
    public int atLvl;
    public int enchantmentLvl;
    public Enchantment enchantment;

    public FruchtMaterial(Material material, double exp, int atLvl) {
        this.material = material;
        this.exp = exp;
        this.atLvl = atLvl;
    }

    public FruchtMaterial(Material material, double exp, int atLvl, Enchantment enchantment, int enchantmentLvl) {
        this.material = material;
        this.exp = exp;
        this.atLvl = atLvl;
        this.enchantmentLvl = enchantmentLvl;
        this.enchantment = enchantment;
    }

    public Material getMaterial() {
        return material;
    }

    public double getExp() {
        return exp;
    }

    public int getAtLvl() {
        return atLvl;
    }

    public int getEnchantmentLvl() {
        return enchantmentLvl;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }
}
