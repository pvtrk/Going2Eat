package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.camp.it.model.Blockade;
import pl.camp.it.service.IBlockadeService;
import pl.camp.it.service.IRestaurantService;
import pl.camp.it.service.IUserService;
import pl.camp.it.session.SessionObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BlockadesController {
    @Autowired
    SessionObject sessionObject;
    @Autowired
    IUserService userService;
    @Autowired
    IRestaurantService restaurantService;
    @Autowired
    IBlockadeService blockadeService;

    @GetMapping(value="/blockReservations/{id}")
    public String showBlockingForm(@PathVariable int id) {
        return "blockReservations";
    }

    @PostMapping(value="/blockReservations/{id}")
    public String blockReservations(@PathVariable int id, @RequestParam String dateStart,
                                    @RequestParam String dateEnd, Model model) {
       // sessionObject.setUser(this.userService.getUserById(2));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Blockade blck = new Blockade();
        blck.setActive(true);
        blck.setRestaurant(this.restaurantService.getRestaurantById(id));
        blck.setUserId(sessionObject.getUser().getId());
        blck.setStartDate(LocalDateTime.parse(dateStart, formatter));
        if(dateEnd != null) {
            blck.setEndDate(LocalDateTime.parse(dateEnd, formatter));
        }
        this.blockadeService.persistBlockade(blck);
        return "redirect:/myRestaurants";

    }

    @GetMapping(value="/unblockReservations/{id}")
    public String showUnlockingForm(@PathVariable int id, Model model) {
        List<Blockade> blockadeList = this.blockadeService.getBlockadesByRestaurantId(id);
        model.addAttribute("blockades" , blockadeList);
        return "unblockReservations";
    }
}
