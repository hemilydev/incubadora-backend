package br.edu.pucgoias.incubadorabackend.controller;

import br.edu.pucgoias.incubadorabackend.model.Startup;
import br.edu.pucgoias.incubadorabackend.service.StartupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Startup.
 * Gerencia todas as rotas relacionadas às startups.
 * Todas as rotas precisam de token JWT (configurado no SecurityConfig).
 */
@RestController
@RequestMapping("/api/startups")
public class StartupController {

    private final StartupService startupService;

    public StartupController(StartupService startupService) {
        this.startupService = startupService;
    }

    /**
     * GET /api/startups
     * Retorna todas as startups ou filtra por ciclo se informado.
     */
    @GetMapping
    public ResponseEntity<List<Startup>> listar(
            @RequestParam(required = false) String ciclo) {
        if (ciclo != null) {
            return ResponseEntity.ok(startupService.listarPorCiclo(ciclo));
        }
        return ResponseEntity.ok(startupService.listarTodas());
    }

    /**
     * POST /api/startups
     * Cadastra uma nova startup.
     */
    @PostMapping
    public ResponseEntity<Startup> cadastrar(@RequestBody Startup startup) {
        return ResponseEntity.ok(startupService.cadastrar(startup));
    }

    /**
     * PUT /api/startups/{id}
     * Atualiza os dados de uma startup existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Startup> atualizar(
            @PathVariable Long id,
            @RequestBody Startup startup) {
        return ResponseEntity.ok(startupService.atualizar(id, startup));
    }

    /**
     * DELETE /api/startups/{id}
     * Remove uma startup do sistema.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        startupService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/startups/{id}/avancar
     * Avança a startup para o próximo ciclo.
     */
    @PatchMapping("/{id}/avancar")
    public ResponseEntity<Startup> avancarCiclo(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.avancarCiclo(id));
    }

    /**
     * PATCH /api/startups/{id}/voltar
     * Volta a startup para o ciclo anterior.
     */
    @PatchMapping("/{id}/voltar")
    public ResponseEntity<Startup> voltarCiclo(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.voltarCiclo(id));
    }

    /**
     * PATCH /api/startups/{id}/desclassificar
     * Desclassifica uma startup.
     */
    @PatchMapping("/{id}/desclassificar")
    public ResponseEntity<Startup> desclassificar(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.desclassificar(id));
    }

    /**
     * PATCH /api/startups/{id}/reativar
     * Reativa uma startup desclassificada, voltando para o Ciclo 1.
     */
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Startup> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.reativar(id));
    }

    /**
     * PATCH /api/startups/{id}/relatorio
     * Registra o relatório de uma startup como enviado.
     */
    @PatchMapping("/{id}/relatorio")
    public ResponseEntity<Startup> registrarRelatorio(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.registrarRelatorio(id));
    }

    /**
     * PATCH /api/startups/{id}/cancelar-contrato
     * Cancela o registro de contrato de uma startup.
     */
    @PatchMapping("/{id}/cancelar-contrato")
    public ResponseEntity<Startup> cancelarContrato(@PathVariable Long id) {
        return ResponseEntity.ok(startupService.cancelarContrato(id));
    }
}