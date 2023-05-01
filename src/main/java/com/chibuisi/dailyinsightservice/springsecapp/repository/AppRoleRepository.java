package com.chibuisi.dailyinsightservice.springsecapp.repository;

import com.chibuisi.dailyinsightservice.springsecapp.model.AppRole;
import com.chibuisi.dailyinsightservice.springsecapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findAppRoleByRoleName(Role roleName);
}
