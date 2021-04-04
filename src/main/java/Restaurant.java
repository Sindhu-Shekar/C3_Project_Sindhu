import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.Clock;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private Clock clock;

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        initDefaultClock();
    }

    public boolean isRestaurantOpen() {
        if (getCurrentTime().isAfter(closingTime) || getCurrentTime().isBefore(openingTime)) {
            return false;
        } else {
            return true;
        }
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(clock); }

    public List<Item> getMenu() {
        return Collections.unmodifiableList(menu);
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public void setClock(Clock newClock) {
        clock = newClock;
    }

    public void initDefaultClock() {
        setClock(
                Clock.system(
                        Clock.systemDefaultZone().getZone()
                )
        );
    }

    public double getAllItemNames(String...itemNames){
        double allItemsCheck = 0.0;
        for(String itemName : itemNames){
            allItemsCheck += getOrderValueByName(itemName);
        }
        return allItemsCheck;
    }

    public double getOrderValueByName(String getItemName){
        double totalPrice = 0.0;
        for(Item item : menu){
            if (item.getName().equals(getItemName))
                totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
