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
import java.util.stream.Collectors;

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
    public Promotion getPromotionById(int id) {
        return this.promotionDAO.getPromotionById(id);
    }

    @Override
    public List<Promotion> getPromotionsSortedByDate() {
        return this.promotionDAO.getPromotionsSortedByDate();
    }

    @Override
    public List<Promotion> getDistinctedPromotions() {
        List<Promotion> promotions = this.promotionDAO.getAllPromotions();
        ArrayList<Promotion> result = promotions.stream()
                .filter(x -> x.isDistinction())
                .collect(Collectors.toCollection(ArrayList::new));
        return result;
    }

    @Override
    public List<Promotion> getPromotionsByRestaurantId(int id) {
        return this.promotionDAO.getPromotionsByRestaurantId(id);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionDAO.getAllPromotions();
    }

    @Override
    public boolean createPromotion(Promotion promotion, String date1, String date2) {
        if(validatePromotionInput(promotion)) {
            promotion.setStatus(PromotionStatus.ACTIVE);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            promotion.setStartDate(LocalDate.parse(date1, formatter));
            promotion.setEndDate(LocalDate.parse(date2, formatter));
            if(promotion.getStartDate().isBefore(promotion.getEndDate())) {
                this.promotionDAO.persistPromotion(promotion);
                return true;
            }
        } return false;
    }

    private boolean validatePromotionInput(Promotion promotion) {
        if (promotion != null) {
            if ((promotion.getDescription() != null) &&
                    regexChecker.checkInput(promotion.getDescription(), regexChecker.getDescriptionRegex())) {
                if (promotion.getPrice() > 0 && promotion.getPrice() <= 999) {
                    if (promotion.getRestaurant() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
