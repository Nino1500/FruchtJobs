package net.fruchtlabor.fruchtjobs.jobRelated;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class FruchtMonster{

    public EntityType entity;
    public double exp;
    public int atLvl;


    public FruchtMonster(EntityType entity, double exp, int atLvl) {
        this.entity = entity;
        this.exp = exp;
        this.atLvl = atLvl;
    }

    public EntityType getEntity() {
        return entity;
    }

    public double getExp() {
        return exp;
    }

    public int getAtLvl() {
        return atLvl;
    }
}
