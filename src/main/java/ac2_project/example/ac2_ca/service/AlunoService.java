// Em ac2_project.example.ac2_ca.service
package ac2_project.example.ac2_ca.service;

import ac2_project.example.ac2_ca.entity.Aluno;
import ac2_project.example.ac2_ca.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    /**
     * Caso de Uso: Validar se um aluno pode se inscrever em mais cursos.
     */
    @Transactional(readOnly = true)
    public boolean verificarElegibilidadeNovosCursos(Long alunoId) {
        // 1. Busca a entidade do banco
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // 2. Chama a LÓGICA DE NEGÓCIO (que está na própria entidade)
        return aluno.podeAdicionarMaisCursos();
    }
    
    // Outros métodos de serviço (criar aluno, matricular em curso, etc.)
}