package com.example.onderdelen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ondedelen")
public class Onderdeel {
    @Id
    private String id;
    private String naam;
    private String merk;
    private Integer voorraad;
    private Integer prijs;

    public Onderdeel(String naam, String merk, Integer voorraad, Integer prijs) {
        this.naam = naam;
        this.merk = merk;
        this.voorraad = voorraad;
        this.prijs = prijs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public Integer getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(Integer voorraad) {
        this.voorraad = voorraad;
    }

    public Integer getPrijs() {
        return prijs;
    }

    public void setPrijs(Integer prijs) {
        this.prijs = prijs;
    }
}
