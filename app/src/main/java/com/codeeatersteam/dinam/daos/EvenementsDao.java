package com.codeeatersteam.dinam.daos;

/**
 * Created by pondikpa on 21/06/17.
 */

public class EvenementsDao {
    int id,etat,typeevenement;
    String nom,telephone,image,date_evenement,description,lieu,siteweb,created_at,updated_at;

    public EvenementsDao(int id, int etat, int typeevenement, String nom,String telephone, String image, String date_evenement, String description, String lieu, String created_at, String updated_at) {
        this.id = id;
        this.etat = etat;
        this.typeevenement = typeevenement;
        this.nom = nom;
        this.telephone=telephone;
        this.image = image;
        this.date_evenement = date_evenement;
        this.description = description;
        this.lieu = lieu;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public int getTypeevenement() {
        return typeevenement;
    }

    public void setTypeevenement(int typeevenement) {
        this.typeevenement = typeevenement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate_evenement() {
        return date_evenement;
    }

    public void setDate_evenement(String date_evenement) {
        this.date_evenement = date_evenement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
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
