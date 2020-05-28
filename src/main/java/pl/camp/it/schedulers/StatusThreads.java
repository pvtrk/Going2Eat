package pl.camp.it.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.camp.it.model.*;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IPromotionService;
import pl.camp.it.service.IReservationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatusThreads {
    @Autowired
    IReservationService reservationService;
    @Autowired
    IBlockadeService blockadeService;
    @Autowired
    IPromotionService promotionService;


    @Scheduled(fixedRate = 1000*60*5, initialDelay = 1000)
    public void updateReservations() {
          List<Reservation> reservations = reservationService.getFutureReservations();
        reservations.stream()
                .filter(x -> x.getStartTime().isBefore(LocalDateTime.now()))
                .forEach(x -> {
                    x.setPast(true);
                    x.setReservationStatus(ReservationStatus.PAST);
                    reservationService.persistReservation(x);
                });

    }
    @Scheduled(fixedRate = 1000*60*5, initialDelay = 2000)
    public void updateBlockades() {
        List<Blockade> blockades = blockadeService.getAllActiveBlockades();
        blockades.stream()
                .filter(x -> x.getEndDate().isBefore(LocalDateTime.now()))
                .forEach( x-> {
                    x.setActive(false);
                    blockadeService.persistBlockade(x);
                });
    }
    @Scheduled(fixedRate = 1000*60*5, initialDelay = 3000)
    public void updatePromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        promotions.stream()
                .filter(x -> x.getEndDate().isBefore(LocalDate.now()))
                .forEach( x-> {
                    x.setStatus(PromotionStatus.OFF);
                    promotionService.persistPromotion(x);
                });
    }

}
