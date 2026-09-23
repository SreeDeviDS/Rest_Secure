package com.JobAppRest.Rest;

import com.JobAppRest.Rest.model.JobPost;
import com.JobAppRest.Rest.model.Role;
import com.JobAppRest.Rest.model.User;
import com.JobAppRest.Rest.service.JobService;
import com.JobAppRest.Rest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    JobService jobservice;

    @Autowired
    UserService userservice;

    //@ResponseBody
    //fetch all
    @GetMapping("JobPosts")
    public ResponseEntity<List<JobPost>> AllJob(){
        List<JobPost> jobs = jobservice.AllJob();
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    //fetch specific id
    @GetMapping("JobPost/{Jobid}")
    public ResponseEntity<JobPost> JobPost(@PathVariable("Jobid") int Jobid){
        JobPost job = jobservice.JobPost(Jobid);
        return new ResponseEntity<>(job,HttpStatus.OK);
    }

    @PostMapping("JobPost")
    public ResponseEntity<JobPost> addjob(@Valid @RequestBody JobPost jobPost) {
        return new ResponseEntity<>(jobservice.AddJob(jobPost), HttpStatus.CREATED);
    }

    @PutMapping("JobPost/{Jobid}")
    public ResponseEntity<JobPost> update(@Valid @RequestBody JobPost jobPost,@PathVariable int Jobid){
        JobPost job = jobservice.update(jobPost,Jobid);
        return new ResponseEntity<>(job,HttpStatus.OK);
    }

    @DeleteMapping("JobPost/{Jobid}")
    public ResponseEntity<String> delete(@PathVariable("Jobid") int Jobid){
        jobservice.delete(Jobid);
        return new ResponseEntity<>("JobPost deleted successfully",HttpStatus.OK);
    }

    @PostMapping("JobPost/load")
    public ResponseEntity<String> load(){
        jobservice.load();
        return new ResponseEntity<>("All Rows Inserted",HttpStatus.OK);
    }

    @GetMapping("JobPost/keyword/{keyword}")
    public ResponseEntity<List<JobPost>> Keyword (@PathVariable("keyword") String keyword){
        List<JobPost> jobs = jobservice.keyword(keyword);
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    @GetMapping("JobPost/experience/{exp}")
    public List<JobPost> experience (@PathVariable("exp") int exp){
        List<JobPost> jobs = jobservice.experience(exp);
        return jobs;
    }

    /*@GetMapping("token")
    public CsrfToken token(HttpServletRequest req){
        return (CsrfToken) req.getAttribute("_csrf");
    }*/

    @PostMapping("register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        user.setRole(Role.USER);   // ignore whatever role the client sent — always USER
        return new ResponseEntity<>(userservice.saveUser(user),HttpStatus.CREATED);
    }


}
