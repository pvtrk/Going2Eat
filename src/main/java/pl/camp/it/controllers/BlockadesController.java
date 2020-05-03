package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Blockade;
import pl.camp.it.model.Restaurant;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;
import pl.camp.it.utils.RegexChecker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BlockadesController {
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    RegexChecker regexChecker;
    @Autowired
    IBlockadeService blockadeService;

    @GetMapping(value="/blockReservations/{id}")
    public String showBlockingForm(@PathVariable int id) {
        return "blockReservations";
    }

    @PostMapping(value="/blockReservations/{id}")
    public String blockReservations(@PathVariable int id, @RequestParam String dateStart,
                                    @RequestParam String dateEnd, Model model) {
        boolean blockAction = false;
        if(regexChecker.checkInput(dateStart, regexChecker.getDateTimeRegexp())
                && regexChecker.checkInput(dateEnd, regexChecker.getDateTimeRegexp())) {
            blockAction = blockadeService.createBlockade(id, dateStart, dateEnd);
        }
        if(blockAction) {
            model.addAttribute("message", "Udało Ci się zablokować rezerwacje");
        } else {
            model.addAttribute("message", "Coś poszło nie tak");
        }
        return "blockReservations";

    }

    @GetMapping(value="/unblockReservations/{id}")
    public String showUnblockingForm(@PathVariable int id, Model model) {
        List<Blockade> blockadeList = this.blockadeService.getActiveBlockadesForRestaurant(id);
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        model.addAttribute("blockades" , blockadeList);
        model.addAttribute("restaurant", restaurant);
        return "unblockReservations";
    }
    @PostMapping(value="/unblck/{id}")
    public String unblockReservation(@PathVariable int id) {

        Blockade blck = this.blockadeService.getBlockadeById(id);
        blck.setActive(false);
        this.blockadeService.persistBlockade(blck);

        return "redirect:/myRestaurants";
    }
}
