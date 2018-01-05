package com.codeeatersteam.dinam.kernel;

import java.util.ArrayList;

/**
 * Created by pondikpa on 15/06/17.
 * @author pondikpa Tchabao
 * @version 1.0
 * @description contient les variables statiques de l'application
 */

public class Core {

    // les informations sur l'application
    public static String APP_NAME = "Dinam";
    public static String APP_VERSION = "1.0.3 Doris";
    public static String TEAM_NAME = "Code Eaters Team";

    // les informations sur la base de donnees locale
    public static String DATABASE_NAME = "dinamdatas";
    public static int DATATBASE_VERSION = 2;

    // les informations sur les preferences
    public static String PREFERENCES_NAME = "dinampreferences";
    public static String ID_PREFERENCE = "id";
    public static String NOM_PREFERENCE = "nom";
    public static String PRENOMS_PREFERENCE = "prenoms";
    public static String DATENAISSANCE_PREFERENCE = "datenaissance";
    public static String EMAIL_PREFERENCE = "email";
    public static String TELEPHONE_PREFERENCE = "telephone";
    public static String MONDIPLOME_PREFERENCE = "mondiplome";
    public static String MONIMAGE_PREFERENCE = "monimage";
    public static String SEXE_PREFERENCE = "sexe";
    public static String DOMAINE_OFFRE_PREFERE = "domaineOffrePref";
    public static String TYPE_OFFRE_PREFERE = "typeOffrePref";
    public static String TYPE_EVENEMENT_PREFERE = "typeEvenementPref";
    public static String TYPE_LIEU_PREFERE = "typeLieuPref";
    public static String TOUS_COMMUN_AUX_PREFERENCES = "Tous";
    public static String OUI_COMMUN_AUX_PREFERENCES = "oui";
    public static String NON_COMMUN_AUX_PREFERENCES = "non";
    public static String WELCOME_STATE= "welcome";

    public static ArrayList<String> CHOIX_PREFERENCES_OFFRES = new ArrayList<String>();

    //le informations de publicites
    public static  final String AD_OFFRES_UNIT_ID ="ca-app-pub-6105029167686346/9575546589";
    public static  final String AD_EVENEMENTS_UNIT_ID ="ca-app-pub-6105029167686346/2421823171";







    //les colones propres a toutes les tables
    public static String COLUMN_TABLE_ID = "id";
    public static String COLUMN_TABLE_NOM = "nom";
    public static String COLUMN_TABLE_DESCRIPTION = "description";
    public static String COLUMN_TABLE_IMAGE = "image";
    public static String COLUMN_TABLE_ADRESSE = "adresse";
    public static String COLUMN_TABLE_TELEPHONE = "telephone";
    public static String COLUMN_TABLE_EMAIL = "email";
    public static String COLUMN_TABLE_SITE_WEB ="siteweb";
    public static String COLUMN_TABLE_CREATED_AT = "created_at";
    public static String COLUMN_TABLE_UPDATED_AT = "updated_at";
    public static String COLUMN_TABLE_SYNCHRONIZED = "1";
    public static String COLUMN_TABLE_UNSYNCHRONIZED = "0";
    public static String COLUMN_TABLE_ETAT = "etat";
    public static String COLUMN_TABLE_SYNC_STATUS = "synchronisation";



    // la table offres et ses attributs
    public static String TABLE_OFFRES = "offres";
    public static String COLUMN_OFFRE_ID = COLUMN_TABLE_ID;
    public static String COLUMN_OFFRE_POSTE = "poste";
    public static String COLUMN_OFFRE_SALAIRE = "salaire";
    public static String COLUMN_OFFRE_SOURCE = "source";
    public static String COLUMN_OFFRE_DATE_AUDIENCE = "date_audience";
    public static String COLUMN_OFFRE_DATE_CLOTURE = "date_fin" ;
    public static String COLUMN_OFFRE_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_OFFRE_TELEPHONE = COLUMN_TABLE_TELEPHONE;
    public static String COLUMN_OFFRE_EMAIL = COLUMN_TABLE_EMAIL;
    public static String COLUMN_OFFRE_ETAT = COLUMN_TABLE_ETAT;
    public static String COLUMN_OFFRE_SYNC_STATUS = COLUMN_TABLE_SYNC_STATUS;
    public static String COLUMN_OFFRE_TYPE_OFFRE = "id_typeoffres";
    public static String COLUMN_OFFRE_DIPLOME_OFFRE = "id_diplomes";
    public static String COLUMN_OFFRE_DOMAINE_OFFRE = "id_domaines";
    public static String COLUMN_OFFRE_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_OFFRE_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;

    // la table evenements et ses attributs
    public static String TABLE_EVENEMENTS = "evenements";
    public static String COLUMN_EVENEMENT_ID = COLUMN_TABLE_ID;
    public static String COLUMN_EVENEMENT_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_EVENEMENT_IMAGE = COLUMN_TABLE_IMAGE;
    public static String COLUMN_EVENEMENT_DATE = "date_event";
    public static String COLUMN_EVENEMENT_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_EVENEMENT_LIEU = "lieu";
    public static String COLUMN_EVENEMENT_TELEPHONE = COLUMN_TABLE_TELEPHONE;
    public static String COLUMN_EVENEMENT_ETAT = COLUMN_TABLE_ETAT;
    public static String COLUMN_EVENEMENT_TYPE_EVENEMENT = "id_typeevenements";
    public static String COLUMN_EVENEMENT_SYNC_STATUS = COLUMN_TABLE_SYNC_STATUS;
    public static String COLUMN_EVENEMENT_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_EVENEMENT_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;


