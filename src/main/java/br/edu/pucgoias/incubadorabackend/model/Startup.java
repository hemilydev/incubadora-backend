package br.edu.pucgoias.incubadorabackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Entidade que representa uma Startup no sistema.
 * Mapeada para a tabela 'startups' no banco de dados.
 */
@Data
@Entity
@Table(name = "startups")
public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome do projeto/startup */
    @Column(nullable = false)
    private String nome;

    /** Nome do fundador responsável */
    @Column(nullable = false)
    private String fundador;

    /** Setor de atuação (ex: Tecnologia, Saúde, Educação...) */
    @Column(nullable = false)
    private String setor;

    /** Ciclo atual: CICLO1, CICLO2, CICLO3, DESCLASSIFICADA */
    @Column(nullable = false)
    private String ciclo;

    /** Descrição da startup */
    @Column(length = 1000)
    private String descricao;

    /** Data de entrada na incubadora */
    private LocalDate dataEntrada;

    /** Indica se o relatório foi enviado */
    private boolean relatorioEnviado;
}