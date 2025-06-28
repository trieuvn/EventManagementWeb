package com.uef.controller.admin;

import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách danh mục với tìm kiếm
    @GetMapping
    public String listCategories(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("categories", categoryService.searchByName(name));
        model.addAttribute("name", name);
        return "admin/categories";
    }

    // Hiển thị form thêm danh mục
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CATEGORY());
        return "admin/categories/edit";
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("/edit/{name}")
    public String showEditCategoryForm(@PathVariable String name, Model model) {
        CATEGORY category = categoryService.getById(name);
        if (category == null) {
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        return "admin/categories/edit";
    }

    // Lưu hoặc cập nhật danh mục
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute CATEGORY category, RedirectAttributes redirectAttributes) {
        try {
            boolean saved = categoryService.set(category);
            redirectAttributes.addFlashAttribute("successMessage", 
                categoryService.getById(category.getName()) != null ? "Cập nhật danh mục thành công!" : "Thêm danh mục thành công!");
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

    // Xóa danh mục
    @GetMapping("/delete/{name}")
    public String deleteCategory(@PathVariable String name, RedirectAttributes redirectAttributes) {
        try {
            CATEGORY category = categoryService.getById(name);
            if (category != null) {
                boolean deleted = categoryService.delete(category);
                if (deleted) {
                    redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
                } else {
                    throw new IllegalStateException("Không thể xóa danh mục do lỗi hệ thống.");
                }
            } else {
                throw new IllegalArgumentException("Danh mục không tồn tại.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}