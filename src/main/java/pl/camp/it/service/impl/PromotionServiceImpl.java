package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IPromotionDAO;
import pl.camp.it.model.Promotion;
import pl.camp.it.model.PromotionStatus;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.utils.RegexChecker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public List<Promotion> getDistinctedPromotions() {
        List<Promotion> promotions = this.promotionDAO.getAllPromotions();
        List<Promotion> result = new ArrayList<>();
        for (Promotion promotion : promotions) {
            if(promotion.isDistinction()) {
                result.add(promotion);
            }
        }
        return result;
    }


    private boolean validatePromotionInput(Promotion promotion) {
        if (promotion != null) {
            if ((promotion.getDescription() != null) &&
                    regexChecker.checkInput(promotion.getDescription(), regexChecker.getDescriptionRegex())) {
                if ((promotion.getPrice() != 0) && (promotion.getPrice() > 0 && promotion.getPrice() <= 999)) {
                    if (promotion.getRestaurant() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean createPromotion(Promotion promotion, String date1, String date2) {
        if(validatePromotionInput(promotion)) {
            promotion.setStatus(PromotionStatus.ACTIVE);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            promotion.setStartDate(LocalDate.parse(date1, formatter));
            promotion.setEndDate(LocalDate.parse(date2, formatter));
            this.promotionDAO.persistPromotion(promotion);
            return true;
        } return false;
    }
}
