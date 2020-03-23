package pl.camp.it.service;

import pl.camp.it.model.Blockade;

import java.util.List;

public interface IBlockadeService {
    void persistBlockade(Blockade blockade);
    List<Blockade> getBlockadesByRestaurantId(int id);
}
