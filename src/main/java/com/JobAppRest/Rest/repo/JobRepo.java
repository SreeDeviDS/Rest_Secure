package com.JobAppRest.Rest.repo;

import com.JobAppRest.Rest.model.JobPost;
import com.JobAppRest.Rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<JobPost,Integer> {

    public List<JobPost> findByPostProfileContainingOrPostDescContaining(String keyword,String keyword1);

    public List<JobPost> findByreqExperienceLessThanEqual(int experience);
}

