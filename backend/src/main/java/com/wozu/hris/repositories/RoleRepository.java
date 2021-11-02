package com.wozu.hris.repositories;

import com.wozu.hris.models.ERole;
import com.wozu.hris.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();

    Role findByName(ERole name);
}
