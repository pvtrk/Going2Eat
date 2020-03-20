package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.model.Promotion;
import pl.camp.it.service.IPromotionService;

import java.util.List;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    IPromotionDAO promotionDAO;
    @Override
    public void persistPromotion(Promotion promotion) {
        this.promotionDAO.persistPromotion(promotion);
    }

    @Override
    public List<Promotion> getPromotionsSortedByDate() {
        return this.promotionDAO.getPromotionsSortedByDate();
    }
}
