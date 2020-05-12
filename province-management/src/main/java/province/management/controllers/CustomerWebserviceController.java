package province.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import province.management.model.Customer;
import province.management.service.CustomerService;
import province.management.service.DuplicateEmailException;

import java.util.List;

@RestController
public class CustomerWebserviceController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/API/customer")
    public ResponseEntity<List<Customer>> listAllCustomer(){
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
//        return res;
        ResponseEntity<List<Customer>> res = new ResponseEntity(customers, HttpStatus.OK);
        return res;
    }

    @PostMapping("/API/customer")
    public ResponseEntity<Void> showAllCustomer(@RequestBody Customer customer) throws DuplicateEmailException {
        customerService.save(customer);
        ResponseEntity<Void> res = new ResponseEntity(HttpStatus.OK);
        return res;
    }

}
