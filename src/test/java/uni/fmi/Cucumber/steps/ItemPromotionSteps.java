package uni.fmi.Cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uni.fmi.Cucumber.model.Item;
import uni.fmi.Cucumber.repository.DBMainRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemPromotionSteps {
    private Item item;
    private List<Item> items = DBMainRepo.getItems();
    private List<Item> selectedItems;
    private Exception caughtException;
    @Given("служителят избира артикул с идентификационен номер {int}")
    public void employeeSelectsItem(Integer id) {
        for (Item item1 : items)
            if (item1.getId().intValue() == id)
                item = item1;
    }
    @When("служителят зададе процент на промоцията {int}")
    public void employeeSetsDiscountPercent(Integer percent) {
        try {
            item.setDiscount(0.01 * percent);
            if (!item.isDiscounted())
                item.setDiscounted(true);
        } catch (Exception e) {
          caughtException = e;
        }
    }

    @Then("артикулът трябва да бъде обозначен, че е в промоция")
    public void checkIfItemIsFlaggedForDiscount() {
        assertTrue(item.isDiscounted());
    }

    @Given("избраният артикулът не е наличен")
    public void itemIsNotInStock() {
        assertEquals(0, item.getStock());
    }

    @Then("служителят трябва да бъде уведомен, че {string}")
    public void cannotSetDiscountToOutOfStockItems(String message) {
        assertEquals(IllegalArgumentException.class, caughtException.getClass());
        assertEquals(message, caughtException.getMessage());
    }

    @When("служителят натиска бутон Премахни Всички Промоции")
    public void removeAllDiscounts() {
        items.forEach(item -> {
            item.setDiscounted(false);
            item.setDiscount(0);
        });
    }

    @Then("системата премахва промоциите на всички артикули")
    public void systemRemovesAllDiscounts() {
        items.forEach(item -> {
            assertFalse(item.isDiscounted());
            assertEquals(0, item.getDiscount());
        });
    }

    @Given("служителят маркира артикулите {string}, {string}, {string}")
    public void employeeSelectsItems(String name1, String name2, String name3) {
        selectedItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getName().equals(name1))
                selectedItems.add(item);
            if (item.getName().equals(name2))
                selectedItems.add(item);
            if (item.getName().equals(name3))
                selectedItems.add(item);
        }

        assertTrue(0 < selectedItems.size());
    }

    @When("служителят зададе процент на промоцията {int} наведнъж на всички")
    public void setDiscountPercentageAtOnce(Integer percent) {
        selectedItems.forEach(item -> {
            item.setDiscounted(true);
            item.setDiscount(0.01 * percent);
        });
    }

    @Then("всички артикули трябва да станат промоционални с процент {int}")
    public void allItemsAreDiscounted(Integer percent) {
        selectedItems.forEach(item -> {
            assertTrue(item.isDiscounted());
            assertEquals(0.01 * percent, item.getDiscount());
        });
    }
}
