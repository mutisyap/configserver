package tech.meliora.configserver.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.meliora.configserver.web.rest.TestUtil;

public class RudishaConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RudishaConfigDTO.class);
        RudishaConfigDTO rudishaConfigDTO1 = new RudishaConfigDTO();
        rudishaConfigDTO1.setId(1L);
        RudishaConfigDTO rudishaConfigDTO2 = new RudishaConfigDTO();
        assertThat(rudishaConfigDTO1).isNotEqualTo(rudishaConfigDTO2);
        rudishaConfigDTO2.setId(rudishaConfigDTO1.getId());
        assertThat(rudishaConfigDTO1).isEqualTo(rudishaConfigDTO2);
        rudishaConfigDTO2.setId(2L);
        assertThat(rudishaConfigDTO1).isNotEqualTo(rudishaConfigDTO2);
        rudishaConfigDTO1.setId(null);
        assertThat(rudishaConfigDTO1).isNotEqualTo(rudishaConfigDTO2);
    }
}
