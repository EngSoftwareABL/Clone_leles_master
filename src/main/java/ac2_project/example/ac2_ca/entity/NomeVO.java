// Em ac2_project.example.ac2_ca.entity.vo
package ac2_project.example.ac2_ca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public final class NomeVO implements Serializable {

    @Column(name = "name", nullable = false)
    private final String valor;

    protected NomeVO() {
        this.valor = null;
    }

    public NomeVO(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        // Você poderia adicionar mais regras: ex: tamanho máximo, formato, etc.
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        //if (o == null || getClass() != o.getClass()) return false;
        NomeVO nomeVO = (NomeVO) o;
        return Objects.equals(valor, nomeVO.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}