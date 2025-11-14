package ac2_project.example.ac2_ca.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded; 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Id; // <--- IMPORTE ISSO
import jakarta.persistence.GeneratedValue; // <--- IMPORTE ISSO
import jakarta.persistence.GenerationType; // <--- IMPORTE ISSO

@Entity
@Table(name = "aluno")
public class Aluno {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chaves primárias são geralmente Long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	@Embedded
    private NomeVO name; // "STR name -> VO - imutavel"

    @Embedded
    private RA ra; // "INT RA -> VO - imutavel"

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "aluno_curso",
        joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private Set<Curso> cursos = new HashSet<>();

    // Construtor, Getters e Setters
    
    public Aluno() {}
    
    public Aluno(NomeVO name, RA ra) {
        this.name = name;
        this.ra = ra;
    }

    // Getters retornam os VOs
    public NomeVO getName() {
        return name;
    }

    public RA getRa() {
        return ra;
    }
    
    // Setters recebem os VOs
    public void setName(NomeVO name) {
        this.name = name;
    }

    public void setRa(RA ra) {
        this.ra = ra;
    }
    
    public Set<Curso> getCursos() {
        return cursos;
    }

	public void adicionarCurso(Curso curso) {
		this.cursos.add(curso);
	}

	public boolean podeAdicionarMaisCursos() {
		// Se o aluno não tem cursos, ele sempre pode adicionar.
		if (this.cursos.isEmpty()) {
			return true;
		}

		// Itera por todos os cursos do aluno.
		// Se encontrar UM curso que NÃO esteja aprovado, ele não pode adicionar mais.
		for (Curso curso : this.cursos) {
			if (!curso.isAprovado()) {
				return false; // Encontrou um curso reprovado.
			}
		}
		
		return true;
	}

	public Integer getVagasDisponiveis() {
		if (this.podeAdicionarMaisCursos()) {
			return 3; 
		} else {
			return 0; 
		}
	}
}