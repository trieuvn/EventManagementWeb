package com.uef.controller.admin;

import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ADMIN')") // Kiểm tra quyền admin (BR-26)
public class CategoryDetailScreenController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị form thêm hoặc chỉnh sửa danh mục
    @GetMapping("/{name}")
    public String showCategoryForm(@PathVariable(value = "name", required = false) String name, Model model) {
        CATEGORY category;
        if (name != null && !name.trim().isEmpty()) { // Kiểm tra name hợp lệ
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
            // Kiểm tra trường bắt buộc (BR-27)
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }
            // Kiểm tra tính duy nhất của tên danh mục (BR-27)
            CATEGORY existingCategory = categoryService.getById(category.getName());
            if (existingCategory != null && (category.getName() == null || !category.getName().equals(existingCategory.getName()))) {
                throw new IllegalArgumentException("Tên danh mục đã tồn tại.");
            }
            boolean saved = categoryService.set(category);
            redirectAttributes.addFlashAttribute("successMessage", 
                existingCategory != null ? "Cập nhật danh mục thành công!" : "Thêm danh mục thành công!");
            if (!saved) {
                throw new IllegalStateException("Không thể lưu danh mục do lỗi hệ thống.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi hệ thống khi lưu danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}