package br.com.jrmaiworm.repository;

import br.com.jrmaiworm.domain.Captura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Captura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapturaRepository extends JpaRepository<Captura, Long> {}
