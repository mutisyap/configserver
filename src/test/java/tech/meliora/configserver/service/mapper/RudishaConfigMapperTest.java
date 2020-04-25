package tech.meliora.configserver.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RudishaConfigMapperTest {

    private RudishaConfigMapper rudishaConfigMapper;

    @BeforeEach
    public void setUp() {
        rudishaConfigMapper = new RudishaConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rudishaConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rudishaConfigMapper.fromId(null)).isNull();
    }
}
