package com.techacademy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // 追加
import org.springframework.web.bind.annotation.PostMapping; // 追加
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // 追加

@Controller
@RequestMapping("country")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("countrylist", service.getCountryList());
        return "country/list";
    }

    @GetMapping(value = { "/detail", "/detail/{code}/" })
    public String getCountry(@PathVariable(name = "code", required = false) String code, Model model) {
        Country country = code != null ? service.getCountry(code) : new Country();
        model.addAttribute("country", country);
        return "country/detail";
    }

    @PostMapping("/detail")
    public String postCountry(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("population") int population, Model model) {
        // 更新（追加）
        service.updateCountry(code, name, population);

        return "redirect:/country/list";
    }

    @GetMapping(value = { "/delete", "/delete/{code}/" })
    public String deleteCountryForm(@PathVariable(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);
        return "country/delete";
    }

    @PostMapping("/delete")
    public String deleteCountry(@RequestParam("code") String code, Model model) {
        service.deleteCountry(code);

        return "redirect:/country/list";
    }
}