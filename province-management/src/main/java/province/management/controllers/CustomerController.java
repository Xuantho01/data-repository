package province.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import province.management.model.Customer;
import province.management.model.Province;
import province.management.service.CustomerService;
import province.management.service.DuplicateEmailException;
import province.management.service.ProvinceService;
import province.management.validate.phoneNumberValidate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public List<Province> provinces() {
        return provinceService.findAll();
    }

    @GetMapping("/customer")
    public ModelAndView listCustomers(@CookieValue (value = "starredCustomer", defaultValue = "") String starredCustomer ,@RequestParam("s") Optional<String> s
            , Pageable pageable) throws Exception {

        String[] customerIds = starredCustomer.split("\\|");

        List<Customer> customerCheckStarr = customerService.findAll();
        boolean[] isStarred = new boolean[customerCheckStarr.size()];

        Page<Customer> customers = customerService.findAll(pageable);

        for (int i = 0; i < customerCheckStarr.size(); i++){
            Customer customerId = customerCheckStarr.get(i);
            for (String customer: customerIds){
                if (!customer.isEmpty() && customerId.getId() == Long.parseLong(customer)){
                    isStarred[i] = true;
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("customer/index");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("isStarred",isStarred);
        return modelAndView;
    }
//@GetMapping("/customer")
//    public ModelAndView listCustomers(@RequestParam("s") Optional<String> s
//            , Pageable pageable) throws Exception {
//
//
//
//        Page<Customer> customers = customerService.findAll(pageable);
//
//        ModelAndView modelAndView = new ModelAndView("customer/index");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }
    @PostMapping("/customer")
    public ModelAndView saveStarredCustomer(@RequestParam String starredCustomer, HttpServletResponse res){

        String savedCookieValue= starredCustomer.replace(',','|');

        res.addCookie(new Cookie("starredCustomer", savedCookieValue));

        ModelAndView modelAndView = new ModelAndView("redirect:/customer");

        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView listCustomer(@RequestParam("s") String s,
                                      @RequestParam("searchType") String type
            , Pageable pageable) throws Exception {
        Page<Customer> customers = null;
        System.out.println(type);
        if (type.equals("firstName")) {
            customers = customerService.findAllByFirstNameContaining(s, pageable);
        } else if (type.equals("lastName")) {
            customers = customerService.findAllByLastNameContaining(s, pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("customer/index");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

//    @GetMapping("/customers")
//    public ModelAndView listCustomers() {
//        List<Customer> customers = customerService.findAll();
//        ModelAndView modelAndView = new ModelAndView("customer/index");
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }

//    @PostMapping("/update")
//    public ModelAndView updateCustomer(Customer customer) {
//        try {
//            customerService.save(customer);
//            return new ModelAndView("redirect:/index");
//        } catch (DuplicateEmailException e) {
//            return new ModelAndView("customers/inputs-not-acceptable");
//        }
//    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) throws DuplicateEmailException {
        new phoneNumberValidate().validate(customer, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("customer/create");
        }
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
       // modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("customer/inputs-not-acceptable");
    }


}
