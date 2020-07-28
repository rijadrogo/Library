/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.facade;

import business.model.Book;
import business.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riki
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

    boolean register(String userName, String password, String name, String surename);

    User login(String username, String password);

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    List<User> findByUsername(String username);



}
