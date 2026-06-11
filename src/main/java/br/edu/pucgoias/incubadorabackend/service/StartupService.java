package br.edu.pucgoias.incubadorabackend.service;

import br.edu.pucgoias.incubadorabackend.model.Startup;
import br.edu.pucgoias.incubadorabackend.repository.StartupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de Startup.
 * Contém todas as regras de negócio relacionadas às startups.
 */
@Service
public class StartupService {

    private final StartupRepository startupRepository;

    public StartupService(StartupRepository startupRepository) {
        this.startupRepository = startupRepository;
    }

    /**
     * Retorna todas as startups cadastradas.
     */
    public List<Startup> listarTodas() {
        return startupRepository.findAll();
    }

    /**
     * Retorna startups filtradas por ciclo.
     */
    public List<Startup> listarPorCiclo(String ciclo) {
        return startupRepository.findByCiclo(ciclo);
    }

    /**
     * Cadastra uma nova startup no sistema.
     */
    public Startup cadastrar(Startup startup) {
        return startupRepository.save(startup);
    }

    /**
     * Atualiza os dados de uma startup existente.
     */
    public Startup atualizar(Long id, Startup dadosNovos) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Startup não encontrada"));

        startup.setNome(dadosNovos.getNome());
        startup.setFundador(dadosNovos.getFundador());
        startup.setSetor(dadosNovos.getSetor());
        startup.setCiclo(dadosNovos.getCiclo());
        startup.setDescricao(dadosNovos.getDescricao());
        startup.setRelatorioEnviado(dadosNovos.isRelatorioEnviado());

        return startupRepository.save(startup);
    }

    /**
     * Remove uma startup do sistema pelo ID.
     */
    public void deletar(Long id) {
        startupRepository.deleteById(id);
    }

    /**
     * Avança a startup para o próximo ciclo.
     * CICLO1 → CICLO2 → CICLO3 → permanece em CICLO3
     */
    public Startup avancarCiclo(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Startup não encontrada"));

        switch (startup.getCiclo()) {
            case "CICLO1" -> startup.setCiclo("CICLO2");
            case "CICLO2" -> startup.setCiclo("CICLO3");
            default -> throw new RuntimeException("Startup já está no ciclo final");
        }

        return startupRepository.save(startup);
    }

    /**
     * Desclassifica uma startup.
     */
    public Startup desclassificar(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Startup não encontrada"));

        startup.setCiclo("DESCLASSIFICADA");
        return startupRepository.save(startup);
    }

    /**
     * Registra o relatório de uma startup como enviado.
     */
    public Startup registrarRelatorio(Long id) {
        Startup startup = startupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Startup não encontrada"));

        startup.setRelatorioEnviado(true);
        return startupRepository.save(startup);
    }
}