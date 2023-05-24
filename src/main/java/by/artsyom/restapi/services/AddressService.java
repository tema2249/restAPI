package by.artsyom.restapi.services;

import by.artsyom.restapi.models.Address;
import by.artsyom.restapi.models.Cart;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> listAddress(User user){
        List<Address> addresses = addressRepository.findAddressesByUser(user);
        if (user.getAddress() != null) {
            addresses.remove(user.getAddress());
            addresses.add(0, user.getAddress());
        }
        if (user.isManeger() == false) {
            List<Address> listPickUpPoint = addressRepository.findAddressByPickUpPoint(true);
            addresses.addAll(listPickUpPoint);
        }

        return addresses;
    }

    public void save(String fullAddress, User user) {
        Address address = new Address();
        address.setFullAddress(fullAddress);
        address.setUser(user);
        if (user.isManeger()){
            address.setPickUpPoint(true);
        }
        if (addressRepository.findAddressesByUser(user).size() == 0 & user.isManeger() == false) {
            address.setMain(true);
        }
        addressRepository.save(address);
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }

    public void setMain(Address address, User user) {
        List<Address> addresses = addressRepository.findAddressesByUser(user);
        for (Address addressFor : addresses) {
            addressFor.setMain(false);
        }
        user.setAddress(address);
        address.setMain(true);
        addressRepository.save(address);
    }
}
