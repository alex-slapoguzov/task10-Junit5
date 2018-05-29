import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import parser.JsonParser;
import parser.Parser;
import shop.Cart;


import java.io.File;


public class JsonParserTest {

    private Parser jsonParser;
    private Cart cart;

    @BeforeEach
    void setUp() {
        jsonParser = new JsonParser();
    }

    @AfterEach
    void tearDown() {
        String pathName = "src/main/resources/";

        File file = new File(pathName);
        for (File item : file.listFiles()) {
            if (!(item.getName().equals("andrew-cart.json")
                    | item.getName().equals("eugen-cart.json"))) {

                item.delete();
            }
        }
    }

    @Nested
    class writeToFileTest {
        @ParameterizedTest
        @ValueSource(strings = {"alexey-cart", "111BBvv6", "!@#$%^&"})
        void writeToFileIsFilePresentPositive(String cartName) {
            String fileName = cartName + ".json";
            cart = new Cart(cartName);

            jsonParser.writeToFile(cart);

            Assertions.assertTrue(isFilePresent(fileName), "File isn't created!");
        }

        @ParameterizedTest
        @ValueSource(strings = {"con", ">aaa", "||aaaa", "***aaaa", "<aaaa"})
        void writeToFileIsFilePresentNegative(String cartName) {
            String fileName = cartName + ".json";
            cart = new Cart(cartName);

            jsonParser.writeToFile(cart);

            Assertions.assertFalse(isFilePresent(fileName), "File is created!");
        }

        @Disabled
        @ParameterizedTest
        @ValueSource(strings = {"alexey-cart", "111BBvv6", "!@#$%^&"})
        void writeToFileNotEmptyFilePositive(String cartName) {
            String fileName = cartName + ".json";
            cart = new Cart(cartName);

            jsonParser.writeToFile(cart);

            Assertions.assertTrue(isFileNotEmpty(fileName), "File is empty!");
        }
    }

    @Nested
    class readFromFileTest {

        @ParameterizedTest
        @CsvSource({"src/test/resources/andrew-cart.json, igor-cart, andrew-cart, 38445.479999999996", "src/main/resources/eugen-cart.json, igor-cart, eugen-cart, 26560.68"})
        void readFromFilePositive(String path, String initCartName, String cartName, double totalPrice) {
            cart = new Cart(initCartName);
            String firstCartName = cart.getCartName();
            double firsTotalPrice = cart.getTotalPrice();

            cart = jsonParser.readFromFile(new File(path));

            Assertions.assertAll(
                    () -> Assertions.assertNotEquals(firstCartName, cart.getCartName()),
                    () -> Assertions.assertEquals(cartName, cart.getCartName()),

                    () -> Assertions.assertNotEquals(firsTotalPrice, cart.getTotalPrice()),
                    () -> Assertions.assertEquals(totalPrice, cart.getTotalPrice())
            );
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"src/test/resources/text.csv", "src/main/resources/andrew-cart.jso", "eugen-cart", "src/test/resources/map.txt", "src/main/resources/eugen-cart"})
    void readFromFileIncorrectTypeExceptionTest(String fileName) {
        File file = new File(fileName);
        Throwable throwable = Assertions.assertThrows(parser.NoSuchFileException.class, () ->
                jsonParser.readFromFile(file));
        Assertions.assertEquals(throwable.getMessage(), String.format("File %s.json not found!", file));
    }


    public boolean isFilePresent(String name) {
        File file = new File("src/main/resources/" + name);
        return file.exists();
    }

    public boolean isFileNotEmpty(String name) {
        File file = new File("src/main/resources/" + name);
        return file.length() > 0;
    }
}
