/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.facade;

import business.model.Book;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riki
 */
@Local
public interface BookFacadeLocal {

    void create(Book book);

    void edit(Book book);

    void remove(Book book);

    Book find(Object id);

    List<Book> findAll();

    List<Book> findRange(int[] range);

    int count();

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String author);

}
