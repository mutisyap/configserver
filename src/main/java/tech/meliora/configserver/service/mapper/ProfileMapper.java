package tech.meliora.configserver.service.mapper;


import tech.meliora.configserver.domain.*;
import tech.meliora.configserver.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.name", target = "moduleName")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "moduleId", target = "module")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
