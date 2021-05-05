package com.vladislav.bashirov.checkpoint.system.repository;

import com.vladislav.bashirov.checkpoint.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Transactional
    @Modifying
    @Query(
            value = "insert into User (key_id, login) values (:keyId, :login)",
            nativeQuery = true
          )
    void addUser(Long keyId, String login);
}
