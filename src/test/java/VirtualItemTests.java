import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shop.VirtualItem;

public class VirtualItemTests {


    private VirtualItem virtualItem;


    @ParameterizedTest
    @CsvSource({"Class: class shop.VirtualItem; Name: Windows; Price: 11.0; Size on disk: 20000.0, Windows, 11.0, 20000.0",
            "Class: class shop.VirtualItem; Name: Microsoft office; Price: 30.0; Size on disk: 8500.0, Microsoft office, 30.0, 8500.0"})
    void virtualItemToStringMethodTest(String rightValue, String name, double price, double size) {
        virtualItem = new VirtualItem();

        virtualItem.setName(name);
        virtualItem.setPrice(price);
        virtualItem.setSizeOnDisk(size);

        Assertions.assertEquals(rightValue, virtualItem.toString());

    }
}
