// Em ac2_project.example.ac2_ca.dto
package ac2_project.example.ac2_ca.dto;

import lombok.Data; // ou lombok.Getter

@Data // Adiciona Getters, Setters, toString, etc.
// OU @Getter // Adiciona apenas Getters
public class ValidacaoResponseDTO {
    private Long alunoId;
    private boolean podeAdicionarCursos;

    public ValidacaoResponseDTO(Long alunoId, boolean podeAdicionarCursos) {
        this.alunoId = alunoId;
        this.podeAdicionarCursos = podeAdicionarCursos;
    }
}