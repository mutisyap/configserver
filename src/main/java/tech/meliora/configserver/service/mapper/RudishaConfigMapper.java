package tech.meliora.configserver.service.mapper;


import tech.meliora.configserver.domain.*;
import tech.meliora.configserver.service.dto.RudishaConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RudishaConfig} and its DTO {@link RudishaConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface RudishaConfigMapper extends EntityMapper<RudishaConfigDTO, RudishaConfig> {

    @Mapping(source = "profile.id", target = "profileId")
    @Mapping(source = "profile.name", target = "profileName")
    RudishaConfigDTO toDto(RudishaConfig rudishaConfig);

    @Mapping(source = "profileId", target = "profile")
    RudishaConfig toEntity(RudishaConfigDTO rudishaConfigDTO);

    default RudishaConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        RudishaConfig rudishaConfig = new RudishaConfig();
        rudishaConfig.setId(id);
        return rudishaConfig;
    }
}
