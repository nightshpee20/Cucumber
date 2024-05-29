package uni.fmi.Cucumber.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public enum Status {
        NEW("Нова поръчка"),
        BEING_PROCESSED("Обработва се"),
        CANCELLED("Отменена"),
        OUT_OF_STOCK("Някой от артикулите не са налични"),
        COMPLETED("Изпълнена");

        private final String description;

        Status(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private Long id;
    private List<Item> items;
    private double total;
    private LocalDate date;
    private Employee processor;
    private Status status;
}
