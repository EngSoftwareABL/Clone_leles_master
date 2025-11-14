// Em ac2_project.example.ac2_ca.repository
package ac2_project.example.ac2_ca.repository;

import ac2_project.example.ac2_ca.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}