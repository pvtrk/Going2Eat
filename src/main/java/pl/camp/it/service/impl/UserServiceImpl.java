package pl.camp.it.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.Register;
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

    @Override
    public boolean authenticateUser(String login, String pass) {
        User user = userDAO.getUserByLogin(login);
        if(user != null) {
            if(DigestUtils.md5Hex(pass).equals(user.getPass())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
        return this.userDAO.getUserByLogin(login);
    }

    @Override
    public boolean checkRegister(Register register) {
        User user = this.userDAO.getUserByLogin(register.getLogin());
        if(user == null && register.getPassword().equals(register.getPassword2())) {
            return true;
        } return false;
    }

    @Override
    public void registerUser(Register register) {
        User user = new User();
        user.setLogin(register.getLogin());
        user.setPass(DigestUtils.md5Hex(register.getPassword()));
        user.setName(register.getName());
        user.setSurname(register.getSurname());
        user.setRole(register.getRole());
        this.userDAO.persistUser(user);
    }

}
