package ac2_project.example.ac2_ca.entity.test;

import ac2_project.example.ac2_ca.entity.RA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Teste puro de JUnit, sem Spring
class RATest {

    @Test
    void testNaoDeveCriarRANegativoOuZero() {
        // Garante que a validação no construtor está funcionando
        assertThrows(IllegalArgumentException.class, () -> {
            new RA(0);
        }, "Deveria lançar exceção para RA zero");

        assertThrows(IllegalArgumentException.class, () -> {
            new RA(-123);
        }, "Deveria lançar exceção para RA negativo");
    }

    @Test
    void testDeveCriarRAValido() {
        RA ra = new RA(12345);
        assertEquals(12345, ra.getNumero());
    }

    @Test
    void testEqualsEHashCode() {
        RA ra1 = new RA(12345);
        RA ra2 = new RA(12345);
        RA ra3 = new RA(54321);

        assertEquals(ra1, ra2, "RAs iguais devem ser 'equals'");
        assertEquals(ra1.hashCode(), ra2.hashCode(), "RAs iguais devem ter mesmo hashCode");
        assertNotEquals(ra1, ra3, "RAs diferentes não devem ser 'equals'");
        
        //dObject obj = new Object();
    	//assertFalse(ra1.equals(obj), "Um objeto RA não deve ser igual a um objeto de outra classe");
    	//assertFalse(ra1.equals(ra1), "Um objeto RA não deve ser igual a um objeto de outra classe");
        
        ra1.equals(ra2);
        assertFalse(ra1.equals(ra3), "RAs com números diferentes não devem ser iguais");
        assertTrue(ra1.equals(ra2), "RAs com o mesmo número devem ser iguais");
        //assertFalse(ra1.equals(null), "Um objeto RA não deve ser igual a null");
        ra1.toString();
    }
}