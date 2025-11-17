package ac2_project.example.ac2_ca.entity.test;

import ac2_project.example.ac2_ca.entity.NomeVO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Teste puro de JUnit, sem Spring
class NomeVOTest {

    @Test
    void testNaoDeveCriarNomeVazioOuNulo() {
        // Garante que a validação no construtor está funcionando
        assertThrows(IllegalArgumentException.class, () -> {
            new NomeVO(null);
        }, "Deveria lançar exceção para nome nulo");

        assertThrows(IllegalArgumentException.class, () -> {
            new NomeVO("");
        }, "Deveria lançar exceção para nome vazio");

        assertThrows(IllegalArgumentException.class, () -> {
            new NomeVO("   ");
        }, "Deveria lançar exceção para nome em branco");
    }

    @Test
    void testDeveCriarNomeValido() {
        // Garante que um nome válido é criado
        NomeVO nome = new NomeVO("Aluno Teste");
        assertEquals("Aluno Teste", nome.getValor());
    }
    
    @Test
    void validadorNome() {
        // Garante que um nome válido é criado
        NomeVO nome1 = new NomeVO("Aluno Teste");
        NomeVO nome2 = new NomeVO("Aluno Teste");
        nome1.equals(nome2);
        
        NomeVO nome3 = new NomeVO(null);
        nome3.equals(null);
        
        nome1.toString();
    }

    @Test
    void testEqualsEHashCode() {
        NomeVO nome1 = new NomeVO("Aluno Teste");
        NomeVO nome2 = new NomeVO("Aluno Teste");
        NomeVO nome3 = new NomeVO("Outro Aluno");

        assertEquals(nome1, nome2, "Nomes iguais devem ser 'equals'");
        assertEquals(nome1.hashCode(), nome2.hashCode(), "Nomes iguais devem ter mesmo hashCode");
        assertNotEquals(nome1, nome3, "Nomes diferentes não devem ser 'equals'");
    }
}