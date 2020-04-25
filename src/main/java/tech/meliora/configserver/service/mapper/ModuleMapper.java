package tech.meliora.configserver.service.mapper;


import tech.meliora.configserver.domain.*;
import tech.meliora.configserver.service.dto.ModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Module} and its DTO {@link ModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApplicationMapper.class})
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {

    @Mapping(source = "application.id", target = "applicationId")
    @Mapping(source = "application.name", target = "applicationName")
    ModuleDTO toDto(Module module);

    @Mapping(source = "applicationId", target = "application")
    Module toEntity(ModuleDTO moduleDTO);

    default Module fromId(Long id) {
        if (id == null) {
            return null;
        }
        Module module = new Module();
        module.setId(id);
        return module;
    }
}
