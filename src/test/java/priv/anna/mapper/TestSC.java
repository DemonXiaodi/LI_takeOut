package priv.anna.mapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.ShoppingCart;
import priv.anna.reggie.mapper.ShoppingCartMapper;

@SpringBootTest(classes = ReggieApplication.class)
public class TestSC {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    public void test1(){

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDishId(Long.parseLong("1413385247889891330"));
        shoppingCart.setUserId(Long.parseLong("1234567890987654"));
        shoppingCart.setDishFlavor("冷藏");
        ShoppingCart existsInShoppingCart = shoppingCartMapper.findExistsInShoppingCart(shoppingCart);
        System.out.println(existsInShoppingCart);

    }
}
