package com.codeeatersteam.dinam.daos;

/**
 * Created by pondikpa on 21/06/17.
 */

public class LieuxDao {
    int id,etat,typelieu;
    String nom,telephone,adresse,description,siteweb,image,created_at,updated_at;

    public LieuxDao(int id, int etat, int typelieu, String nom, String telephone, String adresse,
                    String description, String siteweb, String image, String created_at, String updated_at) {
        this.id = id;
        this.etat = etat;
        this.typelieu = typelieu;
        this.nom = nom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.description = description;
        this.siteweb = siteweb;
        this.image = image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getTypelieu() {
        return typelieu;
    }

    public void setTypelieu(int typelieu) {
        this.typelieu = typelieu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
