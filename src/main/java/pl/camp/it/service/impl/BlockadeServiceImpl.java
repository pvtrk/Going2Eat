package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IBlockadeDAO;
import pl.camp.it.model.Blockade;
import pl.camp.it.service.IBlockadeService;

import java.util.List;

@Service
public class BlockadeServiceImpl implements IBlockadeService {
    @Autowired
    IBlockadeDAO blockadeDAO;
    @Override
    public void persistBlockade(Blockade blockade) {
        this.blockadeDAO.persistBlockade(blockade);
    }

    @Override
    public List<Blockade> getBlockadesByRestaurantId(int id) {
        return this.blockadeDAO.getBlockadesByRestaurantId(id);
    }
}
