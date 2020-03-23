package pl.camp.it.dao;

import pl.camp.it.model.Blockade;

import java.util.List;

public interface IBlockadeDAO {
    void persistBlockade(Blockade blockade);
    List<Blockade> getBlockadesByRestaurantId(int id);
}
