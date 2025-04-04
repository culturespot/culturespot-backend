package com.culturespot.culturespotdomain.core.image.repository;

import com.culturespot.culturespotdomain.core.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
