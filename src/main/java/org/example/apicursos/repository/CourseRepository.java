package org.example.apicursos.repository;

import org.example.apicursos.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    List<CourseModel> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);

    List<CourseModel> findByNameContainingIgnoreCase(String name);

    List<CourseModel> findByCategoryContainingIgnoreCase(String category);
}
