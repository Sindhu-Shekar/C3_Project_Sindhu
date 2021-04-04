import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Clock;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    public void generateConstantData() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("French Toast", 300);
    }
    int year, month, date, hour, minute, second;

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        generateConstantData();
        year = 2021; month = 4; date = 3; hour = 11; minute = 30; second = 0;
        Clock mockClockPass =
                Clock.fixed(
                        LocalDateTime.of(year, month, date, hour, minute, second).toInstant(OffsetDateTime.now().getOffset()),
                        Clock.systemDefaultZone().getZone()
                );
        restaurant.setClock(mockClockPass);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        generateConstantData();
        year = 2021; month = 4; date = 3; hour = 9; minute = 30; second = 0;
        Clock mockClockFail =
                Clock.fixed(
                        LocalDateTime.of(year, month, date, hour, minute, second).toInstant(OffsetDateTime.now().getOffset()),
                        Clock.systemDefaultZone().getZone()
                );
        restaurant.setClock(mockClockFail);
        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        generateConstantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        generateConstantData();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        generateConstantData();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void get_total_value_of_items_selected_by_user(){
        generateConstantData();
        assertEquals(688, restaurant.getAllItemNames("Vegetable lasagne", "Sweet corn soup", "French Toast"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER VALUE>>>>>>>>>>>>>>>>>>>>>>
}