package ac2_project.example.ac2_ca.controller.test;

import ac2_project.example.ac2_ca.controller.AlunoController;
import ac2_project.example.ac2_ca.service.AlunoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Adicione estes imports no início do seu arquivo:
import jakarta.servlet.ServletException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Carrega apenas a camada Web (Controller) e mocka o resto
@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

   @Autowired
    private MockMvc mockMvc; // Permite simular requisições HTTP

    @MockBean // Cria um "dublê" do serviço
    private AlunoService alunoService;

    @Test
    @DisplayName("GET /api/alunos/{id}/validar-cursos deve retornar DTO com true")
    void testValidarCursos_RetornaTrue() throws Exception {
        Long alunoId = 1L;

        // 1. Mock: Simula o serviço retornando 'true'
        when(alunoService.verificarElegibilidadeNovosCursos(alunoId)).thenReturn(true);

        // 2. Execução e Validação
        mockMvc.perform(get("/api/alunos/{id}/validar-cursos", alunoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.alunoId").value(alunoId)) // Valida o JSON de resposta
                .andExpect(jsonPath("$.podeAdicionarCursos").value(true));
    }

    @Test
    @DisplayName("GET /api/alunos/{id}/validar-cursos deve retornar DTO com false")
    void testValidarCursos_RetornaFalse() throws Exception {
        Long alunoId = 2L;

        // 1. Mock: Simula o serviço retornando 'false'
        when(alunoService.verificarElegibilidadeNovosCursos(alunoId)).thenReturn(false);

        // 2. Execução e Validação
        mockMvc.perform(get("/api/alunos/{id}/validar-cursos", alunoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alunoId").value(alunoId))
                .andExpect(jsonPath("$.podeAdicionarCursos").value(false));
    }
    
    @Test
    @DisplayName("GET /api/alunos/{id}/validar-cursos deve lançar exceção se o serviço falhar")
    void testValidarCursos_AlunoNaoEncontrado() throws Exception {
        Long alunoId = 99L;

        // 1. Mock: Simula o serviço lançando a RuntimeException
        when(alunoService.verificarElegibilidadeNovosCursos(alunoId))
                .thenThrow(new RuntimeException("Aluno não encontrado"));

        // 2. Execução e Validação (Abordagem Corrigida)
        // O erro mostrou que o MockMvc empacota a exceção em uma ServletException
        Exception exception = assertThrows(ServletException.class, () -> { // <-- A CORREÇÃO ESTÁ AQUI
            // A chamada que deve falhar fica DENTRO do lambda
            mockMvc.perform(get("/api/alunos/{id}/validar-cursos", alunoId)
                    .accept(MediaType.APPLICATION_JSON));
        });

        // 3. Validação da Causa
        // A lógica de validação da causa continua a mesma
        Throwable cause = exception.getCause();
        
        assertNotNull(cause, "A exceção do servlet deve ter uma causa (cause)");
        assertTrue(cause instanceof RuntimeException, "A causa deve ser RuntimeException");
        assertEquals("Aluno não encontrado", cause.getMessage(), "A mensagem de erro está incorreta");
    }
}