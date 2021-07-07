package ru.geekbrains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String allProduct(@RequestParam(value = "minPrice") Optional<BigDecimal> minPrice,
                             @RequestParam(value = "maxPrice") Optional<BigDecimal> maxPrice,
                             @RequestParam(value = "page") Optional<Integer> page,
                             @RequestParam(value = "size") Optional<Integer> size,
                             Model model) {
        model.addAttribute("activePage", "Products");
        model.addAttribute("productPage", productService.findAllByPriceBetween(minPrice, maxPrice,
                PageRequest.of(page.orElse(1) - 1, size.orElse(5))));
        model.addAttribute("minPrice", minPrice.orElse(null));
        model.addAttribute("maxPrice", maxPrice.orElse(null));
        return "products";
    }
}
