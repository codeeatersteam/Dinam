package com.codeeatersteam.dinam.daos;

/**
 * Created by pondikpa on 21/06/17.
 */

public class OffresDao {
    int id,domaine,typeoffre,diplome;
    String salaire,description,telephone,poste,date_audience,date_fin,email,source;

    public OffresDao(int id, int domaine, int typeoffre, int diplome, String salaire, String description, String telephone, String poste, String date_audience, String date_fin, String email,String source) {
        this.id = id;
        this.domaine = domaine;
        this.typeoffre = typeoffre;
        this.diplome = diplome;
        this.salaire = salaire;
        this.description = description;
        this.telephone = telephone;
        this.poste = poste;
        this.date_audience = date_audience;
        this.date_fin = date_fin;
        this.email = email;
        this.source= source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDomaine() {
        return domaine;
    }

    public void setDomaine(int domaine) {
        this.domaine = domaine;
    }

    public int getTypeoffre() {
        return typeoffre;
    }

    public void setTypeoffre(int typeoffre) {
        this.typeoffre = typeoffre;
    }

    public int getDiplome() {
        return diplome;
    }

    public void setDiplome(int diplome) {
        this.diplome = diplome;
    }

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDate_audience() {
        return date_audience;
    }

    public void setDate_audience(String date_audience) {
        this.date_audience = date_audience;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
