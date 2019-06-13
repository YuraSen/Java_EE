package com.lab4.dao;

import com.lab4.entity.Notebook;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class NotebookDao implements AutoCloseable {
    private EntityManager entityManager;

    public NotebookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Notebook> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notebook> personCriteria = cb.createQuery(Notebook.class);
        Root<Notebook> personRoot = personCriteria.from(Notebook.class);
        personCriteria.select(personRoot);
        List<Notebook> listOfNotebook = entityManager.createQuery(personCriteria).getResultList();
        return listOfNotebook;
    }
    public  boolean create(Notebook notebook) {
        entityManager.getTransaction().begin();
        entityManager.persist(notebook);
        if (entityManager.find(Notebook.class, notebook.getId()) != null) {
            entityManager.getTransaction().commit();
            return true;
        }
        entityManager.getTransaction().commit();
        return false;
    }
    public Optional<Notebook> getById(long id) {
        entityManager.getTransaction().begin();
        Optional<Notebook> car = Optional.of(entityManager.find(Notebook.class, id));
        entityManager.getTransaction().commit();
        return car;
    }

    public boolean remove(long id) {
        try {
            entityManager.getTransaction().begin();
            Notebook notebookRemove = entityManager.find(Notebook.class, id);
            entityManager.remove(notebookRemove);
            entityManager.getTransaction().commit();
            return true;
        } catch (IllegalArgumentException e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }


    public boolean update(Notebook newNotebook) {
        entityManager.getTransaction().begin();
        Optional<Notebook> updatedCar = Optional.of(entityManager.merge(newNotebook));

        if (updatedCar.get().equals(
                entityManager.find(Notebook.class, newNotebook.getId()))){
            entityManager.getTransaction().commit();
            return true;
        }
        entityManager.getTransaction().rollback();
        return false;
    }

    @Override
    public void close() {
        entityManager.close();
    }



}
