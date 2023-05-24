package by.artsyom.restapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nomenclature")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nomenclature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int price;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nomenclature")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;
    private LocalDateTime dateOfCreated;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToNomenclature(Image image){
        image.setNomenclature(this);
        images.add(image);
    }
}
