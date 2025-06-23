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

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CATEGORY());
        return "admin/edit_category";
    }

    @GetMapping("/edit/{name}")
    public String editCategoryForm(@PathVariable String name, Model model) {
        CATEGORY category = categoryService.getById(name);
        if (category == null) {
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/edit_category";
    }

    @PostMapping({"/add", "/update"})
    public String saveCategory(@ModelAttribute CATEGORY category) {
        categoryService.set(category);
        return "redirect:/admin/categories";
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