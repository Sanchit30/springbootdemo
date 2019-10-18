package com.anaadih.aclassdeal.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 
	 Optional<User> findByEmail(String email);

	// Optional<User> findByUsernameOrEmail(String username, String email);

	 List<User> findByIdIn(List<Long> userIds);

	 Optional<User> findByName(String username);

	 //Boolean existsByUsername(String username);

     Boolean existsByEmail(String email);

	Optional<User> findByIdAndBlocked(Long id, boolean b);

	List<User> findByUserType(String type, Pageable pg);

	List<User> findByUserTypeAndDeactivated(String string, boolean b, Pageable pg);

	long countByDeactivated(boolean b);
     

}
