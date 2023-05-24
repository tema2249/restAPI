package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.services.NomenclatureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nomenclature")
@AllArgsConstructor
public class Rest {

    private final NomenclatureService nomenclatureService;
    @GetMapping
    public ResponseEntity<List<Nomenclature>> getAllNomenclature(@RequestParam(required = false) String title){
        List<Nomenclature> nomenclatureList = nomenclatureService.listNomenclatures(title);
        if (!nomenclatureList.isEmpty()) {
            return new ResponseEntity<>(nomenclatureList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nomenclature> getNomenclatureById(@PathVariable Long id) {
        Nomenclature nomenclature = nomenclatureService.getNomenclatureById(id);
        if (nomenclature != null) {
            return new ResponseEntity<>(nomenclature, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Nomenclature> createNomenclature(@RequestBody String title, int price) {
        Nomenclature createdNomenclature = nomenclatureService.createNomenclature(title, price);
        return new ResponseEntity<>(createdNomenclature, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nomenclature> updateNomenclature(
            @PathVariable Long id, @RequestBody String title, int price) {
        Nomenclature updatedNomenclature = nomenclatureService.updateNomenclature(id, title, price);
        if (updatedNomenclature != null) {
            return new ResponseEntity<>(updatedNomenclature, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNomenclature(@PathVariable Long id) {
        boolean deleted = nomenclatureService.deleteNomenclature(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
