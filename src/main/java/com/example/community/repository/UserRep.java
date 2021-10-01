package com.example.community.repository;

import com.example.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends JpaRepository<User, Long> {
    //User findByAccount(Long accountId);
    boolean existsByaccountId(String account_id);
    User findByaccountId(String account_id);
}
