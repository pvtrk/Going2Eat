package pl.camp.it.service;

import pl.camp.it.model.Promotion;

import java.util.List;

public interface IPromotionService {
    void persistPromotion(Promotion promotion);
    Promotion getPromotionById(int id);
    List<Promotion> getPromotionsSortedByDate();
    List<Promotion> getDistinctedPromotions();
    List<Promotion> getPromotionsByRestaurantId(int id);
    List<Promotion> getAllPromotions();
    boolean createPromotion(Promotion promotion, String startDate, String endDate);
}
