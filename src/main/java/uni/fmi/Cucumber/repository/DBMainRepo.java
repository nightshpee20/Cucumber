package uni.fmi.Cucumber.repository;

import uni.fmi.Cucumber.model.Employee;
import uni.fmi.Cucumber.model.Item;
import uni.fmi.Cucumber.model.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBMainRepo {
    private static List<Item> items = new ArrayList<>();
    private static List<Employee> employees = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    static {
        Item item1 = new Item(1L, "Пица Маргарита", 12.99, 8, false, 0);
        Item item2 = new Item(2L, "Суши сет", 19.99, 0, false, 0);
        Item item3 = new Item(3L, "Фанта 500мл", 1.99, 7, false, 0);
        Item item4 = new Item(4L, "Кока-кола 500мл", 1.99, 5, false, 0);
        Item item5 = new Item(5L, "Кафе", 4, 8, true, 0.25);

        Employee employee1 = new Employee(1L, "Георги");
        Employee employee2 = new Employee(2L, "Станислав");

        Order order1 = new Order(1L, List.of(item1, item3, item4), 16.97, LocalDate.of(2024, 5, 28), employee1, Order.Status.COMPLETED);
        Order order2 = new Order(2L, List.of(item5, item5, item4), 7.97, LocalDate.of(2024, 5, 27), employee2, Order.Status.BEING_PROCESSED);
        Order order3 = new Order(3L, List.of(item2, item1, item3, item3), 31.96, LocalDate.of(2024, 5, 28), employee2, Order.Status.CANCELLED);
        Order order4 = new Order(4L, List.of(item4, item4, item4), 5.97, LocalDate.of(2024, 5, 29), employee1, Order.Status.COMPLETED);
        Order order5 = new Order(5L, List.of(item5), 4, LocalDate.of(2024, 5, 29), employee1, Order.Status.BEING_PROCESSED);

        items.addAll(List.of(item1,item2,item3,item4,item5));
        employees.addAll(List.of(employee1, employee2));
        orders.addAll(List.of(order1, order2, order3, order4, order5));
    }

    public static List<Item> getItems() {
        List<Item> copiedList = new ArrayList<>();
        copiedList.addAll(items);
        return items;
    }

    public static List<Employee> getEmployees() {
        return employees;
    }
    public static List<Order> getOrders() {
        return orders;
    }
}
