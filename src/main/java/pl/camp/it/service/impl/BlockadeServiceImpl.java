package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IBlockadeDAO;
import pl.camp.it.dao.IRestaurantDAO;
import pl.camp.it.model.Blockade;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BlockadeServiceImpl implements IBlockadeService {
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IBlockadeDAO blockadeDAO;
    @Override
    public void persistBlockade(Blockade blockade) {
        this.blockadeDAO.persistBlockade(blockade);
    }

    @Override
    public Blockade getBlockadeById(int id) {
        return this.blockadeDAO.getBlockadeById(id);
    }

    @Override
    public List<Blockade> getBlockadesByRestaurantId(int id) {
        return this.blockadeDAO.getBlockadesByRestaurantId(id);
    }

    @Override
    public List<Blockade> getActiveBlockadesForRestaurant(int id) {
        return this.blockadeDAO.getActiveBlockadesForRestaurant(id);
    }

    @Override
    public List<Blockade> getAllActiveBlockades() {
        return blockadeDAO.getAllActiveBlockades();
    }

    @Override
    public boolean createBlockade(int restaurantId, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Blockade blck = new Blockade();
        blck.setActive(true);
        Restaurant restaurant = this.restaurantService.getRestaurantById(restaurantId);
        if (restaurant != null) {
            blck.setRestaurant(restaurant);
        }
        if(sessionObject.getUser() != null) {
            blck.setUserId(sessionObject.getUser().getId());
        }
        blck.setStartDate(LocalDateTime.parse(startDate, formatter));
        if(endDate != null) {
            blck.setEndDate(LocalDateTime.parse(endDate, formatter));
        }
        Blockade.autoValidateBlockade(blck);
        if(blck.getStartDate().isBefore(blck.getEndDate())) {
            this.persistBlockade(blck);
            return true;
        } return false;
    }
}
