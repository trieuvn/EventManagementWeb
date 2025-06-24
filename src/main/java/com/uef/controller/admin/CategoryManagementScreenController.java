
package com.uef.controller.admin;


import com.uef.model.CATEGORY;
import com.uef.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class CategoryManagementScreenController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách danh mục
    @GetMapping
    public String getCategoryList(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories";
    }

    // Chuyển hướng đến màn hình thêm danh mục
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CATEGORY());
        return "admin/categories/edit";
    }

    // Xóa danh mục
    @GetMapping("/delete{id}")
    public String deleteCategory(@PathVariable("id") String name, RedirectAttributes redirectAttributes) {
        try {
            CATEGORY category = categoryService.getById(name);
            if (category != null) {
                boolean deleted = categoryService.delete(category);
                if (deleted) {
                    redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa danh mục do lỗi hệ thống.");
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại.");
            }
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa danh mục vì đang được sử dụng trong tag.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xóa danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}
