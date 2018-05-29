import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.RealItem;

public class RealItemTest {

    private RealItem realItem;

    @ParameterizedTest
    @CsvSource({"Class: class shop.RealItem; Name: Audi; Price: 32026.9; Weight: 1560.0, Audi, 32026.9, 1560",
            "Class: class shop.RealItem; Name: BMW; Price: 22103.9; Weight: 1400.0, BMW, 22103.9, 1400.0"})
    void realItemToStringMethodTest(String rightValue, String name, double price, double weight) {
        realItem = new RealItem();
        realItem.setWeight(weight);
        realItem.setName(name);
        realItem.setPrice(price);
        Assertions.assertEquals(rightValue, realItem.toString());
    }
}
