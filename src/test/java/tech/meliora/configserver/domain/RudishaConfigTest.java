package tech.meliora.configserver.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import tech.meliora.configserver.web.rest.TestUtil;

public class RudishaConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RudishaConfig.class);
        RudishaConfig rudishaConfig1 = new RudishaConfig();
        rudishaConfig1.setId(1L);
        RudishaConfig rudishaConfig2 = new RudishaConfig();
        rudishaConfig2.setId(rudishaConfig1.getId());
        assertThat(rudishaConfig1).isEqualTo(rudishaConfig2);
        rudishaConfig2.setId(2L);
        assertThat(rudishaConfig1).isNotEqualTo(rudishaConfig2);
        rudishaConfig1.setId(null);
        assertThat(rudishaConfig1).isNotEqualTo(rudishaConfig2);
    }
}
