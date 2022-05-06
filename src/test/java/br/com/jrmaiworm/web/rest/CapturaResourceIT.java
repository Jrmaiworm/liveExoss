package br.com.jrmaiworm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jrmaiworm.IntegrationTest;
import br.com.jrmaiworm.domain.Captura;
import br.com.jrmaiworm.repository.CapturaRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CapturaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CapturaResourceIT {

    private static final byte[] DEFAULT_IMAGEM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEM_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/capturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CapturaRepository capturaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCapturaMockMvc;

    private Captura captura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Captura createEntity(EntityManager em) {
        Captura captura = new Captura()
            .imagem(DEFAULT_IMAGEM)
            .imagemContentType(DEFAULT_IMAGEM_CONTENT_TYPE)
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO)
            .status(DEFAULT_STATUS)
            .video(DEFAULT_VIDEO);
        return captura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Captura createUpdatedEntity(EntityManager em) {
        Captura captura = new Captura()
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .video(UPDATED_VIDEO);
        return captura;
    }

    @BeforeEach
    public void initTest() {
        captura = createEntity(em);
    }

    @Test
    @Transactional
    void createCaptura() throws Exception {
        int databaseSizeBeforeCreate = capturaRepository.findAll().size();
        // Create the Captura
        restCapturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captura)))
            .andExpect(status().isCreated());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeCreate + 1);
        Captura testCaptura = capturaList.get(capturaList.size() - 1);
        assertThat(testCaptura.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testCaptura.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testCaptura.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCaptura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCaptura.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCaptura.getVideo()).isEqualTo(DEFAULT_VIDEO);
    }

    @Test
    @Transactional
    void createCapturaWithExistingId() throws Exception {
        // Create the Captura with an existing ID
        captura.setId(1L);

        int databaseSizeBeforeCreate = capturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captura)))
            .andExpect(status().isBadRequest());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCapturas() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        // Get all the capturaList
        restCapturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(captura.getId().intValue())))
            .andExpect(jsonPath("$.[*].imagemContentType").value(hasItem(DEFAULT_IMAGEM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEM))))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)));
    }

    @Test
    @Transactional
    void getCaptura() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        // Get the captura
        restCapturaMockMvc
            .perform(get(ENTITY_API_URL_ID, captura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(captura.getId().intValue()))
            .andExpect(jsonPath("$.imagemContentType").value(DEFAULT_IMAGEM_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagem").value(Base64Utils.encodeToString(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO));
    }

    @Test
    @Transactional
    void getNonExistingCaptura() throws Exception {
        // Get the captura
        restCapturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaptura() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();

        // Update the captura
        Captura updatedCaptura = capturaRepository.findById(captura.getId()).get();
        // Disconnect from session so that the updates on updatedCaptura are not directly saved in db
        em.detach(updatedCaptura);
        updatedCaptura
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .video(UPDATED_VIDEO);

        restCapturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaptura.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaptura))
            )
            .andExpect(status().isOk());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
        Captura testCaptura = capturaList.get(capturaList.size() - 1);
        assertThat(testCaptura.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testCaptura.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testCaptura.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCaptura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCaptura.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCaptura.getVideo()).isEqualTo(UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void putNonExistingCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, captura.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captura))
            )
            .andExpect(status().isBadRequest());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(captura))
            )
            .andExpect(status().isBadRequest());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(captura)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCapturaWithPatch() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();

        // Update the captura using partial update
        Captura partialUpdatedCaptura = new Captura();
        partialUpdatedCaptura.setId(captura.getId());

        partialUpdatedCaptura.video(UPDATED_VIDEO);

        restCapturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaptura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaptura))
            )
            .andExpect(status().isOk());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
        Captura testCaptura = capturaList.get(capturaList.size() - 1);
        assertThat(testCaptura.getImagem()).isEqualTo(DEFAULT_IMAGEM);
        assertThat(testCaptura.getImagemContentType()).isEqualTo(DEFAULT_IMAGEM_CONTENT_TYPE);
        assertThat(testCaptura.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCaptura.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCaptura.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCaptura.getVideo()).isEqualTo(UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void fullUpdateCapturaWithPatch() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();

        // Update the captura using partial update
        Captura partialUpdatedCaptura = new Captura();
        partialUpdatedCaptura.setId(captura.getId());

        partialUpdatedCaptura
            .imagem(UPDATED_IMAGEM)
            .imagemContentType(UPDATED_IMAGEM_CONTENT_TYPE)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .video(UPDATED_VIDEO);

        restCapturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaptura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaptura))
            )
            .andExpect(status().isOk());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
        Captura testCaptura = capturaList.get(capturaList.size() - 1);
        assertThat(testCaptura.getImagem()).isEqualTo(UPDATED_IMAGEM);
        assertThat(testCaptura.getImagemContentType()).isEqualTo(UPDATED_IMAGEM_CONTENT_TYPE);
        assertThat(testCaptura.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCaptura.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCaptura.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCaptura.getVideo()).isEqualTo(UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void patchNonExistingCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, captura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captura))
            )
            .andExpect(status().isBadRequest());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(captura))
            )
            .andExpect(status().isBadRequest());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaptura() throws Exception {
        int databaseSizeBeforeUpdate = capturaRepository.findAll().size();
        captura.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCapturaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(captura)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Captura in the database
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaptura() throws Exception {
        // Initialize the database
        capturaRepository.saveAndFlush(captura);

        int databaseSizeBeforeDelete = capturaRepository.findAll().size();

        // Delete the captura
        restCapturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, captura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Captura> capturaList = capturaRepository.findAll();
        assertThat(capturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
