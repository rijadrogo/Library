package bean;

import business.facade.UserFacadeLocal;
import business.model.User;
import encryption.MD5Encryption;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riki
 */
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    @Inject
    private UserFacadeLocal userFacadeLocal;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String message = "";
    private boolean loggedIn = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String login() {
        System.out.println("login");
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "index";
        }
        User user = userFacadeLocal.login(username, password);
        if (user != null) {
            name = user.getFirstName();
            lastname = user.getLastName();
            loggedIn = true;
            if(user.getPrivIleges().equals(1)){
                return "admin_view";
            }
            return "user_view";
        }
        return "index";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5Encryption.encrypt(password);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String logout() {
        loggedIn = false;
        username = "";
        password = "";
        return "index";
    }
}
