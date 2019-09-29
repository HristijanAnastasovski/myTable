package com.finki.MyTable;

import com.finki.MyTable.model.Customer;
import com.finki.MyTable.model.factory.CustomerFactory;
import com.finki.MyTable.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByEmail_thenReturnCustomer() {
        String email = "test@mail.com";
        Customer customer = CustomerFactory.create("testCust", "LastNameTest", email, "pwd123");

        Optional<Customer> found = customerRepository.findByEmail(email);

        found.ifPresent(foundCustomer -> {
            assertThat(customer).isEqualTo(foundCustomer);
        });
    }
}
