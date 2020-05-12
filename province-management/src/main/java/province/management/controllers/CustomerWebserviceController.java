package province.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import province.management.model.Customer;
import province.management.service.CustomerService;
import province.management.service.DuplicateEmailException;

import java.util.List;

@RestController
public class CustomerWebserviceController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/api/customers")
    public ResponseEntity<List<Customer>> listAllCustomer(UriComponentsBuilder ucBuilder){
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }
//        return res;
        ResponseEntity<List<Customer>> res = new ResponseEntity(customers, HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{Id}").buildAndExpand(customers.get(1).getId()).toUri());

        return res;
    }

    @PostMapping("/api/customers")
    public ResponseEntity<Void> showAllCustomer(@RequestBody Customer customer) throws DuplicateEmailException {
        customerService.save(customer);
        ResponseEntity<Void> res = new ResponseEntity(HttpStatus.OK);
        return res;
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable ("id") long id,@RequestBody Customer customer) throws Exception {
        Customer currentCustomer = customerService.findById(id);

        if (currentCustomer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND); }

        customer.setId(id);

        customerService.save(customer);

        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/api/delete/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable ("id") long id) throws Exception {

        Customer currentCustomer = customerService.findById(id);

        if (currentCustomer == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND); }

        customerService.remove(id);

        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
    }

    @GetMapping(value = "/api/findCustomer/customers/{id}")
    public ResponseEntity<Customer> listAllCustomers(@PathVariable("id") long id) throws Exception {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }


}
