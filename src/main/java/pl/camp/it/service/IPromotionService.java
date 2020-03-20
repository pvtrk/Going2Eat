package pl.camp.it.service;

import pl.camp.it.model.Promotion;

import java.util.List;

public interface IPromotionService {
    void persistPromotion(Promotion promotion);
    List<Promotion> getPromotionsSortedByDate();
}
