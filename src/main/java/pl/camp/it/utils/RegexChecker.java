package pl.camp.it.utils;

import org.springframework.stereotype.Service;

@Service
public class RegexChecker {
    private final static String loginRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@\\/\"'#&*!+-,\\s]{3,15}$";
    private final static String passRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!+-,\\s]{5,15}$";
    private final static String restaurantNameRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9\\/#&*!+-.,\\s]{1,30}$";
    private final static String restaurantAdressRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9\\s]{3,15}[A-Za-z0-9_@.#&+\\-\\s]{0,30}$";
    private final static String restaurantCityRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s]{3,30}$";
    private final static String restaurantCuisineRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\\/#&*!+-.,\\s]{3,50}$";
    private final static String nameSurnameRegex ="^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\\-']{3,15}$";
    private final static String descriptionRegex = "^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ0-9_@.\\/#&*!+-,\\s]{5,150}$";
    private final static String placesQuantityRegex ="^[1-9]{1}[0-9]{0,2}$";
    private final static String priceRegex = "^[1-9]{1}[0-9]{0,6}$";
    private final static String dateRegexp = "^[2]{1}[0]{1}[2-4]{1}[0-9]{1}[-]{1}[0-1]{1}[0-9]{1}[-]{1}[0-3]{1}[0-9]{1}$";
    private final static String dateTimeRegexp = "^[2]{1}[0]{1}[2-4]{1}[0-9]{1}[-]{1}[0-1]{1}[0-9]{1}[-]{1}[0-3]{1}[0-9]{1}[\\s]{1}[1-2]{1}[0-9]{1}[:]{1}[0-5]{1}[0-9]{1}$";

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

    public String getDateRegexp() {
        return dateRegexp;
    }

    public String getDateTimeRegexp() {
        return dateTimeRegexp;
    }
}
