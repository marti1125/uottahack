package com.uothackk.app.forum.persistance;

import com.uothackk.app.forum.Category;
import com.uothackk.app.forum.CategoryService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class JpaCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public JpaCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> categories() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(this::mapCategory)
                .collect(Collectors.toList());
    }

    Category mapCategory(CategoryEntity entity) {
        return new Category(entity.getId(), entity.getName());
    }

}
