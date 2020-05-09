package province.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import province.management.model.Customer;
import province.management.model.Province;
import province.management.service.CustomerService;
import province.management.service.ProvinceService;

import java.util.List;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/provinces")
    public ModelAndView listProvinces(){
        List<Province> provinces  = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("province/index");
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/create-province")
    public ModelAndView showCreateForm(){

        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province", new Province());
        return modelAndView;

    }

    @PostMapping("/create-province")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province){

        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province", new Province());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;

    }

    @GetMapping("/view-province/{id}")
    public ModelAndView showProvinceDetail(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView("province/view");

        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);

        return modelAndView;
    }
    @GetMapping("{id}")
    public ModelAndView showInformation(@PathVariable Long id) {
        try {
            ModelAndView modelAndView = new ModelAndView("province/view");
            Province province = null;
             province = provinceService.findById(id);
            modelAndView.addObject("customers", province);
            return modelAndView;
        } catch (Exception e) {
            return new ModelAndView("redirect:/index");
        }
    }

}