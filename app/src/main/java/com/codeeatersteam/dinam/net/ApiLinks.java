package com.codeeatersteam.dinam.net;

/**
 * Created by pondikpa on 15/06/17.
 * @author pondikpa Tchabao
 * @version 1.0
 * @description contient les routes vers  l'api
 */
public class ApiLinks {
    protected static String API_ROOT_URL = "https://apidinam.000webhostapp.com/";
    public static String API_LOGIN_URL = API_ROOT_URL+"loginmobileapp";
    public static String API_REGISTER_URL = API_ROOT_URL+"newuser";
    protected static String  API_ENVOIE_OFFRES_URL = API_ROOT_URL+"newoffre";
    public static String  API_ENVOIE_CV_URL = API_ROOT_URL+"uploadcv";
    protected static String  API_ENVOIE_EVENEMENTS_URL = API_ROOT_URL+"newevenement";
    protected static String  API_ENVOIE_LIEUX_URL = API_ROOT_URL+"newlieu";
    protected static String API_RECUPERER_OFFRES_URL = API_ROOT_URL+"listedesoffresapprouves";
    protected static String API_RECUPERER_EVENEMENTS_URL = API_ROOT_URL+"listedesevenementsapprouves";
    protected static String API_RECUPERER_LIEUX_URL = API_ROOT_URL+"listedeslieuxapprouves";
    protected static String API_RECUPERER_DOMAINES_URL = API_ROOT_URL+"listedesdomainesapprouves";
    protected static String API_RECUPERER_DIPLOMES_URL = API_ROOT_URL+"listedesdiplomesapprouves";
    protected static String API_RECUPERER_TYPESOFFRES_URL = API_ROOT_URL+"listedestypeoffresapprouves";
    protected static String API_RECUPERER_TYPESEVENEMENTS_URL = API_ROOT_URL+"listedestypeevenementsapprouves";
    protected static String API_RECUPERER_TYPESLIEUX_URL = API_ROOT_URL+"listedestypelieuxapprouves";
    public static String API_IMAGES_EVENEMENTS_URL = API_ROOT_URL+"images/evenements/";
    public static String API_IMAGES_LIEUX_URL = API_ROOT_URL+"images/lieux/";
    public static String API_IMAGES_USERS_URL = API_ROOT_URL+"images/users/";
    public static String API_DOSSIER_CV_URL = API_ROOT_URL+"cv/";

}
