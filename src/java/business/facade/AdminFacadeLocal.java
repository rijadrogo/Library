package business.facade;

import business.model.Admin;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author riki
 */
@Local
public interface AdminFacadeLocal {

    void create(Admin admin);

    void edit(Admin admin);

    void remove(Admin admin);

    Admin find(Object id);

    List<Admin> findAll();

    List<Admin> findRange(int[] range);

    int count();
    
}
