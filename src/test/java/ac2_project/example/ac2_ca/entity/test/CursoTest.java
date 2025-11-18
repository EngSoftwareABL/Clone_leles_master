package ac2_project.example.ac2_ca.entity.test;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.TipoCurso;
import ac2_project.example.ac2_ca.entity.NomeVO;
import ac2_project.example.ac2_ca.entity.RA;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class CursoTest{
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
    void testeGettersSetters() {
    	Curso curso = criarCurso(TipoCurso.FREQUENCIA, 0f, 99.9f);
    	curso.getName();
    	NomeVO NOME_NOVO = new NomeVO("Novo curso");
    	curso.setName(NOME_NOVO);
    	curso.getPresenca();
    	curso.setPresenca(99.8f);
    	curso.getTipoCurso();
    }
    
    @Test
    void testeEquals() {
    	Curso curso1 = criarCurso(TipoCurso.FREQUENCIA, 0f, 99.9f);
    	Curso curso2 = criarCurso(TipoCurso.FREQUENCIA, 0f, 99.9f);
    	curso2.setTipoCurso(TipoCurso.FREQUENCIA);
    	Object obj = new Object();
    	assertFalse(curso1.equals(obj), "Um objeto RA não deve ser igual a um objeto de outra classe");
    	
    	curso1.equals(curso2);
    	assertTrue(curso1.equals(curso1), "RAs com o mesmo número devem ser iguais");
    	assertFalse(curso1.equals(null), "Um curso não deve ser igual a null");
    }

    @Test
    @DisplayName("Equals deve cobrir o branch 'this.id != null' e 'outro.id == null'")
    void testEquals_UmIdNulo() {
        // Supondo que você tem um 'setId' ou 'BaseEntity' com 'setId'
        Curso cursoComId = new Curso();
        cursoComId.setId(1L); // <--- Objeto 1 tem ID

        Curso cursoSemId = new Curso(); // <--- Objeto 2 NÃO tem ID
        
        // Cobre o branch (true && false) da Linha 116, que cai na Linha 119
        assertFalse(cursoComId.equals(cursoSemId), "Entidade com ID não deve ser igual a entidade sem ID");
    }

    @Test
    @DisplayName("Equals deve cobrir o branch 'this.id == null' e 'outro.id == null'")
    void testEquals_AmbosIdsNulos() {
        Curso cursoSemId1 = new Curso(); // Objeto 1 NÃO tem ID
        Curso cursoSemId2 = new Curso(); // Objeto 2 NÃO tem ID
        
        // Cobre o branch (false && ...) da Linha 116, que cai na Linha 119
        // Retorna false porque são instâncias de memória diferentes
        assertFalse(cursoSemId1.equals(cursoSemId2), "Entidades transientes diferentes não devem ser iguais");
    }
    
    @Test
    @DisplayName("Deve retornar false em isAprovado se o TipoCurso for nulo")
    void testIsAprovado_ComTipoNulo() {
        // 1. Cria um curso com as seguintes condições:
        //    - concluido = false
        //    - tipoCurso = null
        Curso curso = new Curso(
            new NomeVO("Curso com Tipo Nulo"), // name
            0f,  // mediaFinal (não importa para este teste)
            0f,  // presenca (não importa para este teste)
            false, // cursoConcluido <-- Importante
            null   // tipoCurso <-- O ponto principal da correção
        );

        // 2. Executa e Valida
        // Esta chamada vai falhar no 'if' da linha 123 (concluido é false).
        // Vai falhar no 'if' da linha 127 (tipoCurso não é NOTA).
        // Vai falhar no 'if' da linha 131 (tipoCurso não é FREQUENCIA).
        // E finalmente vai executar a linha 135.
        assertFalse(curso.isAprovado(), "Curso com tipo nulo deve retornar false");
    }
}
