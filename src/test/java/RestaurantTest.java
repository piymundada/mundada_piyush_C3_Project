import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    @Spy
    @InjectMocks
    private static Restaurant restaurant;

    @BeforeAll
    public static void beforeAll() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:30:00"));
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:00:00"));
        assertFalse(restaurant.isRestaurantOpen());
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>Total Order value<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void total_order_value_should_be_correct_when_order_contains_multiple_items() {
        assertEquals(388, restaurant.getTotalOrderValue(Arrays.asList("Sweet corn soup", "Vegetable lasagne")));
    }

    @Test
    public void total_order_value_should_be_correct_when_order_contains_single_item() {
        assertEquals(119, restaurant.getTotalOrderValue(Arrays.asList("Sweet corn soup")));
    }

    @Test
    public void total_order_value_should_be_zero_when_order_is_empty() {
        assertEquals(0, restaurant.getTotalOrderValue(Arrays.asList()));
    }


    //<<<<<<<<<<<<<<<<<<<<Total Order value>>>>>>>>>>>>>>>>>>>>>>>>>>
}