package priv.anna.mapper;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.anna.reggie.ReggieApplication;
import priv.anna.reggie.domain.AddressBook;
import priv.anna.reggie.mapper.AddressBookMapper;
import priv.anna.reggie.service.AddressBookService;

@SpringBootTest(classes = ReggieApplication.class)
public class TestAddress {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private AddressBookService addressBookService;

    @Test
    public void test1(){
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(Long.parseLong("1417012167126876162"));

        Boolean aBoolean = addressBookMapper.setNonDefault(addressBook);
        System.out.println(aBoolean);
    }
}
