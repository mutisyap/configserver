package tech.meliora.configserver.repository;

import tech.meliora.configserver.domain.Module;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Module entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    Module findByNameAndApplicationId(String name, Long appId);
}
