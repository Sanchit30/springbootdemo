package com.anaadih.aclassdeal.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.Role;
import com.anaadih.aclassdeal.Model.RoleName;

@Repository
public interface RolesRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByName(RoleName roleName);
}
