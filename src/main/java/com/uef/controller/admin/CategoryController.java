package com.uef.controller.admin;

import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/edit/{name}")
    public String editCategoryForm(@PathVariable String name, Model model) {
        CATEGORY category = categoryService.getById(name);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/edit_category";
    }

    @PostMapping("/delete/{name}")
    public String deleteCategory(@PathVariable String name) {
        CATEGORY category = categoryService.getById(name);
        if (category != null) {
            categoryService.delete(category);
        }
        return "redirect:/admin/categories";
    }
}