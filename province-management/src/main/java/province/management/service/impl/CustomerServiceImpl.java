package province.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import province.management.model.Customer;
import province.management.model.Province;
import province.management.repository.CustomerRepository;
import province.management.service.CustomerService;
import province.management.service.DuplicateEmailException;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

//    @Override
//    public Page<Customer> findAll(Pageable pageable) {
//        return customerRepository.findAll(pageable);
//    }

@Override
public List<Customer> findAll() {
    return (List<Customer>) customerRepository.findAll();
}

    @Override
    public Customer findById(Long id) throws Exception {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }
        throw new Exception("Not found customer has which id is "+ id);
    }

    @Override
    public Customer save(Customer customer) throws DuplicateEmailException{
        try {
            return customerRepository.save(customer);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateEmailException();
        }
    }

    @Override
    public void remove(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAllByProvince(Province province) {
        return customerRepository.findAllByProvince(province);
    }

//    @Override
//    public Page<Customer> findAllByFirstNameContaining(String firstname, Pageable pageable) {
//        return customerRepository.findAllByFirstNameContaining(firstname,pageable);
//    }

}
