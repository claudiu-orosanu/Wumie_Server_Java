package com.claudiuorosanu.Wumie.repository;

import com.claudiuorosanu.Wumie.model.Role;
import com.claudiuorosanu.Wumie.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);

}
