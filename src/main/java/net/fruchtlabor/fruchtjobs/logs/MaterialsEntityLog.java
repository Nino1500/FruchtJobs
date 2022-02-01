package net.fruchtlabor.fruchtjobs.logs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import org.bukkit.entity.EntityType;

public class MaterialsEntityLog {
    private EntityType entityType;
    private Job job;
    private double experience;
    private MaterialType type;
    private int atLvl;

    public MaterialsEntityLog(EntityType entityType, Job job, double experience, MaterialType type, int atLvl) {
        this.entityType = entityType;
        this.job = job;
        this.experience = experience;
        this.type = type;
        this.atLvl = atLvl;
    }

    public EntityType getEntityType() {
        return entityType;
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

    public int getAtLvl() {
        return atLvl;
    }

    @Override
    public String toString() {
        return "MaterialsEntityLog{" +
                "entityType=" + entityType +
                ", job=" + job +
                ", experience=" + experience +
                ", type=" + type +
                ", atLvl=" + atLvl +
                '}';
    }
}
