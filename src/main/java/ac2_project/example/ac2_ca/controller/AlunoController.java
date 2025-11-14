// Em ac2_project.example.ac2_ca.controller
package ac2_project.example.ac2_ca.controller;

import ac2_project.example.ac2_ca.dto.ValidacaoResponseDTO;
import ac2_project.example.ac2_ca.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{id}/validar-cursos")
    public ResponseEntity<ValidacaoResponseDTO> validarCursos(@PathVariable Long id) {
        boolean podeAdicionar = alunoService.verificarElegibilidadeNovosCursos(id);
        
        ValidacaoResponseDTO response = new ValidacaoResponseDTO(id, podeAdicionar);
        
        return ResponseEntity.ok(response);
    }

    // Outros endpoints (POST /api/alunos para criar, etc.)
}