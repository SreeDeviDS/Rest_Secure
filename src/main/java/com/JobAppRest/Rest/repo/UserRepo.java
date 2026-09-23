package com.JobAppRest.Rest.repo;

import com.JobAppRest.Rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}
