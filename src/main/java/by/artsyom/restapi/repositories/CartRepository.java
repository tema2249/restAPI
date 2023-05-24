package by.artsyom.restapi.repositories;

import by.artsyom.restapi.models.Cart;
import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findCartByUser(User user);

    Cart findCartByNomenclatureAndUser(Nomenclature nomenclature, User user);

}
