package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r")
    Set<Role> findAllRoles();
}