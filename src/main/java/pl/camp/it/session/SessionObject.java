package pl.camp.it.session;

import pl.camp.it.model.User;

public class SessionObject {
    private boolean logged;
    private User user;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
