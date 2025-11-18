package ac2_project.example.ac2_ca.entity.test;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.TipoCurso;
import ac2_project.example.ac2_ca.entity.NomeVO;
import ac2_project.example.ac2_ca.entity.RA;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
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
    	
    	curso1.equals(curso2);
    	assertTrue(curso1.equals(curso1), "RAs com o mesmo número devem ser iguais");
    	assertFalse(curso1.equals(null), "Um curso não deve ser igual a null");
    }
}