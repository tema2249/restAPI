package by.artsyom.restapi.repositories;

import by.artsyom.restapi.models.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NomenclatureRepository extends JpaRepository<Nomenclature, Long> {
    List<Nomenclature> findByTitle(String title);
}
