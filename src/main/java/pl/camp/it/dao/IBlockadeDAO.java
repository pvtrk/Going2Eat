package pl.camp.it.dao;


import pl.camp.it.model.Blockade;

import java.util.List;

public interface IBlockadeDAO {
    void persistBlockade(Blockade blockade);
    Blockade getBlockadeById(int id);
    List<Blockade> getBlockadesByRestaurantId(int id);
    List<Blockade> getActiveBlockadesForRestaurant(int id);
    List<Blockade> getAllActiveBlockades();
}
