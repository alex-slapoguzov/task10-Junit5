import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import parser.JsonParser;
import parser.Parser;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;

import java.io.File;


public class CartTest {

    private Cart cart;
    private RealItem realItem;
    private VirtualItem virtualItem;
    private Parser parser;

    @ParameterizedTest
    @CsvSource({"src\\test\\resources\\andrew-cart.json, 38445.48", "src\\test\\resources\\eugen-cart.json, 26560.68"})
    void getTotalPriceWithParserClassPositiveTest(String pathName, double totalPrice) {
        parser = new JsonParser();
        cart = parser.readFromFile(new File(pathName));
        Assertions.assertEquals(totalPrice, cart.getTotalPrice(), 0.1);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "\\index.csv")
    void getTotalPriceWithDataPositiveTest(double priceRealItem, double priceVirtualItem, double totalPrice) {
        cart = new Cart("igor-cart");

        realItem = new RealItem();
        virtualItem = new VirtualItem();

        realItem.setPrice(priceRealItem);
        virtualItem.setPrice(priceVirtualItem);

        cart.addRealItem(realItem);
        cart.addVirtualItem(virtualItem);

        Assertions.assertEquals(totalPrice, cart.getTotalPrice());
    }
}
