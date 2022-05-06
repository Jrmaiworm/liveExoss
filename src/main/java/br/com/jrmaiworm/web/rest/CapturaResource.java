package br.com.jrmaiworm.web.rest;

import br.com.jrmaiworm.domain.Captura;
import br.com.jrmaiworm.repository.CapturaRepository;
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
 * REST controller for managing {@link br.com.jrmaiworm.domain.Captura}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CapturaResource {

    private final Logger log = LoggerFactory.getLogger(CapturaResource.class);

    private static final String ENTITY_NAME = "captura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapturaRepository capturaRepository;

    public CapturaResource(CapturaRepository capturaRepository) {
        this.capturaRepository = capturaRepository;
    }

    /**
     * {@code POST  /capturas} : Create a new captura.
     *
     * @param captura the captura to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new captura, or with status {@code 400 (Bad Request)} if the captura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capturas")
    public ResponseEntity<Captura> createCaptura(@RequestBody Captura captura) throws URISyntaxException {
        log.debug("REST request to save Captura : {}", captura);
        if (captura.getId() != null) {
            throw new BadRequestAlertException("A new captura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Captura result = capturaRepository.save(captura);
        return ResponseEntity
            .created(new URI("/api/capturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capturas/:id} : Updates an existing captura.
     *
     * @param id the id of the captura to save.
     * @param captura the captura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captura,
     * or with status {@code 400 (Bad Request)} if the captura is not valid,
     * or with status {@code 500 (Internal Server Error)} if the captura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capturas/{id}")
    public ResponseEntity<Captura> updateCaptura(@PathVariable(value = "id", required = false) final Long id, @RequestBody Captura captura)
        throws URISyntaxException {
        log.debug("REST request to update Captura : {}, {}", id, captura);
        if (captura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Captura result = capturaRepository.save(captura);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, captura.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /capturas/:id} : Partial updates given fields of an existing captura, field will ignore if it is null
     *
     * @param id the id of the captura to save.
     * @param captura the captura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated captura,
     * or with status {@code 400 (Bad Request)} if the captura is not valid,
     * or with status {@code 404 (Not Found)} if the captura is not found,
     * or with status {@code 500 (Internal Server Error)} if the captura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/capturas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Captura> partialUpdateCaptura(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Captura captura
    ) throws URISyntaxException {
        log.debug("REST request to partial update Captura partially : {}, {}", id, captura);
        if (captura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, captura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!capturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Captura> result = capturaRepository
            .findById(captura.getId())
            .map(existingCaptura -> {
                if (captura.getImagem() != null) {
                    existingCaptura.setImagem(captura.getImagem());
                }
                if (captura.getImagemContentType() != null) {
                    existingCaptura.setImagemContentType(captura.getImagemContentType());
                }
                if (captura.getData() != null) {
                    existingCaptura.setData(captura.getData());
                }
                if (captura.getDescricao() != null) {
                    existingCaptura.setDescricao(captura.getDescricao());
                }
                if (captura.getStatus() != null) {
                    existingCaptura.setStatus(captura.getStatus());
                }
                if (captura.getVideo() != null) {
                    existingCaptura.setVideo(captura.getVideo());
                }

                return existingCaptura;
            })
            .map(capturaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, captura.getId().toString())
        );
    }

    /**
     * {@code GET  /capturas} : get all the capturas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capturas in body.
     */
    @GetMapping("/capturas")
    public List<Captura> getAllCapturas() {
        log.debug("REST request to get all Capturas");
        return capturaRepository.findAll();
    }

    /**
     * {@code GET  /capturas/:id} : get the "id" captura.
     *
     * @param id the id of the captura to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the captura, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capturas/{id}")
    public ResponseEntity<Captura> getCaptura(@PathVariable Long id) {
        log.debug("REST request to get Captura : {}", id);
        Optional<Captura> captura = capturaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(captura);
    }

    /**
     * {@code DELETE  /capturas/:id} : delete the "id" captura.
     *
     * @param id the id of the captura to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capturas/{id}")
    public ResponseEntity<Void> deleteCaptura(@PathVariable Long id) {
        log.debug("REST request to delete Captura : {}", id);
        capturaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
