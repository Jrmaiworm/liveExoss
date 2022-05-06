package br.com.jrmaiworm.web.rest;

import br.com.jrmaiworm.domain.Estacao;
import br.com.jrmaiworm.repository.EstacaoRepository;
import br.com.jrmaiworm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.jrmaiworm.domain.Estacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstacaoResource {

    private final Logger log = LoggerFactory.getLogger(EstacaoResource.class);

    private static final String ENTITY_NAME = "estacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstacaoRepository estacaoRepository;

    public EstacaoResource(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
    }

    /**
     * {@code POST  /estacaos} : Create a new estacao.
     *
     * @param estacao the estacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estacao, or with status {@code 400 (Bad Request)} if the estacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estacaos")
    public ResponseEntity<Estacao> createEstacao(@RequestBody Estacao estacao) throws URISyntaxException {
        log.debug("REST request to save Estacao : {}", estacao);
        if (estacao.getId() != null) {
            throw new BadRequestAlertException("A new estacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Estacao result = estacaoRepository.save(estacao);
        return ResponseEntity
            .created(new URI("/api/estacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estacaos/:id} : Updates an existing estacao.
     *
     * @param id the id of the estacao to save.
     * @param estacao the estacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estacao,
     * or with status {@code 400 (Bad Request)} if the estacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estacaos/{id}")
    public ResponseEntity<Estacao> updateEstacao(@PathVariable(value = "id", required = false) final Long id, @RequestBody Estacao estacao)
        throws URISyntaxException {
        log.debug("REST request to update Estacao : {}, {}", id, estacao);
        if (estacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Estacao result = estacaoRepository.save(estacao);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estacaos/:id} : Partial updates given fields of an existing estacao, field will ignore if it is null
     *
     * @param id the id of the estacao to save.
     * @param estacao the estacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estacao,
     * or with status {@code 400 (Bad Request)} if the estacao is not valid,
     * or with status {@code 404 (Not Found)} if the estacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the estacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estacaos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Estacao> partialUpdateEstacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Estacao estacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estacao partially : {}, {}", id, estacao);
        if (estacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Estacao> result = estacaoRepository
            .findById(estacao.getId())
            .map(existingEstacao -> {
                if (estacao.getNome() != null) {
                    existingEstacao.setNome(estacao.getNome());
                }
                if (estacao.getAssociado() != null) {
                    existingEstacao.setAssociado(estacao.getAssociado());
                }
                if (estacao.getEmail() != null) {
                    existingEstacao.setEmail(estacao.getEmail());
                }
                if (estacao.getTelefone() != null) {
                    existingEstacao.setTelefone(estacao.getTelefone());
                }
                if (estacao.getCidade() != null) {
                    existingEstacao.setCidade(estacao.getCidade());
                }
                if (estacao.getEstado() != null) {
                    existingEstacao.setEstado(estacao.getEstado());
                }
                if (estacao.getLente() != null) {
                    existingEstacao.setLente(estacao.getLente());
                }
                if (estacao.getCamera() != null) {
                    existingEstacao.setCamera(estacao.getCamera());
                }
                if (estacao.getFov() != null) {
                    existingEstacao.setFov(estacao.getFov());
                }
                if (estacao.getKml() != null) {
                    existingEstacao.setKml(estacao.getKml());
                }
                if (estacao.getLat() != null) {
                    existingEstacao.setLat(estacao.getLat());
                }
                if (estacao.getLng() != null) {
                    existingEstacao.setLng(estacao.getLng());
                }
                if (estacao.getSite() != null) {
                    existingEstacao.setSite(estacao.getSite());
                }
                if (estacao.getAtiva() != null) {
                    existingEstacao.setAtiva(estacao.getAtiva());
                }
                if (estacao.getPareamento() != null) {
                    existingEstacao.setPareamento(estacao.getPareamento());
                }

                return existingEstacao;
            })
            .map(estacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, estacao.getId().toString())
        );
    }

    /**
     * {@code GET  /estacaos} : get all the estacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estacaos in body.
     */
    @GetMapping("/estacaos")
    public List<Estacao> getAllEstacaos() {
        log.debug("REST request to get all Estacaos");
        return estacaoRepository.findAll();
    }

    /**
     * {@code GET  /estacaos/:id} : get the "id" estacao.
     *
     * @param id the id of the estacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estacaos/{id}")
    public ResponseEntity<Estacao> getEstacao(@PathVariable Long id) {
        log.debug("REST request to get Estacao : {}", id);
        Optional<Estacao> estacao = estacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estacao);
    }

    /**
     * {@code DELETE  /estacaos/:id} : delete the "id" estacao.
     *
     * @param id the id of the estacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estacaos/{id}")
    public ResponseEntity<Void> deleteEstacao(@PathVariable Long id) {
        log.debug("REST request to delete Estacao : {}", id);
        estacaoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
