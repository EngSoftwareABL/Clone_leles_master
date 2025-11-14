package ac2_project.example.ac2_ca.service;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.entity.Curso;
import ac2_project.example.ac2_ca.entity.TipoCurso;
import ac2_project.example.ac2_ca.entity.NomeVO;
import ac2_project.example.ac2_ca.entity.RA;
import ac2_project.example.ac2_ca.repository.AlunoRepository;
//import ac2_project.example.ac2_ca.service.AlunoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
class AlunoServiceTest {

    @Mock // Cria um "dublê" do repositório
    private AlunoRepository alunoRepository;

    @InjectMocks // Injeta os mocks (alunoRepository) no serviço
    private AlunoService alunoService;

    // Helper para criar cursos
    private Curso criarCurso(TipoCurso tipo, float media, float presenca) {
        return new Curso(new NomeVO("Curso " + tipo), media, presenca, false, tipo);
    }

    @Test
    @DisplayName("Serviço deve retornar true para aluno elegível")
    void testVerificarElegibilidade_AlunoElegivel() {
        // 1. Cenário: Aluno elegível (com curso aprovado)
        Aluno alunoElegivel = new Aluno(new NomeVO("Aluno Bom"), new RA(111));
        alunoElegivel.adicionarCurso(criarCurso(TipoCurso.NOTA, 8f, 80f));
        
        // 2. Mock: Simula o repositório encontrando o aluno
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(alunoElegivel));

        // 3. Execução: Chama o serviço
        boolean resultado = alunoService.verificarElegibilidadeNovosCursos(1L);

        // 4. Validação
        assertTrue(resultado, "Serviço deveria retornar true");
        verify(alunoRepository).findById(1L); // Garante que o método do repo foi chamado
    }

    @Test
    @DisplayName("Serviço deve retornar false para aluno não elegível")
    void testVerificarElegibilidade_AlunoNaoElegivel() {
        // 1. Cenário: Aluno não elegível (com curso reprovado)
        Aluno alunoNaoElegivel = new Aluno(new NomeVO("Aluno Ruim"), new RA(222));
        alunoNaoElegivel.adicionarCurso(criarCurso(TipoCurso.NOTA, 5f, 80f)); // Média baixa

        // 2. Mock
        when(alunoRepository.findById(2L)).thenReturn(Optional.of(alunoNaoElegivel));

        // 3. Execução
        boolean resultado = alunoService.verificarElegibilidadeNovosCursos(2L);

        // 4. Validação
        assertFalse(resultado, "Serviço deveria retornar false");
        verify(alunoRepository).findById(2L);
    }

    @Test
    @DisplayName("Serviço deve lançar RuntimeException se aluno não for encontrado")
    void testVerificarElegibilidade_AlunoNaoEncontrado() {
        // 1. Cenário: Aluno não existe
        Long idInexistente = 99L;

        // 2. Mock: Simula o repositório não encontrando nada
        when(alunoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // 3. Execução e Validação
        // Verifica se o serviço lança a exceção exata que definimos
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            alunoService.verificarElegibilidadeNovosCursos(idInexistente);
        });

        assertEquals("Aluno não encontrado", exception.getMessage());
        verify(alunoRepository).findById(idInexistente);
    }
}