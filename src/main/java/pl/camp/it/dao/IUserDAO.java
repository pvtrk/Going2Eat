package pl.camp.it.dao;

import pl.camp.it.model.User;

public interface IUserDAO {
    void persistUser (User user);
    User getUserByLogin (String login);
    User getUserById (int id);
}
