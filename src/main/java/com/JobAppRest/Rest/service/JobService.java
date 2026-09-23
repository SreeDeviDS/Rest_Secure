package com.JobAppRest.Rest.service;

import com.JobAppRest.Rest.JobPostNotFoundException;
import com.JobAppRest.Rest.model.JobPost;
import com.JobAppRest.Rest.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    @Autowired
    JobRepo repo;

    // called by controller -> repo
    public JobPost AddJob(JobPost jobpost){
        return repo.save(jobpost);
    }

    // called by controller -> repo
    public List<JobPost> AllJob(){
        return repo.findAll();
    }

    public JobPost JobPost(int Jobid) {
        return repo.findById(Jobid).orElseThrow(()->new JobPostNotFoundException("JobPost with id " +Jobid+ " not found"));
    }

    // called by controller -> repo
    public JobPost update(JobPost jobPost, int Jobid){
        JobPost existingjob = repo.findById(Jobid).orElseThrow(()->new JobPostNotFoundException("JobPost with id " +Jobid+ " not found"));
        existingjob.setPostProfile(jobPost.getPostProfile());
        existingjob.setPostDesc(jobPost.getPostDesc());
        existingjob.setReqExperience(jobPost.getReqExperience());
        existingjob.setPostTechStack(jobPost.getPostTechStack());
        return repo.save(existingjob);
    }

    public void delete(int Jobid) throws JobPostNotFoundException {
        if (repo.existsById(Jobid)) {
            repo.deleteById(Jobid);
        }
        else throw new JobPostNotFoundException("JobPost with id " + Jobid + " not found");
    }

    public void load() {
        // arrayList to store store JobPost objects
        List<JobPost> jobs =
                new ArrayList<>(List.of(
                        new JobPost("Software Engineer", "Exciting opportunity for a skilled software engineer.", 3, List.of("Java", "Spring", "SQL")),
                        new JobPost("Data Scientist", "Join our data science team and work on cutting-edge projects.", 5, List.of("Python", "Machine Learning", "TensorFlow")),
                        new JobPost("Frontend Developer", "Create amazing user interfaces with our talented frontend team.", 2, List.of("JavaScript", "React", "CSS")),
                        new JobPost("Network Engineer", "Design and maintain our robust network infrastructure.", 4, List.of("Cisco", "Routing", "Firewalls")),
                        new JobPost("UX Designer", "Shape the user experience with your creative design skills.", 3, List.of("UI/UX Design", "Adobe XD", "Prototyping"))

                ));
        repo.saveAll(jobs);
    }

    public List<JobPost> keyword(String keyword) {
        return repo.findByPostProfileContainingOrPostDescContaining(keyword,keyword);
    }

    public List<JobPost> experience(int exp) {
        return repo.findByreqExperienceLessThanEqual(exp);
    }
}