    // la table LIEUs et ses attributs
    public static String TABLE_LIEUX = "lieux";
    public static String COLUMN_LIEU_ID = COLUMN_TABLE_ID;
    public static String COLUMN_LIEU_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_LIEU_TELEPHONE = COLUMN_TABLE_TELEPHONE ;
    public static String COLUMN_LIEU_ADRESSE = COLUMN_TABLE_ADRESSE;
    public static String COLUMN_LIEU_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_LIEU_SITE_WEB = COLUMN_TABLE_SITE_WEB;
    public static String COLUMN_LIEU_IMAGE = COLUMN_TABLE_IMAGE;
    public static String COLUMN_LIEU_TYPE_LIEU = "id_typelieux";
    public static String COLUMN_LIEU_ETAT = COLUMN_TABLE_ETAT;
    public static String COLUMN_LIEU_SYNC_STATUS = COLUMN_TABLE_SYNC_STATUS;
    public static String COLUMN_LIEU_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_LIEU_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;


    // la table type d'offres et ses attributs
    public static String TABLE_TYPE_OFFRES = "typeoffres";
    public static String COLUMN_TYPE_OFFRE_ID = COLUMN_TABLE_ID;
    public static String COLUMN_TYPE_OFFRE_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_TYPE_OFFRE_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_TYPE_OFFRE_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_TYPE_OFFRE_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;


    // la table type d'evenements et ses attributs
    public static String TABLE_TYPE_EVENEMENTS = "typeevenements";
    public static String COLUMN_TYPE_EVENEMENT_ID = COLUMN_TABLE_ID;
    public static String COLUMN_TYPE_EVENEMENT_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_TYPE_EVENEMENT_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_TYPE_EVENEMENT_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_TYPE_EVENEMENT_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;

    // la table type de lieux et ses attributs
    public static String TABLE_TYPE_LIEUX = "typelieux";
    public static String COLUMN_TYPE_LIEU_ID = COLUMN_TABLE_ID;
    public static String COLUMN_TYPE_LIEU_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_TYPE_LIEU_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_TYPE_LIEU_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_TYPE_LIEU_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;

    //la table domaines et ses attributs
    public static String TABLE_DOMAINES = "domaines";
    public static String COLUMN_DOMAINE_ID = COLUMN_TABLE_ID;
    public static String COLUMN_DOMAINE_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_DOMAINE_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_DOMAINE_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_DOMAINE_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;


    // la table diplomes et ses attributs
    public static String TABLE_DIPLOMES = "diplomes";
    public static String COLUMN_DIPLOME_ID = COLUMN_TABLE_ID;
    public static String COLUMN_DIPLOME_NOM = COLUMN_TABLE_NOM;
    public static String COLUMN_DIPLOME_DESCRIPTION = COLUMN_TABLE_DESCRIPTION;
    public static String COLUMN_DIPLOME_CREATED_AT = COLUMN_TABLE_CREATED_AT;
    public static String COLUMN_DIPLOME_UPDATED_AT = COLUMN_TABLE_UPDATED_AT;


    //Les requetes statiques;

    public static String LISTES_OFFRES_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_ETAT+" = "+COLUMN_TABLE_SYNCHRONIZED+" ORDER BY "+COLUMN_OFFRE_ID+" DESC";
    public static String LISTES_OFFRES_EN_LOCAL_NON_SYNCHRONISES = " SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_SYNC_STATUS+" = "+COLUMN_TABLE_UNSYNCHRONIZED+" AND "+COLUMN_OFFRE_ETAT+" ="+COLUMN_TABLE_UNSYNCHRONIZED;
    public static String LISTES_EVENEMENTS_EN_LOCAL_NON_SYNCHRONISES = " SELECT * FROM "+TABLE_EVENEMENTS+" WHERE "+COLUMN_EVENEMENT_SYNC_STATUS+" = "+COLUMN_TABLE_UNSYNCHRONIZED+" AND "+COLUMN_EVENEMENT_ETAT+" ="+COLUMN_TABLE_UNSYNCHRONIZED;
    public static String LISTES_LIEUX_EN_LOCAL_NON_SYNCHRONISES = " SELECT * FROM "+TABLE_LIEUX+" WHERE "+COLUMN_LIEU_SYNC_STATUS+" = "+COLUMN_TABLE_UNSYNCHRONIZED+" AND "+COLUMN_LIEU_ETAT+" ="+COLUMN_TABLE_UNSYNCHRONIZED;
    public static String LISTES_EVENEMENTS_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_EVENEMENTS+" WHERE "+COLUMN_EVENEMENT_ETAT+"= "+COLUMN_TABLE_SYNCHRONIZED+" ORDER BY "+COLUMN_EVENEMENT_ID+" DESC";
    public static String LISTES_LIEUX_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_LIEUX+" ORDER BY "+COLUMN_LIEU_ID+" DESC";
    public static String LISTES_DOMAINES_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_DOMAINES+" ORDER BY "+COLUMN_LIEU_NOM+" ASC";
    public static String LISTES_TYPEOFFRES_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_TYPE_OFFRES+" ORDER BY "+COLUMN_TYPE_OFFRE_NOM+" ASC";
    public static String LISTES_DIPLOMES_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_DIPLOMES+" ORDER BY "+COLUMN_DIPLOME_NOM+" ASC";
    public static String LISTES_TYPEEVENEMENTS_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_TYPE_EVENEMENTS+" ORDER BY "+COLUMN_TYPE_EVENEMENT_NOM+" ASC";
    public static String LISTES_TYPELIEUX_EN_LOCAL_APPROUVES = " SELECT * FROM "+TABLE_TYPE_LIEUX+" ORDER BY "+COLUMN_TYPE_LIEU_NOM+" ASC";










}
