package vn.edu.iuh.fit.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id){
        return addressRepository.findById(id);
    }

    @Override
    public Address createAddress(Address address){
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address){
        if(address.getId() == null){
            throw new IllegalArgumentException("Address ID cannot be null");
        }
        // Kiểm tra xem địa chỉ có tồn tại hay không
        Address existingAddress = addressRepository.findById(address.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + address.getId()));

        // Cập nhật các trường cần thiết
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setZipcode(address.getZipcode());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long id){
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found for this id :: " + id));
        addressRepository.delete(address);
    }
}
