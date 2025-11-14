package ac2_project.example.ac2_ca.repository.test;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.TipoCurso;
import ac2_project.example.ac2_ca.entity.NomeVO;
import ac2_project.example.ac2_ca.entity.RA;
import ac2_project.example.ac2_ca.repository.AlunoRepository;
import ac2_project.example.ac2_ca.repository.CursoRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Carrega apenas a camada de persistência e usa um BD em memória (ex: H2)
class AlunoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // Ajuda a preparar o cenário

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository; // Necessário para persistir cursos

    @Test
    @DisplayName("Deve salvar e buscar um Aluno com VOs e Cursos")
    void testDeveSalvarEBuscarAlunoComVOsECursos() {
        // 1. Cria VOs
        NomeVO nomeAluno = new NomeVO("Aluno Persistido");
        RA raAluno = new RA(54321);

        // 2. Cria Cursos e salva (eles precisam existir no BD)
        Curso curso1 = new Curso(new NomeVO("Curso de JPA"), 8, 80, false, TipoCurso.NOTA);
        Curso curso2 = new Curso(new NomeVO("Curso de Docker"), 0, 100, false, TipoCurso.FREQUENCIA);
        
        // O @DataJpaTest não aplica Cascade.PERSIST automaticamente vindo de @ManyToMany
        // Então, salvamos os cursos primeiro.
        cursoRepository.save(curso1);
        cursoRepository.save(curso2);

        // 3. Cria Aluno e associa
        Aluno aluno = new Aluno(nomeAluno, raAluno);
        aluno.adicionarCurso(curso1);
        aluno.adicionarCurso(curso2);

        // 4. Salva o Aluno
        Aluno alunoSalvo = alunoRepository.save(aluno);
        
        // 5. Limpa o cache para forçar a busca do BD
        entityManager.flush();
        entityManager.clear();

        // 6. Busca o Aluno
        Aluno alunoBuscado = alunoRepository.findById(alunoSalvo.getId()).orElse(null);

        // 7. Valida
        assertThat(alunoBuscado).isNotNull();
        assertThat(alunoBuscado.getName()).isEqualTo(nomeAluno);
        assertThat(alunoBuscado.getRa()).isEqualTo(raAluno);
        assertThat(alunoBuscado.getCursos()).hasSize(2);
        assertThat(alunoBuscado.getCursos()).contains(curso1, curso2);
    }
}