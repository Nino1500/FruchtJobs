package net.fruchtlabor.fruchtjobs.codedJobs;

import net.fruchtlabor.fruchtjobs.abstracts.Job;
import net.fruchtlabor.fruchtjobs.jobRelated.JobFactory;

import java.util.ArrayList;

public class CodedJobs {

    public ArrayList<Job> getJobs(){
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(JobFactory.getJob("Farmer"));
        jobs.add(JobFactory.getJob("Fischer"));
        jobs.add(JobFactory.getJob("Foerster"));
        //jobs.add(JobFactory.getJob("Jaeger"));
        jobs.add(JobFactory.getJob("Miner"));
        jobs.add(JobFactory.getJob("Schatzsucher"));
        jobs.add(JobFactory.getJob("Verzauberer"));
        return jobs;
    }
}
