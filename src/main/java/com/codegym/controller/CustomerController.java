package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "Created success !");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        List<Customer> customers = customerService.findAll();
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null) {
            modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
        } else {
            modelAndView = new ModelAndView("/customer/notFound");
        }
        return modelAndView;
    }

    @PostMapping("/edit-customer")
    public ModelAndView edit(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("message", "Updated success!");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null) {
            modelAndView = new ModelAndView("/customer/delete");
            return modelAndView;
        } else {
            modelAndView = new ModelAndView("/customer/notFound");
            return modelAndView;
        }
    }

    @PostMapping("/delete-customer")
    public ModelAndView delete(@ModelAttribute("customer") Customer customer) {
        customerService.remove(customer.getId());
        return new ModelAndView("/customer/list");
    }

}
