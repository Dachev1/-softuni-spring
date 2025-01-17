package bg.softuni.mobilele.web;

import bg.softuni.mobilele.model.dtos.offer.NewOfferDTO;
import bg.softuni.mobilele.model.dtos.offer.OfferDetailsDTO;
import bg.softuni.mobilele.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/add")
    public String addOfferForm(Model model) {
        NewOfferDTO newOfferDTO = new NewOfferDTO();
        model.addAttribute("newOfferDTO", newOfferDTO);
        model.addAttribute("engineTypes", offerService.getAllEngineTypes());
        model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
        model.addAttribute("isEdit", false);
        return "offer-add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid @ModelAttribute("newOfferDTO") NewOfferDTO newOfferDTO,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("engineTypes", offerService.getAllEngineTypes());
            model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
            model.addAttribute("isEdit", false);
            return "offer-add";
        }
        UUID createdOfferId = offerService.addOffer(newOfferDTO);
        return "redirect:/offers/details/" + createdOfferId;
    }

    @GetMapping("/edit/{id}")
    public String editOfferForm(@PathVariable UUID id, Model model) {
        NewOfferDTO offerToEdit = offerService.getOfferDetailsForEdit(id);
        model.addAttribute("newOfferDTO", offerToEdit);
        model.addAttribute("engineTypes", offerService.getAllEngineTypes());
        model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
        model.addAttribute("isEdit", true);
        return "offer-add";
    }

    @PostMapping("/update/{id}")
    public String updateOffer(@PathVariable UUID id,
                              @Valid @ModelAttribute("newOfferDTO") NewOfferDTO newOfferDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("engineTypes", offerService.getAllEngineTypes());
            model.addAttribute("transmissionTypes", offerService.getAllTransmissionTypes());
            model.addAttribute("isEdit", true);
            return "offer-add";
        }
        offerService.updateOffer(id, newOfferDTO);
        return "redirect:/offers/details/" + id;
    }

    @GetMapping("/details/{id}")
    public String showOfferDetails(@PathVariable UUID id, Model model) {
        OfferDetailsDTO offerDetails = offerService.getOfferById(id);
        model.addAttribute("offerDetails", offerDetails);
        return "details";
    }

    @GetMapping("/all")
    public String getAllOffers(Model model) {
        List<OfferDetailsDTO> offers = offerService.getAllOffers();
        model.addAttribute("offers", offers);
        return "offers";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        offerService.deleteOfferById(id);
        return "redirect:/offers/all";
    }
}
