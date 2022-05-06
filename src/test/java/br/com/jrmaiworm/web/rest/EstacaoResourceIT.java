package br.com.jrmaiworm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.jrmaiworm.IntegrationTest;
import br.com.jrmaiworm.domain.Estacao;
import br.com.jrmaiworm.repository.EstacaoRepository;
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

/**
 * Integration tests for the {@link EstacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCIADO = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_LENTE = "AAAAAAAAAA";
    private static final String UPDATED_LENTE = "BBBBBBBBBB";

    private static final String DEFAULT_CAMERA = "AAAAAAAAAA";
    private static final String UPDATED_CAMERA = "BBBBBBBBBB";

    private static final Double DEFAULT_FOV = 1D;
    private static final Double UPDATED_FOV = 2D;

    private static final String DEFAULT_KML = "AAAAAAAAAA";
    private static final String UPDATED_KML = "BBBBBBBBBB";

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LNG = 1D;
    private static final Double UPDATED_LNG = 2D;

    private static final String DEFAULT_SITE = "AAAAAAAAAA";
    private static final String UPDATED_SITE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVA = false;
    private static final Boolean UPDATED_ATIVA = true;

    private static final String DEFAULT_PAREAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_PAREAMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstacaoRepository estacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstacaoMockMvc;

    private Estacao estacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estacao createEntity(EntityManager em) {
        Estacao estacao = new Estacao()
            .nome(DEFAULT_NOME)
            .associado(DEFAULT_ASSOCIADO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .lente(DEFAULT_LENTE)
            .camera(DEFAULT_CAMERA)
            .fov(DEFAULT_FOV)
            .kml(DEFAULT_KML)
            .lat(DEFAULT_LAT)
            .lng(DEFAULT_LNG)
            .site(DEFAULT_SITE)
            .ativa(DEFAULT_ATIVA)
            .pareamento(DEFAULT_PAREAMENTO);
        return estacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estacao createUpdatedEntity(EntityManager em) {
        Estacao estacao = new Estacao()
            .nome(UPDATED_NOME)
            .associado(UPDATED_ASSOCIADO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .lente(UPDATED_LENTE)
            .camera(UPDATED_CAMERA)
            .fov(UPDATED_FOV)
            .kml(UPDATED_KML)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .site(UPDATED_SITE)
            .ativa(UPDATED_ATIVA)
            .pareamento(UPDATED_PAREAMENTO);
        return estacao;
    }

    @BeforeEach
    public void initTest() {
        estacao = createEntity(em);
    }

    @Test
    @Transactional
    void createEstacao() throws Exception {
        int databaseSizeBeforeCreate = estacaoRepository.findAll().size();
        // Create the Estacao
        restEstacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estacao)))
            .andExpect(status().isCreated());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Estacao testEstacao = estacaoList.get(estacaoList.size() - 1);
        assertThat(testEstacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEstacao.getAssociado()).isEqualTo(DEFAULT_ASSOCIADO);
        assertThat(testEstacao.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEstacao.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testEstacao.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEstacao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEstacao.getLente()).isEqualTo(DEFAULT_LENTE);
        assertThat(testEstacao.getCamera()).isEqualTo(DEFAULT_CAMERA);
        assertThat(testEstacao.getFov()).isEqualTo(DEFAULT_FOV);
        assertThat(testEstacao.getKml()).isEqualTo(DEFAULT_KML);
        assertThat(testEstacao.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testEstacao.getLng()).isEqualTo(DEFAULT_LNG);
        assertThat(testEstacao.getSite()).isEqualTo(DEFAULT_SITE);
        assertThat(testEstacao.getAtiva()).isEqualTo(DEFAULT_ATIVA);
        assertThat(testEstacao.getPareamento()).isEqualTo(DEFAULT_PAREAMENTO);
    }

    @Test
    @Transactional
    void createEstacaoWithExistingId() throws Exception {
        // Create the Estacao with an existing ID
        estacao.setId(1L);

        int databaseSizeBeforeCreate = estacaoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estacao)))
            .andExpect(status().isBadRequest());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstacaos() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        // Get all the estacaoList
        restEstacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].associado").value(hasItem(DEFAULT_ASSOCIADO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].lente").value(hasItem(DEFAULT_LENTE)))
            .andExpect(jsonPath("$.[*].camera").value(hasItem(DEFAULT_CAMERA)))
            .andExpect(jsonPath("$.[*].fov").value(hasItem(DEFAULT_FOV.doubleValue())))
            .andExpect(jsonPath("$.[*].kml").value(hasItem(DEFAULT_KML)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lng").value(hasItem(DEFAULT_LNG.doubleValue())))
            .andExpect(jsonPath("$.[*].site").value(hasItem(DEFAULT_SITE)))
            .andExpect(jsonPath("$.[*].ativa").value(hasItem(DEFAULT_ATIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].pareamento").value(hasItem(DEFAULT_PAREAMENTO)));
    }

    @Test
    @Transactional
    void getEstacao() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        // Get the estacao
        restEstacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, estacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.associado").value(DEFAULT_ASSOCIADO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.lente").value(DEFAULT_LENTE))
            .andExpect(jsonPath("$.camera").value(DEFAULT_CAMERA))
            .andExpect(jsonPath("$.fov").value(DEFAULT_FOV.doubleValue()))
            .andExpect(jsonPath("$.kml").value(DEFAULT_KML))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lng").value(DEFAULT_LNG.doubleValue()))
            .andExpect(jsonPath("$.site").value(DEFAULT_SITE))
            .andExpect(jsonPath("$.ativa").value(DEFAULT_ATIVA.booleanValue()))
            .andExpect(jsonPath("$.pareamento").value(DEFAULT_PAREAMENTO));
    }

    @Test
    @Transactional
    void getNonExistingEstacao() throws Exception {
        // Get the estacao
        restEstacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstacao() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();

        // Update the estacao
        Estacao updatedEstacao = estacaoRepository.findById(estacao.getId()).get();
        // Disconnect from session so that the updates on updatedEstacao are not directly saved in db
        em.detach(updatedEstacao);
        updatedEstacao
            .nome(UPDATED_NOME)
            .associado(UPDATED_ASSOCIADO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .lente(UPDATED_LENTE)
            .camera(UPDATED_CAMERA)
            .fov(UPDATED_FOV)
            .kml(UPDATED_KML)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .site(UPDATED_SITE)
            .ativa(UPDATED_ATIVA)
            .pareamento(UPDATED_PAREAMENTO);

        restEstacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstacao))
            )
            .andExpect(status().isOk());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
        Estacao testEstacao = estacaoList.get(estacaoList.size() - 1);
        assertThat(testEstacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstacao.getAssociado()).isEqualTo(UPDATED_ASSOCIADO);
        assertThat(testEstacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstacao.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEstacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEstacao.getLente()).isEqualTo(UPDATED_LENTE);
        assertThat(testEstacao.getCamera()).isEqualTo(UPDATED_CAMERA);
        assertThat(testEstacao.getFov()).isEqualTo(UPDATED_FOV);
        assertThat(testEstacao.getKml()).isEqualTo(UPDATED_KML);
        assertThat(testEstacao.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testEstacao.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testEstacao.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testEstacao.getPareamento()).isEqualTo(UPDATED_PAREAMENTO);
    }

    @Test
    @Transactional
    void putNonExistingEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstacaoWithPatch() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();

        // Update the estacao using partial update
        Estacao partialUpdatedEstacao = new Estacao();
        partialUpdatedEstacao.setId(estacao.getId());

        partialUpdatedEstacao
            .associado(UPDATED_ASSOCIADO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .lente(UPDATED_LENTE)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .site(UPDATED_SITE)
            .ativa(UPDATED_ATIVA)
            .pareamento(UPDATED_PAREAMENTO);

        restEstacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstacao))
            )
            .andExpect(status().isOk());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
        Estacao testEstacao = estacaoList.get(estacaoList.size() - 1);
        assertThat(testEstacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEstacao.getAssociado()).isEqualTo(UPDATED_ASSOCIADO);
        assertThat(testEstacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstacao.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEstacao.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEstacao.getLente()).isEqualTo(UPDATED_LENTE);
        assertThat(testEstacao.getCamera()).isEqualTo(DEFAULT_CAMERA);
        assertThat(testEstacao.getFov()).isEqualTo(DEFAULT_FOV);
        assertThat(testEstacao.getKml()).isEqualTo(DEFAULT_KML);
        assertThat(testEstacao.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testEstacao.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testEstacao.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testEstacao.getPareamento()).isEqualTo(UPDATED_PAREAMENTO);
    }

    @Test
    @Transactional
    void fullUpdateEstacaoWithPatch() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();

        // Update the estacao using partial update
        Estacao partialUpdatedEstacao = new Estacao();
        partialUpdatedEstacao.setId(estacao.getId());

        partialUpdatedEstacao
            .nome(UPDATED_NOME)
            .associado(UPDATED_ASSOCIADO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .lente(UPDATED_LENTE)
            .camera(UPDATED_CAMERA)
            .fov(UPDATED_FOV)
            .kml(UPDATED_KML)
            .lat(UPDATED_LAT)
            .lng(UPDATED_LNG)
            .site(UPDATED_SITE)
            .ativa(UPDATED_ATIVA)
            .pareamento(UPDATED_PAREAMENTO);

        restEstacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstacao))
            )
            .andExpect(status().isOk());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
        Estacao testEstacao = estacaoList.get(estacaoList.size() - 1);
        assertThat(testEstacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEstacao.getAssociado()).isEqualTo(UPDATED_ASSOCIADO);
        assertThat(testEstacao.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEstacao.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testEstacao.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEstacao.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEstacao.getLente()).isEqualTo(UPDATED_LENTE);
        assertThat(testEstacao.getCamera()).isEqualTo(UPDATED_CAMERA);
        assertThat(testEstacao.getFov()).isEqualTo(UPDATED_FOV);
        assertThat(testEstacao.getKml()).isEqualTo(UPDATED_KML);
        assertThat(testEstacao.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testEstacao.getLng()).isEqualTo(UPDATED_LNG);
        assertThat(testEstacao.getSite()).isEqualTo(UPDATED_SITE);
        assertThat(testEstacao.getAtiva()).isEqualTo(UPDATED_ATIVA);
        assertThat(testEstacao.getPareamento()).isEqualTo(UPDATED_PAREAMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstacao() throws Exception {
        int databaseSizeBeforeUpdate = estacaoRepository.findAll().size();
        estacao.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstacaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estacao in the database
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstacao() throws Exception {
        // Initialize the database
        estacaoRepository.saveAndFlush(estacao);

        int databaseSizeBeforeDelete = estacaoRepository.findAll().size();

        // Delete the estacao
        restEstacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estacao> estacaoList = estacaoRepository.findAll();
        assertThat(estacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
