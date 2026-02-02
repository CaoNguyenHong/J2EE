package phattrienungdungvoij2ee.bai4_qlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import phattrienungdungvoij2ee.bai4_qlsp.model.Product;
import phattrienungdungvoij2ee.bai4_qlsp.service.CategoryService;
import phattrienungdungvoij2ee.bai4_qlsp.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public String Index(Model model) {
		model.addAttribute("listproduct", productService.getAll());
		return "product/products";
	}

	@GetMapping("/create")
	public String Create(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAll());
		return "product/create";
	}

	@PostMapping("/create")
	public String Create(
			@Valid Product newProduct,
			BindingResult result,
			@RequestParam("category.id") int categoryId,
			@RequestParam("imageProduct") MultipartFile imageProduct,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", newProduct);
			model.addAttribute("categories", categoryService.getAll());
			return "product/create";
		}
		// Xử lý ảnh
		productService.updateImage(newProduct, imageProduct);
		newProduct.setCategory(categoryService.get(categoryId));
		productService.add(newProduct);
		return "redirect:/products";
	}

	@GetMapping("/edit/{id}")
	public String Edit(@PathVariable int id, Model model) {
		Product find = productService.get(id);
		if (find == null) {
			return "error/404"; // Trang lỗi tùy chỉnh
		}
		model.addAttribute("product", find);
		model.addAttribute("categories", categoryService.getAll());
		return "product/edit";
	}

	@PostMapping("/edit")
	public String Edit(
			@Valid Product editProduct,
			BindingResult result,
			@RequestParam("category.id") int categoryId,
			@RequestParam("imageProduct") MultipartFile imageProduct,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("product", editProduct);
			model.addAttribute("categories", categoryService.getAll());
			return "product/edit";
		}
		// Cập nhật ảnh nếu có
		if (imageProduct != null && !imageProduct.isEmpty()) {
			productService.updateImage(editProduct, imageProduct);
		}
		editProduct.setCategory(categoryService.get(categoryId));
		// Cập nhật sản phẩm
		productService.update(editProduct);
		return "redirect:/products";
	}

	@PostMapping("/delete/{id}")
	public String Delete(@PathVariable int id) {
		productService.delete(id);
		return "redirect:/products";
	}
}
