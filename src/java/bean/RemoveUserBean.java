package bean;

import bean.pagination.PaginationHelper;
import business.facade.UserFacadeLocal;
import business.model.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author riki
 */
@Named(value = "removeUserBean")
@ViewScoped
public class RemoveUserBean implements Serializable {

    @Inject
    private UserFacadeLocal userFacadeLocal;

    private User user;
    List<User> users;
    private String firstname = "";
    private String lastname = "";
    private String username = "";

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getUsersByFirstname() {
        users = userFacadeLocal.findByFirstname(firstname);
        return users;
    }

    public List<User> getUsersByLastname() {
        users = userFacadeLocal.findByFirstname(lastname);
        return users;
    }

    public List<User> getUsersByUsername() {

        return userFacadeLocal.findByFirstname(username);
    }

    public List<User> getUsers() {
        if (!firstname.isEmpty()) {
            users = userFacadeLocal.findByFirstname(firstname);
        } else if (!lastname.isEmpty()) {
            users = userFacadeLocal.findByLastname(lastname);
        } else if (!username.isEmpty()) {
            users = userFacadeLocal.findByUsername(username);
        } else {
            System.out.println("asds");
            users = userFacadeLocal.findAll();
        }

        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public DataModel getDtmdl() {
        return dtmdl;
    }

    public void setDtmdl(DataModel dtmdl) {
        this.dtmdl = dtmdl;
    }

    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel dtmdl = null;

    public RemoveUserBean() {
    }

    public PaginationHelper getPagination() {

        if (pagination == null) {

            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return userFacadeLocal.count();
                }

                @Override
                public DataModel createPageDataModel() {

                    return new ListDataModel(userFacadeLocal.findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public DataModel getdtmdl() {
        if (dtmdl == null) {
            dtmdl = getPagination().createPageDataModel();
        }
        return dtmdl;
    }

    private void recreateModel() {
        dtmdl = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    private void updateCurrentItem() {
        int count = userFacadeLocal.count();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {

                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            user = userFacadeLocal.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public void removeUser(String username) {
        User userToRemove = userFacadeLocal.findByUsername(username).get(0);
        userFacadeLocal.remove(userToRemove);
    }

}
