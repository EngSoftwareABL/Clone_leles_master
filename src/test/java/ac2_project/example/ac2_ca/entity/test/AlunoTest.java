package ac2_project.example.ac2_ca.entity.test;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.TipoCurso;
import ac2_project.example.ac2_ca.entity.NomeVO;
import ac2_project.example.ac2_ca.entity.RA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Teste puro de JUnit, sem Spring
class AlunoTest {

    private Aluno aluno;
    private final NomeVO NOME_PADRAO = new NomeVO("Aluno Teste");
    private final RA RA_PADRAO = new RA(12345);

    @BeforeEach
    void setUp() {
        aluno = new Aluno(NOME_PADRAO, RA_PADRAO);
    }

    private Curso criarCurso(TipoCurso tipo, float media, float presenca) {
        return new Curso(new NomeVO("Curso de " + tipo), media, presenca, false, tipo);
    }

    @Test
    @DisplayName("Deve permitir novos cursos se o aluno não tiver nenhum")
    void testPodeAdicionarCursos_SemCursos() {
        assertTrue(aluno.podeAdicionarMaisCursos(), "Aluno sem cursos deve poder adicionar");
        assertEquals(3, aluno.getVagasDisponiveis(), "Vagas deve ser 3");
    }

    @Test
    @DisplayName("Deve permitir novos cursos com requisitos de NOTA atendidos")
    void testPodeAdicionarCursos_ComCursoDeNotaAprovado() {
        // Média >= 7 e Presença > 75%
        Curso curso = criarCurso(TipoCurso.NOTA, 7.0f, 75.1f);
        aluno.adicionarCurso(curso);

        assertTrue(aluno.podeAdicionarMaisCursos(), "Aluno com curso de NOTA OK deve poder adicionar");
        assertEquals(3, aluno.getVagasDisponiveis(), "Vagas deve ser 3");
        curso.getMediaFinal();
        curso.setMediaFinal(80.0f);
    }

    @Test
    @DisplayName("Deve BLOQUEAR novos cursos se a MÉDIA for baixa")
    void testNaoPodeAdicionarCursos_CursoDeNotaReprovadoMedia() {
        // Média < 7
        Curso curso = criarCurso(TipoCurso.NOTA, 6.9f, 100f);
        aluno.adicionarCurso(curso);

        assertFalse(aluno.podeAdicionarMaisCursos(), "Aluno com MÉDIA baixa não deve poder adicionar");
        assertEquals(0, aluno.getVagasDisponiveis(), "Vagas deve ser 0");
    }

    @Test
    @DisplayName("Deve BLOQUEAR novos cursos se a PRESENÇA for baixa em curso de NOTA")
    void testNaoPodeAdicionarCursos_CursoDeNotaReprovadoPresenca() {
        // Presença <= 75%
        Curso curso = criarCurso(TipoCurso.NOTA, 10.0f, 75.0f);
        aluno.adicionarCurso(curso);

        assertFalse(aluno.podeAdicionarMaisCursos(), "Aluno com PRESENÇA baixa (nota) não deve poder adicionar");
        assertEquals(0, aluno.getVagasDisponiveis(), "Vagas deve ser 0");
        curso.setId(12345678L);
    }

    @Test
    @DisplayName("Deve permitir novos cursos com requisitos de FREQUENCIA atendidos")
    void testPodeAdicionarCursos_ComCursoDeFrequenciaAprovado() {
        // Presença == 100%
        Curso curso = criarCurso(TipoCurso.FREQUENCIA, 0f, 100f);
        aluno.adicionarCurso(curso);

        assertTrue(aluno.podeAdicionarMaisCursos(), "Aluno com curso de FREQUENCIA OK deve poder adicionar");
        assertEquals(3, aluno.getVagasDisponiveis(), "Vagas deve ser 3");
        
        curso.isAprovado();
    }

    @Test
    @DisplayName("Deve BLOQUEAR novos cursos se a PRESENÇA for baixa em curso de FREQUENCIA")
    void testNaoPodeAdicionarCursos_CursoDeFrequenciaReprovado() {
        // Presença < 100%
        Curso curso = criarCurso(TipoCurso.FREQUENCIA, 0f, 99.9f);
        aluno.adicionarCurso(curso);

        assertFalse(aluno.podeAdicionarMaisCursos(), "Aluno com PRESENÇA baixa (frequencia) não deve poder adicionar");
        assertEquals(0, aluno.getVagasDisponiveis(), "Vagas deve ser 0");
        
        curso.isAprovado();
        curso.getAlunos();
        curso.setAlunos(null);
    }

    @Test
    @DisplayName("Deve permitir novos cursos se TODOS os cursos estiverem OK")
    void testPodeAdicionarCursos_ComMultiplosCursosOK() {
        aluno.adicionarCurso(criarCurso(TipoCurso.NOTA, 8f, 80f));
        aluno.adicionarCurso(criarCurso(TipoCurso.FREQUENCIA, 0f, 100f));
        aluno.adicionarCurso(criarCurso(TipoCurso.NOTA, 7f, 76f));

        assertTrue(aluno.podeAdicionarMaisCursos(), "Aluno com todos os cursos OK deve poder adicionar");
        assertEquals(3, aluno.getVagasDisponiveis(), "Vagas deve ser 3");
    }

    @Test
    @DisplayName("Deve BLOQUEAR novos cursos se UM ÚNICO curso falhar")
    void testNaoPodeAdicionarCursos_ComUmCursoReprovado() {
        aluno.adicionarCurso(criarCurso(TipoCurso.NOTA, 8f, 80f));
        aluno.adicionarCurso(criarCurso(TipoCurso.FREQUENCIA, 0f, 100f));
        aluno.adicionarCurso(criarCurso(TipoCurso.NOTA, 6.9f, 90f)); // Este falha

        assertFalse(aluno.podeAdicionarMaisCursos(), "Aluno com um curso reprovado não deve poder adicionar");
        assertEquals(0, aluno.getVagasDisponiveis(), "Vagas deve ser 0");
    }
    
    @Test
    @DisplayName("Testa alteração de variáveis para o aluno")
    void testCriacaoAluno() {
        aluno.setId(12346789L);
        NomeVO NOME_NOVO = new NomeVO("Novo Nome");
        aluno.setName(NOME_NOVO);
        RA RA_NOVO = new RA(123456);
        aluno.setRa(RA_NOVO);
    }
}