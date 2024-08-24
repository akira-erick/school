package org.me.utils;

import jakarta.persistence.EntityManager;
import org.me.dao.AlunoDao;
import org.me.models.Aluno;

import java.util.List;

public class DataProcessor {

    public static void registerAluno(Aluno aluno){
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao =  new AlunoDao(em);

        em.getTransaction().begin();
        dao.registerAluno(aluno);
        em.getTransaction().commit();
    }

    public static boolean deleteAluno(String nome){
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao =  new AlunoDao(em);

        em.getTransaction().begin();

        Aluno a = dao.selectAlunoByName(nome);
        if(null == a){
            return false;
        }
        dao.deleteAluno(a.getId());

        em.getTransaction().commit();
        return true;
    }

    public static void updateAluno(Aluno oldAluno, Aluno newAluno){
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao =  new AlunoDao(em);

        em.getTransaction().begin();
        dao.updateAluno(oldAluno, newAluno);
        em.getTransaction().commit();
    }

    public static Aluno selectAlunoByName(String name){
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao =  new AlunoDao(em);
        return dao.selectAlunoByName(name);
    }

    public static List<Aluno> listAllAlunos(){
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao dao =  new AlunoDao(em);

        return dao.listAllAlunos();
    }
}
