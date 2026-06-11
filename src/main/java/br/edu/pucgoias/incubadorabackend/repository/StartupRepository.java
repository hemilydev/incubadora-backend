package br.edu.pucgoias.incubadorabackend.repository;

import br.edu.pucgoias.incubadorabackend.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório de Startup.
 * O JpaRepository já fornece os métodos básicos:
 * save, findById, findAll, deleteById, etc.
 */
@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {

    /** Busca todas as startups de um determinado ciclo */
    List<Startup> findByCiclo(String ciclo);
}