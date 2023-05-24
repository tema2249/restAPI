package by.artsyom.restapi.repositories;

import by.artsyom.restapi.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {


}
