package uni.fmi.Cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.fmi.Cucumber.model.Item;
import uni.fmi.Cucumber.model.Order;
import uni.fmi.Cucumber.repository.DBMainRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessOrderSteps {
    private String itemName;
    private Order order;
    private DBMainRepo repo;
    private List<Item> items = DBMainRepo.getItems();
    private List<Order> orders = DBMainRepo.getOrders();

    @Given("клиентът е направил поръчка с артикул {string}")
    public void createOrderWithItem(String name) {
        order = new Order();
        Item orderedItem = null;
        for (Item item : items)
            if (item.getName().equals(name))
                orderedItem = item;
        assertNotNull(orderedItem);
        order.setItems(List.of(orderedItem));
        order.setStatus(Order.Status.NEW);
    }

    @When("служителят приеме поръчката")
    public void acceptOrder() {
        order.setStatus(Order.Status.BEING_PROCESSED);
    }

    @Then("поръчката трябва да бъде маркирана като {string}")
    public void orderChangesStatusTo(String status) {
        assertEquals(status, order.getStatus().toString());
    }

    @Given("артикулът не е наличен")
    public void itemIsOutOfStock() {
        for (Item item : order.getItems()) {
            if (item.getStock() == 0) {
                order.setStatus(Order.Status.OUT_OF_STOCK);
                break;
            }
        }
    }

    @When("служителят опита да приеме поръчката")
    public void employeeTriesToAcceptOrder() {
        order.setStatus(Order.Status.BEING_PROCESSED);
    }

    @Then("системата трябва да смени статуса на поръчката на {string}")
    public void checkIfStatusIsChangedToOutOfStock(String status) {
        assertEquals(status, order.getStatus().toString());
    }

    @Given("клиентът е направил поръчка с артикул {string}, който е с цена 4 лева и на промоция 25%")
    public void clientOrdersDiscountedItem(String name) {
        order = new Order();
        Item orderedItem = null;
        for (Item item : items)
            if (item.getName().equals(name))
                orderedItem = item;

        assertNotNull(orderedItem);
        order.setItems(List.of(orderedItem));

        double total = 0;

        for (Item item : order.getItems())
            if (item.isDiscounted())
                total =+ item.getPrice() - item.getPrice() * item.getDiscount();
            else
                total =+ item.getPrice();

        order.setTotal(total);
    }

    @Given("клиентът поиска промяна на поръчка, която е направил само с един артикул")
    public void clientHasMadeOrderOnlyWithItem() {
        for (Order order1 : orders)
            if (order1.getItems().size() == 1)
                order = order1;

        assertNotNull(order);
    }

    @Then("цената на поръчката трябва да включва промоционалната отстъпка")
    public void totalIncludesDiscountedPrice() {
        double total = order.getTotal();
        assertEquals(3, total);
    }

    @Given("клиентът е направил поръчка с артикули {string}, {string} и {string}")
    public void clientHasOrdered(String name1, String name2, String name3) {
        List<Item> orderedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(name1))
                orderedItems.add(item);
            if (item.getName().equals(name2))
                orderedItems.add(item);
            if (item.getName().equals(name3))
                orderedItems.add(item);
        }

        for (Order order : orders) {
            List<Item> items = order.getItems();
            if (items.containsAll(orderedItems)) {
                this.order = order;
                break;
            }
        }

        assertNotNull(order);
    }

    @Given("поръчката е в статус {string}")
    public void orderIsInStatus(String string) {
        assertEquals(string, order.getStatus().toString());
    }

    @When("клиентът уведоми че иска да откаже поръчката и служителят я откаже")
    public void employeeCancelsTheOrder() {
        order.setStatus(Order.Status.CANCELLED);
    }

    @Then("служителят прави нужната промяна като заменя поръчаният артикул с {string}")
    public void modifyOrder(String name) {
        Item newItem = null;
        for (Item item : items)
            if (item.getName().equals(name))
                newItem = item;

        assertNotNull(newItem);
        order.setItems(List.of(newItem));
    }

    @Then("статусът на поръчката трябва да остане {string}")
    public void makeSureStatusIsUnchanged(String string) {
        assertEquals(string, order.getStatus().toString());
    }
}
