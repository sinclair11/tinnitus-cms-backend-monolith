package com.tinnitussounds.cms.category;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.tinnitussounds.cms.config.Constants;
import com.tinnitussounds.cms.services.StorageService;

@RestController
public class CategoryController {
    @Autowired
    private CategoryRepository categoriesRepository;
    private StorageService storageService;

    public CategoryController(CategoryRepository categoriesRepository, StorageService storageService) {
        this.categoriesRepository = categoriesRepository;
        this.storageService = storageService;
    }

    @GetMapping("/api/admin/categories{which}")
    public ResponseEntity<?> getCategories(@RequestParam("which") String which) {
        List<Category> categories = categoriesRepository.findAll();
        CategoryPack categoryPack = new CategoryPack(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<Category> categoryList = new ArrayList<Category>();

        if (which.equals("album")) {
            for (Category category : categories) {
                if (category.getType().equals("album")) {
                    categoryList.add(category);
                }
            }
        } else if (which.equals("preset")) {
            for (Category category : categories) {
                if (category.getType().equals("preset")) {
                    categoryList.add(category);
                }
            }
        } else if (which.equals("sample")) {
            for (Category category : categories) {
                if (category.getType().equals("sample")) {
                    categoryList.add(category);
                }
            }
        } else if (which.equals("all")) {
            for (Category category : categories) {
                if (category.getType().equals("album")) {
                    categoryPack.getAlbumCategories().add(category);
                } else if (category.getType().equals("preset")) {
                    categoryPack.getPresetCategories().add(category);
                } else if (category.getType().equals("sample")) {
                    categoryPack.getSampleCategories().add(category);
                }
            }
        }

        if (which.equals("all"))
            return ResponseEntity.ok().body(categoryPack);
        else
            return ResponseEntity.ok().body(categoryList);
    }

    @GetMapping("/api/admin/categories/category{name}")
    public ResponseEntity<Category> getCategory(@RequestParam("name") String name) {
        List<Category> categories = categoriesRepository.findAll();

        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return ResponseEntity.ok().body(category);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/api/admin/categories", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCategory(@RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("color") String color,
            @RequestParam("type") String type,
            @RequestParam MultipartFile file) {
        
        Category category = new Category("", name, description, color, type);
        category = categoriesRepository.insert(category);
        try {
            String id = category.getId();
            storageService.putFile(Constants.categoriesPath + "/" + id, id + ".jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().body("Could not create category item");
        }
        return ResponseEntity.status(201).body("Category added successfully");
    }

    @PutMapping("/api/admin/categories")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        Optional<Category> categoryObj = categoriesRepository.findById(category.getId());

        if (categoryObj.isPresent()) {
            categoriesRepository.save(category);
            return ResponseEntity.ok().body("Category updated successfuly");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/admin/categories/category{id}")
    public ResponseEntity<String> deleteCategory(@RequestParam("id") String id) {
        Optional<Category> category = categoriesRepository.findById(id);
        if (category.isPresent()) {
            categoriesRepository.delete(category.get());
            try {
                storageService.deleteFolder(Constants.categoriesPath + "/" + id);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Could not delete category");
            }
        } else {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("Category deleted successfuly");
    }
}