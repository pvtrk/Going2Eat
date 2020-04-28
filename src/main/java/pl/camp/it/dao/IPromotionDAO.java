package pl.camp.it.dao;

import pl.camp.it.model.Promotion;

import java.util.List;

public interface IPromotionDAO {
    void persistPromotion(Promotion promotion);
    Promotion getPromotionById(int id);
    List<Promotion> getPromotionsSortedByDate();
    List<Promotion> getAllPromotions();

}
