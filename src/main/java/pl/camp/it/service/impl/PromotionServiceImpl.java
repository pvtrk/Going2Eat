package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.model.Promotion;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.utils.RegexChecker;

import java.util.List;

@Service
public class PromotionServiceImpl implements IPromotionService {
    @Autowired
    RegexChecker regexChecker;
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

    @Override
    public boolean validatePromotionInput(Promotion promotion) {
        if((promotion.getDescription() != null) &&
                regexChecker.checkInput(promotion.getDescription(), regexChecker.getDescriptionRegex())) {
            if((promotion.getPrice() != 0) && (promotion.getPrice() > 0 && promotion.getPrice() <= 999)) {
                if(promotion.getRestaurant() != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
