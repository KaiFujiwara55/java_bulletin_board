package session;

import model.User;

public class Session {
    private boolean login_status = false;
    private User user = null;

    public boolean get_login_status() {
        return this.login_status;
    }

    public User get_user() {
        return this.user;
    }

    public void login(User user) {
        this.user = user;
        this.login_status = true;
    }

    public void logout() {
        this.user = null;
        this.login_status = false;
    }
}
