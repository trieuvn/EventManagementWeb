package com.uef.controller.admin;

import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')") // Kiểm tra quyền admin (BR-26)
public class CategoryManagementScreenController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách danh mục với lọc và phân trang cơ bản
    @GetMapping
    public String getCategoryList(Model model, 
                                  @RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        List<CATEGORY> categories;
        
        // Lọc theo tên nếu có
        if (name != null && !name.trim().isEmpty()) {
            CATEGORY category = categoryService.getById(name);
            categories = category != null ? Collections.singletonList(category) : Collections.emptyList();
        } else {
            categories = categoryService.getAll();
        }

        // Phân trang cơ bản
        int start = page * size;
        int end = Math.min(start + size, categories.size());
        List<CATEGORY> pagedCategories = start < categories.size() ? categories.subList(start, end) : Collections.emptyList();
        
        model.addAttribute("categories", pagedCategories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) categories.size() / size));
        model.addAttribute("name", name);
        return "admin/categories";
    }

    // Chuyển hướng đến form thêm danh mục
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CATEGORY());
        return "admin/categories/edit";
    }

    // Xóa danh mục
    @GetMapping("/delete/{name}")
    public String deleteCategory(@PathVariable("name") String name, RedirectAttributes redirectAttributes) {
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