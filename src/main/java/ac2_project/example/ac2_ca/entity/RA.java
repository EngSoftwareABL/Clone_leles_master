// Pode ir em um novo pacote, ex: ac2_project.example.ac2_ca.entity.vo
package ac2_project.example.ac2_ca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public final class RA implements Serializable {

    @Column(name = "ra", nullable = false, unique = true)
    private final int numero;

    /**
     * Construtor protegido para uso exclusivo do JPA.
     */
    protected RA() {
        this.numero = 0; // Valor padrão para JPA
    }

    /**
     * Construtor público que garante a validação.
     * @param numero O número do RA.
     */
    public RA(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("RA deve ser um número positivo.");
        }
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        // 1. Testa se é a mesma instância de memória
        if (this == o) return true;

        // 2. Testa se é nulo ou se é de classe diferente
        // Esta é a linha que pega o seu teste
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Agora que sabemos que é da mesma classe, faz o cast
        RA ra = (RA) o;

        // 4. Compara os valores internos (assumindo que o campo se chama 'numero')
        // Se você usa o getNumero(), troque para: return Objects.equals(this.getNumero(), ra.getNumero());
        return Objects.equals(this.numero, ra.numero); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return String.valueOf(numero);
    }
}