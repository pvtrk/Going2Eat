package pl.camp.it.utils;

import org.springframework.stereotype.Service;

@Service
public class RegexChecker {
    private final String loginRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]{3,15}$";
    private final String passRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!+-]{5,15}$";
    private final String restaurantNameRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]{1,30}$";
    private final String restaurantAdressRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9]{3,15}[A-Za-z0-9_@./#&+-]{0,20}$";
    private final String restaurantCityRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private final String restaurantCuisineRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,20}$";
    private final String nameSurnameRegex ="^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{3,15}$";
    private final String descriptionRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!+-.\\s]{5,150}$";
    private final String placesQuantityRegex ="^[1-9]{1}[0-9]{0,2}$";
    private final String priceRegex = "^[1-9]{1}[0-9]{0,6}$";

    public boolean checkInput(String input, String regex) {
        if (input.matches(regex)) {
            return true;
        } return false;
    }

    public String getLoginRegex() {
        return loginRegex;
    }

    public String getPassRegex() {
        return passRegex;
    }

    public String getNameSurnameRegex() {
        return nameSurnameRegex;
    }

    public String getDescriptionRegex() {
        return descriptionRegex;
    }

    public String getPlacesQuantityRegex() {
        return placesQuantityRegex;
    }

    public String getPriceRegex() {
        return priceRegex;
    }

    public String getRestaurantNameRegex() {
        return restaurantNameRegex;
    }

    public String getRestaurantAdressRegex() {
        return restaurantAdressRegex;
    }

    public String getRestaurantCityRegex() {
        return restaurantCityRegex;
    }

    public String getRestaurantCuisineRegex() {
        return restaurantCuisineRegex;
    }
}
