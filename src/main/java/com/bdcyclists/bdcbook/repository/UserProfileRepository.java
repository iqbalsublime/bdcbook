package com.bdcyclists.bdcbook.repository;

import com.bdcyclists.bdcbook.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author A N M Bazlur Rahman
 * @since 1/27/15.
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
