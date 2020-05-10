package province.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import province.management.model.Customer;
import province.management.model.Province;

import java.util.List;

public interface CustomerService {
   Page<Customer> findAll(Pageable pageable);

   List<Customer> findAll();

    Customer findById(Long id) throws Exception;

    Customer save(Customer customer) throws DuplicateEmailException;

    void remove(Long id);

    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);
}
