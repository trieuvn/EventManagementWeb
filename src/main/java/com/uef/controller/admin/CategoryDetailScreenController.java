/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uef.controller.admin;


import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class CategoryDetailScreenController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị form sửa danh mục
    @GetMapping("/edit{id}")
    public String showEditCategoryForm(@PathVariable("id") String name, Model model) {
        CATEGORY category = categoryService.getById(name);
        if (category == null) {
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    // Lưu danh mục mới
    @PostMapping("/add")
    public String addCategory(@ModelAttribute CATEGORY category, RedirectAttributes redirectAttributes) {
        try {
            if (category.getName() == null || category.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }
            boolean saved = categoryService.set(category);
            if (saved) {
                redirectAttributes.addFlashAttribute("successMessage", "Thêm danh mục thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể thêm danh mục do lỗi hệ thống.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    // Cập nhật danh mục hiện có
    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CATEGORY category, RedirectAttributes redirectAttributes) {
        try {
            if (category.getName() == null || category.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }
            boolean saved = categoryService.set(category);
            if (saved) {
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể cập nhật danh mục do lỗi hệ thống.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}
