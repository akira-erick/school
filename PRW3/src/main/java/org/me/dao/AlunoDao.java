package org.me.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.me.models.Aluno;
import org.me.utils.JPAUtil;

import java.util.List;

public class AlunoDao {

    private EntityManager em;

    public AlunoDao (EntityManager em){
        this.em = em;
    }

    public void registerAluno(Aluno aluno){
        em.persist(aluno);
    }

    public Aluno selectAlunoById(long id){
        return em.find(Aluno.class, id);
    }

    public void deleteAluno(long id){

        Aluno a = selectAlunoById(id);
        if(null != a) em.remove(a);
    }

    public void updateAluno(Aluno oldAluno, Aluno newAluno){
        String jpql = "UPDATE Aluno a SET a.nome = :nome, a.ra = :ra, a.email = :email, " +
                "a.nota1 = :nota1, a.nota2 = :nota2, a.nota3 = :nota3 " +
                "WHERE a.id = :id";
        em.createQuery(jpql)
                .setParameter("nome", newAluno.getNome())
                .setParameter("ra", newAluno.getRa())
                .setParameter("email", newAluno.getEmail())
                .setParameter("nota1", newAluno.getNota1())
                .setParameter("nota2", newAluno.getNota2())
                .setParameter("nota3", newAluno.getNota3())
                .setParameter("id", oldAluno.getId()) // Assuming ID is 1
                .executeUpdate();

    }

    public Aluno selectAlunoByName(String name){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            AlunoDao dao = new AlunoDao(em);
            String jpql = "SELECT a FROM Aluno a WHERE a.nome = :nome";
            TypedQuery<Aluno> query = em.createQuery(jpql, Aluno.class);
            query.setParameter("nome", name);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Aluno> listAllAlunos(){
        String jpql = "SELECT a FROM Aluno a";
        return em.createQuery(jpql, Aluno.class).getResultList();
    }
}
