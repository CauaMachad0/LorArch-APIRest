package com.lorarch.challenge.repository;

import com.lorarch.challenge.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = """
        INSERT INTO RM558024.APP_USERS (USERNAME, PASSWORD, ENABLED)
        VALUES (:username, :password, :enabled)
        """, nativeQuery = true)
    int insertUserNative(@Param("username") String username,
                         @Param("password") String password,
                         @Param("enabled") int enabled);

    @Query(value = "SELECT ID FROM RM558024.APP_USERS WHERE USERNAME = :username",
            nativeQuery = true)
    Long findIdByUsername(@Param("username") String username);
}
