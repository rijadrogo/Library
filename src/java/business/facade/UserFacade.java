/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.facade;

import business.model.Admin;
import business.model.Book;
import business.model.User;
import encryption.MD5Encryption;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author riki
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "LibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public boolean register(String username, String password, String name, String surename) {
        try {
            Query query = em.createNamedQuery("User.findByUsername");
            query.setParameter("username", username);
            List<User> users = query.getResultList();

            if (users.isEmpty()) {
                Admin userPriveleges = (Admin) em.createNamedQuery("Admin.findById").setParameter("id", 2).getSingleResult();
                User user = new User();
                user.setFirstName(name);
                user.setLastName(surename);
                user.setPassword(password);
                user.setUsername(username);
                user.setPrivIleges(userPriveleges);
                em.persist(user);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    @Override
    public User login(String username, String password) {
        try {
            Query query = em.createNamedQuery("User.findByUsernameAndPassword");
            query.setParameter("username", username);
            query.setParameter("password", password);
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;

        }
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        return (List<User>) em.createQuery(
                "SELECT u FROM User u WHERE u.firstName LIKE :firstname")
                .setParameter("firstname", "%" + firstname + "%")
                .getResultList();
    }

    @Override
    public List<User> findByLastname(String lastname) {
        return (List<User>) em.createQuery(
                "SELECT u FROM User u WHERE u.lastName LIKE :lastname")
                .setParameter("lastname", "%" + lastname + "%")
                .getResultList();
    }

    @Override
    public List<User> findByUsername(String username) {
        return (List<User>) em.createQuery(
                "SELECT u FROM User u WHERE u.username LIKE :username")
                .setParameter("username", "%" + username + "%")
                .getResultList();
    }

}
