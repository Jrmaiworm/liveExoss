package br.com.jrmaiworm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jrmaiworm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estacao.class);
        Estacao estacao1 = new Estacao();
        estacao1.setId(1L);
        Estacao estacao2 = new Estacao();
        estacao2.setId(estacao1.getId());
        assertThat(estacao1).isEqualTo(estacao2);
        estacao2.setId(2L);
        assertThat(estacao1).isNotEqualTo(estacao2);
        estacao1.setId(null);
        assertThat(estacao1).isNotEqualTo(estacao2);
    }
}
