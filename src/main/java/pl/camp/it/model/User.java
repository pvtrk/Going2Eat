package pl.camp.it.model;

import javax.persistence.*;

@Entity(name="tuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String login;
    private String pass;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public static void autoVlidate(User user) {
        if (user != null) {
            if (user.getId() < 0) {
                throw new UserValidationException();
            }
            if (user.getRole() == null) {
                throw new UserValidationException();
            }
            if (user.getPass() == null) {
                throw new UserValidationException();
            }
            if (user.getLogin() == null) {
                throw new UserValidationException();
            }
        }

    }

    static class UserValidationException extends RuntimeException{

    }
}
