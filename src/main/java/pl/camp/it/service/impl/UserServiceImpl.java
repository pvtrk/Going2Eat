package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.User;
import pl.camp.it.service.IUserService;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserDAO userDAO;
    @Override
    public void persistUser(User user) {
        this.userDAO.persistUser(user);
    }

    @Override
    public User getUserById(int id) {
        return this.userDAO.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

}
