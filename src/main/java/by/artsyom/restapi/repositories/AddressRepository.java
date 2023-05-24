package by.artsyom.restapi.repositories;

import by.artsyom.restapi.models.Address;
import by.artsyom.restapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAddressesByUser(User user);
    List<Address> findAddressByPickUpPoint(boolean PickUpPoint);

    Address findAddressByIdAndUser(Long id, User user);
}
