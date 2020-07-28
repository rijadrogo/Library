/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.facade;

import business.model.Book;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riki
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> implements BookFacadeLocal {

    @PersistenceContext(unitName = "LibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return em.createQuery(
                "SELECT b FROM Book b WHERE b.bookAuthor LIKE :bookAuthor")
                .setParameter("bookAuthor", "%" + author + "%")
                .getResultList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return em.createQuery(
                "SELECT b FROM Book b WHERE b.bookName LIKE :bookName")
                .setParameter("bookName", "%" + title + "%")
                .getResultList();
    }

}
