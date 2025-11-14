package ac2_project.example.ac2_ca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded; // Importante: para embutir o VO
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidade que representa um Curso.
 * Utiliza o Value Object NomeVO para o seu nome.
 */
@Entity
@Table(name = "curso")
public class Curso extends BaseEntity {

    @Embedded // Usando o Value Object para o nome
    private NomeVO name;

    @Column(name = "media_final")
    private float mediaFinal;

    @Column(name = "presenca")
    private float presenca; // Campo adicionado para a regra de negócio

    @Column(name = "curso_concluido")
    protected boolean cursoConcluido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_curso", nullable = false)
    private TipoCurso tipoCurso;
    
    @ManyToMany(mappedBy = "cursos") // "cursos" é o nome da lista em Aluno
    private Set<Aluno> alunos = new HashSet<>();

    /**
     * Construtor padrão exigido pelo JPA.
     */
    public Curso() {
    }

    /**
     * Construtor de conveniência para criar um Curso.
     */
    public Curso(NomeVO name, float mediaFinal, float presenca, boolean cursoConcluido, TipoCurso tipoCurso) {
        this.name = name;
        this.mediaFinal = mediaFinal;
        this.presenca = presenca;
        this.cursoConcluido = cursoConcluido;
        this.tipoCurso = tipoCurso;
    }

    // --- Getters e Setters ---

    public NomeVO getName() {
        return name;
    }

    public void setName(NomeVO name) {
        this.name = name;
    }

    public float getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(float mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public float getPresenca() {
        return presenca;
    }

    public void setPresenca(float presenca) {
        this.presenca = presenca;
    }

    public boolean isCursoConcluido() {
        return cursoConcluido;
    }

    public void setCursoConcluido(boolean cursoConcluido) {
        this.cursoConcluido = cursoConcluido;
    }

    public TipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(TipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    // --- hashCode e equals (Boa prática para Entidades) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
         
        if (this.getId() != null && curso.getId() != null) {
            return Objects.equals(this.getId(), curso.getId());
        }
        return super.equals(o);
    }
    
    public boolean isAprovado() {
        if (this.cursoConcluido) {
            return true;
        }

        if (this.tipoCurso == TipoCurso.NOTA) {
            return this.mediaFinal >= 7.0f && this.presenca > 75.0f;
        }

        if (this.tipoCurso == TipoCurso.FREQUENCIA) {
            return this.presenca >= 100.0f;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }
}