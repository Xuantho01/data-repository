package province.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import province.management.model.Customer;
import province.management.model.Province;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

   Iterable<Customer> findAllByProvince(Province province);
   Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);
//   Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable);
}
