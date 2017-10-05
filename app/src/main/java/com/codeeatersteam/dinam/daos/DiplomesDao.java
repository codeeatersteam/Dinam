package com.codeeatersteam.dinam.daos;

/**
 * Created by pondikpa on 18/07/17.
 */

public class DiplomesDao {
    int id;
    String nom,description,created_at,updated_at;

    public DiplomesDao(int id, String nom, String description, String created_at, String updated_at) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
