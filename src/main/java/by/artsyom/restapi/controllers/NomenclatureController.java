package by.artsyom.restapi.controllers;

import by.artsyom.restapi.models.Nomenclature;
import by.artsyom.restapi.services.NomenclatureService;
import by.artsyom.restapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
@Slf4j
public class NomenclatureController {
    private final NomenclatureService nomenclatureServices;
    private final UserService userService;

    @GetMapping(value = "/nomenclatures")
    public String product(@RequestParam(name = "title", required = false) String title, Principal principal, Model model){
        model.addAttribute("nomenclatures", nomenclatureServices.listNomenclatures(title));
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "nomenclatures";
    }

    @PostMapping( "/nomenclature/create2")
    public String createNomenclature(Nomenclature nomenclature) throws IOException {
        MultipartFile file1 = null;
        MultipartFile file2 = null;
        MultipartFile file3 = null;
        log.info(nomenclature.getTitle());
        //nomenclatureServices.saveNomenclature(nomenclature,file1,file2,file3);
        return "redirect:/nomenclatures";
    }

    @PostMapping( "/nomenclature/create")
    public String createNomenclature(@RequestParam("file1") MultipartFile file1, @RequestParam("file2")
    MultipartFile file2, @RequestParam("file3") MultipartFile file3, Nomenclature nomenclature, Principal principal) throws IOException {
        log.info(nomenclature.getTitle());
        nomenclatureServices.saveNomenclature(principal, nomenclature,file1,file2,file3);
        return "redirect:/nomenclatures";
    }

    @PostMapping(value = "/nomenclature/delete/{id}")
    public String deleteNomenclature(@PathVariable Long id){
        nomenclatureServices.deleteNomenclatures(id);
        return "redirect:/nomenclatures";
    }

    @GetMapping(value = "/nomenclature/{id}")
    public String nomenclatureInfo(@PathVariable Long id, Model model, Principal principal){
        Nomenclature nomenclature = nomenclatureServices.getNomenclatureById(id);
        model.addAttribute("nomenclature", nomenclature);
        model.addAttribute("images", nomenclature.getImages());
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "nomenclature-info";
    }

    @GetMapping(value = "/")
    public String getPage(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "index";
    }
}
