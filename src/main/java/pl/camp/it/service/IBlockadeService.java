package pl.camp.it.service;

import pl.camp.it.model.Blockade;

import java.util.List;

public interface IBlockadeService {
    void persistBlockade(Blockade blockade);
    Blockade getBlockadeById(int id);
    List<Blockade> getBlockadesByRestaurantId(int id);
    List<Blockade> getActiveBlockadesForRestaurant(int id);
    List<Blockade> getAllActiveBlockades();
    boolean createBlockade(int restaurantId, String startDate, String endDate);
}
