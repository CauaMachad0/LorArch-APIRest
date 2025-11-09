package com.lorarch.challenge.repository;

import com.lorarch.challenge.model.Role;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    @Query(value = "SELECT ID FROM RM558024.APP_ROLES WHERE NAME = :name",
            nativeQuery = true)
    Long findIdByName(@Param("name") String name);

    @Modifying
    @Query(value = """
        INSERT INTO RM558024.APP_USER_ROLES (USER_ID, ROLE_ID)
        VALUES (:userId, :roleId)
        """, nativeQuery = true)
    int addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
