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

    // Hiển thị form thêm hoặc sửa danh mục
    @GetMapping({"/add", "/edit{id}"})
    public String showCategoryForm(@PathVariable(value = "id", required = false) String name, Model model) {
        CATEGORY category;
        if (name != null) {
            category = categoryService.getById(name);
            if (category == null) {
                model.addAttribute("errorMessage", "Danh mục không tồn tại.");
                return "admin/categories";
            }
        } else {
            category = new CATEGORY();
        }
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    // Lưu hoặc cập nhật danh mục
    @PostMapping({"/add", "/update"})
    public String saveCategory(@ModelAttribute CATEGORY category, RedirectAttributes redirectAttributes) {
        try {
            if (category.getName() == null || category.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }
            boolean saved = categoryService.set(category);
            if (saved) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    categoryService.getById(category.getName()) != null ? "Cập nhật danh mục thành công!" : "Thêm danh mục thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Không thể lưu danh mục do lỗi hệ thống.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}
