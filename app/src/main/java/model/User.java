package model;

/**
 * Created by fevzi on 06.10.14.
 */
public class User {


    private String email;
    private String password;
    private String name;
    private String password_confirm;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getPasswordConfirm() {

        return password_confirm;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswordConfirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }
}
