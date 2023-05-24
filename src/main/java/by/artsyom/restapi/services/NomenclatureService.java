package by.artsyom.restapi.services;

import by.artsyom.restapi.models.Image;
import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.models.User;
import by.artsyom.restapi.repositories.NomenclatureRepository;
import by.artsyom.restapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NomenclatureService {
    private final UserRepository userRepository;
    private final NomenclatureRepository nomenclatureRepository;
    private List<Nomenclature> nomenclatures = new ArrayList<>();

    public List<Nomenclature> listNomenclatures(String title){
        if (title != null) return nomenclatureRepository.findByTitle(title);
        return nomenclatureRepository.findAll();
    }
    public void saveNomenclature(Principal principal, Nomenclature nomenclature, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        nomenclature.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            nomenclature.addImageToNomenclature(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            nomenclature.addImageToNomenclature(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file2);
            nomenclature.addImageToNomenclature(image3);
        }
        log.info("добавлен {}", nomenclature.getTitle());
        Nomenclature nomenclatureFromDb = nomenclatureRepository.save(nomenclature);
        List<Image> images = nomenclatureFromDb.getImages();
        if (images.size() >= 1){
            nomenclature.setPreviewImageId(images.get(0).getId());
        }
        nomenclatureRepository.save(nomenclature);
        nomenclatureFromDb.setPreviewImageId(nomenclatureFromDb.getImages().get(0).getId());
        nomenclatureRepository.save(nomenclature);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByLogin(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteNomenclatures(Long id){
        nomenclatureRepository.deleteById(id);
    }

    public Nomenclature getNomenclatureById(Long id) {
        return nomenclatureRepository.findById(id).orElse(null);
    }

    public Nomenclature createNomenclature(String title, int price) {
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setTitle(title);
        nomenclature.setPrice(price);
        nomenclatureRepository.save(nomenclature);
        return nomenclature;
    }

    public Nomenclature updateNomenclature(Long id, String title, int price) {
        Optional<Nomenclature> nomenclature= Optional.ofNullable(nomenclatureRepository.findById(id)
                .orElse(null));
        if (nomenclature == null) {
            return null;
        };
        nomenclature.get().setTitle(title);
        nomenclature.get().setPrice(price);
        nomenclatureRepository.save(nomenclature.get());
        return nomenclature.get();




    }

    public boolean deleteNomenclature(Long id) {
        Optional<Nomenclature> nomenclature= Optional.ofNullable(nomenclatureRepository.findById(id)
                .orElse(null));
        if (nomenclature == null) {
            return false;
        };
        nomenclatureRepository.delete(nomenclature.get());
        return true;
    }
}
