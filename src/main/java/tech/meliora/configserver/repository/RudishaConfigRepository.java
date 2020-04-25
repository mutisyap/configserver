package tech.meliora.configserver.repository;

import tech.meliora.configserver.domain.RudishaConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RudishaConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RudishaConfigRepository extends JpaRepository<RudishaConfig, Long> {
}
