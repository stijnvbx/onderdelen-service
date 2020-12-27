package com.example.onderdelen.controller;

import com.example.onderdelen.model.Onderdeel;
import com.example.onderdelen.repository.OnderdeelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class OnderdeelController {

    @Autowired
    private OnderdeelRepository onderdeelRepository;

    @PostConstruct
    public void fillDB(){
        if(onderdeelRepository.count()==0) {
            onderdeelRepository.save(new Onderdeel("ketting", "Thompson", 20, 20));
            onderdeelRepository.save(new Onderdeel("wiel_130", "Norta", 4, 30));
            onderdeelRepository.save(new Onderdeel("stuur", "Batavus", 2, 15));
        }

        System.out.println("Onderdeel test: " + onderdeelRepository.findAll());
    }

    @GetMapping("/onderdelen")
    public List<Onderdeel> findAll() {
        return onderdeelRepository.findAll();
    }

    @GetMapping("/onderdelen/{merk}")
    public List<Onderdeel> findOnderdeelsByMerk(@PathVariable String merk) {
        return  onderdeelRepository.findOnderdeelsByMerk(merk);
    }

    @PostMapping("/onderdelen")
    public Onderdeel addOnderdeel(@RequestBody Onderdeel onderdeel){
        onderdeelRepository.save(onderdeel);
        return  onderdeel;
    }

    @PutMapping("/onderdelen")
    public Onderdeel updateOnderdeel(@RequestBody Onderdeel updatedOnderdeel){
        Onderdeel retrievedOnderdeel = onderdeelRepository.findOnderdeelByMerkAndNaam(updatedOnderdeel.getMerk(),updatedOnderdeel.getNaam());

        retrievedOnderdeel.setPrijs(updatedOnderdeel.getPrijs());
        retrievedOnderdeel.setVoorraad(updatedOnderdeel.getVoorraad());

        onderdeelRepository.save(retrievedOnderdeel);

        return retrievedOnderdeel;
    }

    @DeleteMapping("/onderdelen/merk/{merk}/naam/{naam}")
    public ResponseEntity deleteOnderdeel(@PathVariable String merk, @PathVariable String naam) {
        Onderdeel onderdeel = onderdeelRepository.findOnderdeelByMerkAndNaam(merk, naam);
        if(onderdeel != null) {
            onderdeelRepository.delete(onderdeel);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
