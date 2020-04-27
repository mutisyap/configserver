package tech.meliora.configserver.repository;

import tech.meliora.configserver.domain.Profile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByNameAndModuleId(String name, Long moduleId);
}
