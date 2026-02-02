package phattrienungdungvoij2ee.bai4_qlsp.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import phattrienungdungvoij2ee.bai4_qlsp.model.Category;

@Service
public class CategoryService {
	private final List<Category> listCategory = new ArrayList<>();

	@PostConstruct
	public void init() {
		listCategory.add(new Category(1, "Điện thoại"));
		listCategory.add(new Category(2, "Laptop"));
		listCategory.add(new Category(3, "Phụ kiện"));
	}

	public List<Category> getAll() {
		return listCategory;
	}

	public Category get(int id) {
		return listCategory.stream()
				.filter(c -> c.getId() == id)
				.findFirst()
				.orElse(null);
	}
}
