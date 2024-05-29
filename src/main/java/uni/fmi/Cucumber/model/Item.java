package uni.fmi.Cucumber.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private boolean isDiscounted;
    private double discount;

    public void setDiscount(double discount) {
        if (!isDiscounted && discount == 0) {
            this.discount = 0;
            return;
        }

        if (discount <= 0 || 100 <= discount)
            throw new IllegalArgumentException("Промоционалният процент трябва да е положително число с максимална стойност 99");

        if (stock <= 0)
            throw new IllegalArgumentException("Не е възможно да се зададе промоционална цена на артикули, които не са в наличност");

        this.discount = discount;
    }
}
