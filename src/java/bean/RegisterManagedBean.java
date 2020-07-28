package bean;

import business.facade.UserFacadeLocal;
import encryption.MD5Encryption;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author riki
 */
@Named(value = "registerManagedBean")
@ViewScoped
public class RegisterManagedBean implements Serializable {

    @Inject
    private UserFacadeLocal userFacadeLocal;

    private String username;
    private String password;
    private String name;
    private String surename;
    private String message;

    public String register() {
        boolean userCreated = userFacadeLocal.register(username, password, name, surename);
        if (userCreated) {
            return "index";
        }
        message = "Unijeto korisničko ime je zauzeto";
        return "register";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

}
