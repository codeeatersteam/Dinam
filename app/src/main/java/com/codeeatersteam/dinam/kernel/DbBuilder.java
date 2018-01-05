package com.codeeatersteam.dinam.kernel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DIPLOME_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_DOMAINE_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DATE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_SYNC_STATUS;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_TYPE_EVENEMENT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_EVENEMENT_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ADRESSE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_IMAGE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_SITE_WEB;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_SYNC_STATUS;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_TYPE_LIEU;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_LIEU_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_AUDIENCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DATE_CLOTURE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DIPLOME_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_DOMAINE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_EMAIL;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ETAT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_POSTE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SALAIRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SOURCE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_SYNC_STATUS;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TELEPHONE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_TYPE_OFFRE;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_OFFRE_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TABLE_SYNCHRONIZED;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TABLE_UNSYNCHRONIZED;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_EVENEMENT_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_LIEU_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_LIEU_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_LIEU_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_LIEU_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_LIEU_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_CREATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_DESCRIPTION;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_ID;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_NOM;
import static com.codeeatersteam.dinam.kernel.Core.COLUMN_TYPE_OFFRE_UPDATED_AT;
import static com.codeeatersteam.dinam.kernel.Core.DATABASE_NAME;
import static com.codeeatersteam.dinam.kernel.Core.DATATBASE_VERSION;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_DIPLOMES_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_DOMAINES_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_TYPEEVENEMENTS_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_TYPELIEUX_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.LISTES_TYPEOFFRES_EN_LOCAL_APPROUVES;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_DIPLOMES;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_DOMAINES;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_EVENEMENTS;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_LIEUX;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_OFFRES;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_TYPE_EVENEMENTS;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_TYPE_LIEUX;
import static com.codeeatersteam.dinam.kernel.Core.TABLE_TYPE_OFFRES;

/**
 * Created by pondikpa on 15/06/17.
 * @author pondikpa Tchabao
 * @version 1.0
 * @description centre de traitement des donnees de la base de donnees locale
 */

public class DbBuilder extends SQLiteOpenHelper {

    //scripts de creations des differentes tables

    private static  String CREATE_TABLE_OFFRES = " CREATE TABLE IF NOT EXISTS "+TABLE_OFFRES+"(" +
            " "+COLUMN_OFFRE_ID+" INTEGER PRIMARY KEY ," +
            " "+COLUMN_OFFRE_POSTE+" TEXT NOT NULL," +
            " "+COLUMN_OFFRE_SALAIRE+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_DATE_AUDIENCE+" TEXT ," +
            " "+COLUMN_OFFRE_DATE_CLOTURE+" TEXT ," +
            " "+COLUMN_OFFRE_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_OFFRE_TELEPHONE+" TEXT NOT NULL," +
            " "+COLUMN_OFFRE_EMAIL+" TEXT ," +
            " "+COLUMN_OFFRE_SOURCE+" TEXT ," +
            " "+COLUMN_OFFRE_ETAT+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_TYPE_OFFRE+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_DOMAINE_OFFRE+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_DIPLOME_OFFRE+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_SYNC_STATUS+" INTEGER NOT NULL," +
            " "+COLUMN_OFFRE_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_OFFRE_UPDATED_AT+" TEXT NOT NULL ) ";

    private static  String CREATE_TABLE_EVENEMENTS = " CREATE TABLE IF NOT EXISTS "+TABLE_EVENEMENTS+"(" +
            " "+COLUMN_EVENEMENT_ID+" INTEGER PRIMARY KEY ," +
            " "+COLUMN_EVENEMENT_NOM+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_IMAGE+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_DATE+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_LIEU+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_TELEPHONE+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_ETAT+" INTEGER NOT NULL," +
            " "+COLUMN_EVENEMENT_TYPE_EVENEMENT+" INTEGER NOT NULL," +
            " "+COLUMN_EVENEMENT_SYNC_STATUS+" INTEGER NOT NULL," +
            " "+COLUMN_EVENEMENT_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_EVENEMENT_UPDATED_AT+" TEXT NOT NULL ) ";

    private static  String CREATE_TABLE_LIEUX = " CREATE TABLE IF NOT EXISTS "+TABLE_LIEUX+"(" +
            " "+COLUMN_LIEU_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_LIEU_NOM+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_TELEPHONE+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_ADRESSE+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_SITE_WEB+" TEXT," +
            " "+COLUMN_LIEU_IMAGE+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_TYPE_LIEU+" INTEGER NOT NULL," +
            " "+COLUMN_LIEU_ETAT+" INTEGER NOT NULL," +
            " "+COLUMN_LIEU_SYNC_STATUS+" INTEGER NOT NULL," +
            " "+COLUMN_LIEU_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_LIEU_UPDATED_AT+" TEXT NOT NULL ) ";

    private static  String CREATE_TABLE_DOMAINES = " CREATE TABLE IF NOT EXISTS "+TABLE_DOMAINES+"(" +
            " "+COLUMN_DOMAINE_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_DOMAINE_NOM+" TEXT NOT NULL," +
            " "+COLUMN_DOMAINE_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_DOMAINE_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_DOMAINE_UPDATED_AT+" TEXT NOT NULL)";

    private static  String CREATE_TABLE_DIPLOMES = " CREATE TABLE IF NOT EXISTS "+TABLE_DIPLOMES+"(" +
            " "+COLUMN_DIPLOME_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_DIPLOME_NOM+" TEXT NOT NULL," +
            " "+COLUMN_DIPLOME_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_DIPLOME_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_DIPLOME_UPDATED_AT+" TEXT NOT NULL)";

    private static  String CREATE_TABLE_TYPES_OFFRES = "CREATE TABLE IF NOT EXISTS "+TABLE_TYPE_OFFRES+"(" +
            " "+COLUMN_TYPE_OFFRE_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_TYPE_OFFRE_NOM+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_OFFRE_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_OFFRE_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_OFFRE_UPDATED_AT+" TEXT NOT NULL)";

    private static  String CREATE_TABLE_TYPES_EVENEMENTS = "CREATE TABLE IF NOT EXISTS "+TABLE_TYPE_EVENEMENTS+"(" +
            " "+COLUMN_TYPE_EVENEMENT_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_TYPE_EVENEMENT_NOM+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_EVENEMENT_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_EVENEMENT_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_EVENEMENT_UPDATED_AT+" TEXT NOT NULL)";

    private static  String CREATE_TABLE_TYPES_LIEUS = "CREATE TABLE IF NOT EXISTS "+TABLE_TYPE_LIEUX+"(" +
            " "+COLUMN_TYPE_LIEU_ID+" INTEGER PRIMARY KEY," +
            " "+COLUMN_TYPE_LIEU_NOM+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_LIEU_DESCRIPTION+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_LIEU_CREATED_AT+" TEXT NOT NULL," +
            " "+COLUMN_TYPE_LIEU_UPDATED_AT+" TEXT NOT NULL)";

    public DbBuilder(Context context) {
        super(context, DATABASE_NAME, null, DATATBASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OFFRES);
        db.execSQL(CREATE_TABLE_EVENEMENTS);
        db.execSQL(CREATE_TABLE_LIEUX);
        db.execSQL(CREATE_TABLE_DOMAINES);
        db.execSQL(CREATE_TABLE_DIPLOMES);
        db.execSQL(CREATE_TABLE_TYPES_OFFRES);
        db.execSQL(CREATE_TABLE_TYPES_EVENEMENTS);
        db.execSQL(CREATE_TABLE_TYPES_LIEUS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_OFFRES+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENEMENTS+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LIEUX+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DOMAINES+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DIPLOMES+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TYPE_OFFRES+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TYPE_EVENEMENTS+"");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TYPE_LIEUX+"");

    }

    public void enregistrerDomainesOffreDepuisApi(int id, String nom,String description,String created_at,
                                                  String updated_at, SQLiteDatabase db){

        ContentValues domaineValues = new ContentValues();
        domaineValues.put(COLUMN_DOMAINE_ID,id);
        domaineValues.put(COLUMN_DOMAINE_NOM,nom);
        domaineValues.put(COLUMN_DOMAINE_DESCRIPTION,description);
        domaineValues.put(COLUMN_DOMAINE_CREATED_AT,created_at);
        domaineValues.put(COLUMN_DOMAINE_UPDATED_AT,updated_at);


        String query = "SELECT * FROM "+TABLE_DOMAINES+" WHERE "+COLUMN_DOMAINE_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_DOMAINES,domaineValues,COLUMN_DOMAINE_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_DOMAINE_ID)))});

            }
        }
        else {
            long l = db.insert(TABLE_DOMAINES,null,domaineValues);
        }


    }

    public void enregistrerDiplomesDepuisApi(int id, String nom,String description,String created_at,
                                                  String updated_at, SQLiteDatabase db){

        ContentValues domaineValues = new ContentValues();
        domaineValues.put(COLUMN_DIPLOME_ID,id);
        domaineValues.put(COLUMN_DIPLOME_NOM,nom);
        domaineValues.put(COLUMN_DIPLOME_DESCRIPTION,description);
        domaineValues.put(COLUMN_DIPLOME_CREATED_AT,created_at);
        domaineValues.put(COLUMN_DIPLOME_UPDATED_AT,updated_at);

        String query = "SELECT * FROM "+TABLE_DIPLOMES+" WHERE "+COLUMN_DIPLOME_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_DIPLOMES,domaineValues,COLUMN_DIPLOME_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_DIPLOME_ID)))});

            }
        }
        else {
            long l = db.insert(TABLE_DIPLOMES,null,domaineValues);
        }



    }

    public void enregistrerTypeOffreDepuisApi(int id, String nom, String description,String created_at,
                                              String updated_at,SQLiteDatabase db){

        ContentValues typeOffreValues = new ContentValues();
        typeOffreValues.put(COLUMN_TYPE_OFFRE_ID,id);
        typeOffreValues.put(COLUMN_TYPE_OFFRE_NOM,nom);
        typeOffreValues.put(COLUMN_TYPE_OFFRE_DESCRIPTION,description);
        typeOffreValues.put(COLUMN_TYPE_OFFRE_CREATED_AT,created_at);
        typeOffreValues.put(COLUMN_TYPE_OFFRE_UPDATED_AT,updated_at);

        String query = "SELECT * FROM "+TABLE_TYPE_OFFRES+" WHERE "+COLUMN_TYPE_OFFRE_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_TYPE_OFFRES,typeOffreValues,COLUMN_TYPE_OFFRE_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OFFRE_ID)))});

            }
        }
        else {
            long l = db.insert(TABLE_TYPE_OFFRES,null,typeOffreValues);
        }




    }

    public void enregistrerTypeEvenementDepuisApi(int id, String nom, String description,String created_at,
                                              String updated_at,SQLiteDatabase db){

        ContentValues typeEvenementValues = new ContentValues();
        typeEvenementValues.put(COLUMN_TYPE_EVENEMENT_ID,id);
        typeEvenementValues.put(COLUMN_TYPE_EVENEMENT_NOM,nom);
        typeEvenementValues.put(COLUMN_TYPE_EVENEMENT_DESCRIPTION,description);
        typeEvenementValues.put(COLUMN_TYPE_EVENEMENT_CREATED_AT,created_at);
        typeEvenementValues.put(COLUMN_TYPE_EVENEMENT_UPDATED_AT,updated_at);


        String query = "SELECT * FROM "+TABLE_TYPE_EVENEMENTS+" WHERE "+COLUMN_TYPE_EVENEMENT_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_TYPE_EVENEMENTS,typeEvenementValues,COLUMN_TYPE_EVENEMENT_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_EVENEMENT_ID)))});

            }
        }
        else {
            long l = db.insert(TABLE_TYPE_EVENEMENTS,null,typeEvenementValues);
        }



    }

    public void enregistrerTypeLieuDepuisApi(int id, String nom, String description,String created_at,
                                                  String updated_at,SQLiteDatabase db){

        ContentValues typeLIEUValues = new ContentValues();
        typeLIEUValues.put(COLUMN_TYPE_LIEU_ID,id);
        typeLIEUValues.put(COLUMN_TYPE_LIEU_NOM,nom);
        typeLIEUValues.put(COLUMN_TYPE_LIEU_DESCRIPTION,description);
        typeLIEUValues.put(COLUMN_TYPE_LIEU_CREATED_AT,created_at);
        typeLIEUValues.put(COLUMN_TYPE_LIEU_UPDATED_AT,updated_at);


        String query = "SELECT * FROM "+TABLE_TYPE_LIEUX+" WHERE "+COLUMN_TYPE_LIEU_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_TYPE_LIEUX,typeLIEUValues,COLUMN_TYPE_LIEU_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_LIEU_ID)))});

            }
        }
        else {
            long l = db.insert(TABLE_TYPE_LIEUX,null,typeLIEUValues);
        }


    }



    public  void enregistrerOffreEnLocal(int id,String poste,String salaire,String date_audience,
                                         String date_fin,String description,String email,String source,String telephone,
                                         int typeoffreid,int domaineoffreid,int diplomeoffreid,SQLiteDatabase db){

        ContentValues offreValues = new ContentValues();
        offreValues.put(COLUMN_OFFRE_ID,id);
        offreValues.put(COLUMN_OFFRE_POSTE,poste);
        offreValues.put(COLUMN_OFFRE_SALAIRE,salaire);
        offreValues.put(COLUMN_OFFRE_DATE_AUDIENCE,date_audience);
        offreValues.put(COLUMN_OFFRE_DATE_CLOTURE,date_fin);
        offreValues.put(COLUMN_OFFRE_DESCRIPTION,description);
        offreValues.put(COLUMN_OFFRE_EMAIL,email);
        offreValues.put(COLUMN_OFFRE_SOURCE,source);
        offreValues.put(COLUMN_OFFRE_TELEPHONE,telephone);
        offreValues.put(COLUMN_OFFRE_TYPE_OFFRE,typeoffreid);
        offreValues.put(COLUMN_OFFRE_DOMAINE_OFFRE,domaineoffreid);
        offreValues.put(COLUMN_OFFRE_DIPLOME_OFFRE,diplomeoffreid);
        offreValues.put(COLUMN_OFFRE_ETAT,COLUMN_TABLE_UNSYNCHRONIZED);
        offreValues.put(COLUMN_OFFRE_SYNC_STATUS,COLUMN_TABLE_UNSYNCHRONIZED);
        offreValues.put(COLUMN_OFFRE_CREATED_AT,email);
        offreValues.put(COLUMN_OFFRE_UPDATED_AT,email);
        Long l = db.insert(TABLE_OFFRES,null,offreValues);



    }

    public void enregistrerOffreDepuisApi(int id,String poste,String salaire,String date_audience,
                                          String date_fin,String description,String email,String source,String telephone,
                                          int typeoffreid,int domaineoffreid,int diplomeoffreid,SQLiteDatabase db){

        ContentValues offreValues = new ContentValues();
        offreValues.put(COLUMN_OFFRE_ID,id);
        offreValues.put(COLUMN_OFFRE_POSTE,poste);
        offreValues.put(COLUMN_OFFRE_SALAIRE,salaire);
        offreValues.put(COLUMN_OFFRE_DATE_AUDIENCE,date_audience);
        offreValues.put(COLUMN_OFFRE_DATE_CLOTURE,date_fin);
        offreValues.put(COLUMN_OFFRE_DESCRIPTION,description);
        offreValues.put(COLUMN_OFFRE_EMAIL,email);
        offreValues.put(COLUMN_OFFRE_SOURCE,source);
        offreValues.put(COLUMN_OFFRE_TELEPHONE,telephone);
        offreValues.put(COLUMN_OFFRE_TYPE_OFFRE,typeoffreid);
        offreValues.put(COLUMN_OFFRE_DOMAINE_OFFRE,domaineoffreid);
        offreValues.put(COLUMN_OFFRE_DIPLOME_OFFRE,diplomeoffreid);
        offreValues.put(COLUMN_OFFRE_ETAT,COLUMN_TABLE_SYNCHRONIZED);
        offreValues.put(COLUMN_OFFRE_SYNC_STATUS,COLUMN_TABLE_SYNCHRONIZED);
        offreValues.put(COLUMN_OFFRE_CREATED_AT,FonctionsUtiles.dateActuelle());
        offreValues.put(COLUMN_OFFRE_UPDATED_AT,FonctionsUtiles.dateActuelle());

        String query = "SELECT * FROM "+TABLE_OFFRES+" WHERE "+COLUMN_OFFRE_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_OFFRES,offreValues,COLUMN_OFFRE_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_ID)))});

            }
        }
        else {
            Long l = db.insert(TABLE_OFFRES,null,offreValues);
        }




    }



    public void enregistrerEvenementDepuisApi(int id, int etat, int typeevenement, String nom,String telephone,
                                              String image, String date_evenement, String description,
                                              String lieu, String created_at,
                                              String updated_at,SQLiteDatabase db) {
        ContentValues evenementValues = new ContentValues();
        evenementValues.put(COLUMN_EVENEMENT_ID,id);
        evenementValues.put(COLUMN_EVENEMENT_ETAT,etat);
        evenementValues.put(COLUMN_EVENEMENT_NOM,nom);
        evenementValues.put(COLUMN_EVENEMENT_TYPE_EVENEMENT,typeevenement);
        evenementValues.put(COLUMN_EVENEMENT_TELEPHONE,telephone);
        evenementValues.put(COLUMN_EVENEMENT_IMAGE,image);
        evenementValues.put(COLUMN_EVENEMENT_DATE,date_evenement);
        evenementValues.put(COLUMN_EVENEMENT_DESCRIPTION,description);
        evenementValues.put(COLUMN_EVENEMENT_LIEU,lieu);
        evenementValues.put(COLUMN_EVENEMENT_CREATED_AT,created_at);
        evenementValues.put(COLUMN_EVENEMENT_UPDATED_AT,updated_at);
        evenementValues.put(COLUMN_EVENEMENT_SYNC_STATUS,COLUMN_TABLE_SYNCHRONIZED);


        String query = "SELECT * FROM "+TABLE_EVENEMENTS+" WHERE "+COLUMN_EVENEMENT_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_EVENEMENTS,evenementValues,COLUMN_EVENEMENT_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ID)))});

            }
        }
        else {
            Long l = db.insert(TABLE_EVENEMENTS,null,evenementValues);
        }



    }

    public void enregistrerEvenementEnLocal(int id, int etat, int typeevenement, String nom,String telephone,
                                              String image, String date_evenement, String description,
                                              String lieu, String created_at,
                                              String updated_at,SQLiteDatabase db) {
        ContentValues evenementValues = new ContentValues();
        evenementValues.put(COLUMN_EVENEMENT_ID,id);
        evenementValues.put(COLUMN_EVENEMENT_ETAT,etat);
        evenementValues.put(COLUMN_EVENEMENT_NOM,nom);
        evenementValues.put(COLUMN_EVENEMENT_TYPE_EVENEMENT,typeevenement);
        evenementValues.put(COLUMN_EVENEMENT_TELEPHONE,telephone);
        evenementValues.put(COLUMN_EVENEMENT_IMAGE,image);
        evenementValues.put(COLUMN_EVENEMENT_DATE,date_evenement);
        evenementValues.put(COLUMN_EVENEMENT_DESCRIPTION,description);
        evenementValues.put(COLUMN_EVENEMENT_LIEU,lieu);
        evenementValues.put(COLUMN_EVENEMENT_CREATED_AT,created_at);
        evenementValues.put(COLUMN_EVENEMENT_UPDATED_AT,updated_at);
        evenementValues.put(COLUMN_EVENEMENT_SYNC_STATUS,COLUMN_TABLE_UNSYNCHRONIZED);
        Long l = db.insert(TABLE_EVENEMENTS,null,evenementValues);



    }

    public void enregistrerLieuDepuisApi(int id, int etat, int typelieu, String nom, String telephone, String adresse,
                                         String description, String siteweb, String image,
                                         String created_at, String updated_at, SQLiteDatabase db) {
        ContentValues lieuValues = new ContentValues();
        lieuValues.put(COLUMN_LIEU_ID,id);
        lieuValues.put(COLUMN_LIEU_ETAT,etat);
        lieuValues.put(COLUMN_LIEU_SYNC_STATUS,COLUMN_TABLE_SYNCHRONIZED);
        lieuValues.put(COLUMN_LIEU_TYPE_LIEU,typelieu);
        lieuValues.put(COLUMN_LIEU_NOM,nom);
        lieuValues.put(COLUMN_LIEU_TELEPHONE,telephone);
        lieuValues.put(COLUMN_LIEU_ADRESSE,adresse);
        lieuValues.put(COLUMN_LIEU_DESCRIPTION,description);
        lieuValues.put(COLUMN_LIEU_SITE_WEB,siteweb);
        lieuValues.put(COLUMN_LIEU_IMAGE,image);
        lieuValues.put(COLUMN_LIEU_CREATED_AT,created_at);
        lieuValues.put(COLUMN_LIEU_UPDATED_AT,updated_at);

        String query = "SELECT * FROM "+TABLE_LIEUX+" WHERE "+COLUMN_LIEU_ID+" = "+id+" ";
        SQLiteDatabase dbz = this.getReadableDatabase();
        Cursor cursor = dbz.rawQuery(query,null);

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                db.update(TABLE_LIEUX,lieuValues,COLUMN_EVENEMENT_ID+" = ?",new String[] {String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ID)))});

            }
        }
        else {
            Long l = db.insert(TABLE_LIEUX,null,lieuValues);
        }




    }

    public void enregistrerLieuEnLocal(int id, int etat, int typelieu, String nom, String telephone, String adresse,
                                         String description, String siteweb, String image,
                                         String created_at, String updated_at, SQLiteDatabase db) {
        ContentValues lieuValues = new ContentValues();
        lieuValues.put(COLUMN_LIEU_ID,id);
        lieuValues.put(COLUMN_LIEU_ETAT,etat);
        lieuValues.put(COLUMN_LIEU_SYNC_STATUS,COLUMN_TABLE_UNSYNCHRONIZED);
        lieuValues.put(COLUMN_LIEU_TYPE_LIEU,typelieu);
        lieuValues.put(COLUMN_LIEU_NOM,nom);
        lieuValues.put(COLUMN_LIEU_TELEPHONE,telephone);
        lieuValues.put(COLUMN_LIEU_ADRESSE,adresse);
        lieuValues.put(COLUMN_LIEU_DESCRIPTION,description);
        lieuValues.put(COLUMN_LIEU_SITE_WEB,siteweb);
        lieuValues.put(COLUMN_LIEU_IMAGE,image);
        lieuValues.put(COLUMN_LIEU_CREATED_AT,created_at);
        lieuValues.put(COLUMN_LIEU_UPDATED_AT,updated_at);
        Long l = db.insert(TABLE_LIEUX,null,lieuValues);


    }




    public  int idDernierOffre(){
        int id = 0;
        String query = "SELECT id FROM "+TABLE_OFFRES+" ORDER BY "+COLUMN_OFFRE_ID+" DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_OFFRE_ID));

            }
        }


        return id;

    }

    public  int idDernierEvenement(){
        int id = 0;
        String query = "SELECT id FROM "+TABLE_EVENEMENTS+" ORDER BY "+COLUMN_OFFRE_ID+" DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENEMENT_ID));

            }
        }


        return id;

    }

    public  int idDernierLieu(){
        int id = 0;
        String query = "SELECT id FROM "+TABLE_LIEUX+" ORDER BY "+COLUMN_OFFRE_ID+" DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_LIEU_ID));

            }
        }


        return id;

    }

    public  int idDomaineFromNom(String nom ){
        int id = 0;
        String query = "SELECT id FROM "+TABLE_DOMAINES+" WHERE "+COLUMN_DOMAINE_NOM+" = '"+nom+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_DOMAINE_ID));

            }
        }


        return id;

    }



    public  int idDiplomeFromNom(String nom ){
        int id = 0;
        String query = "SELECT id FROM "+TABLE_DIPLOMES+" WHERE "+COLUMN_DIPLOME_NOM+" = '"+nom+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_DIPLOME_ID));

            }
        }


        return id;

    }

    public  int idTypeOffreFromNom(String nom ){
        int id = 0;
        String query = "SELECT "+COLUMN_TYPE_OFFRE_ID+" FROM "+TABLE_TYPE_OFFRES+" WHERE "+COLUMN_TYPE_OFFRE_NOM+" = '"+nom+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OFFRE_ID));

            }
        }


        return id;

    }

    public  int idTypeEvenementFromNom(String nom ){
        int id = 0;
        String query = "SELECT "+COLUMN_TYPE_EVENEMENT_ID+" FROM "+TABLE_TYPE_EVENEMENTS+" WHERE "+COLUMN_TYPE_EVENEMENT_NOM+" = '"+nom+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OFFRE_ID));

            }
        }


        return id;

    }

    public  int idTypeLieuFromNom(String nom ){
        int id = 0;
        String query = "SELECT "+COLUMN_TYPE_LIEU_ID+" FROM "+TABLE_TYPE_LIEUX+" WHERE "+COLUMN_TYPE_LIEU_NOM+" = '"+nom+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_LIEU_ID));

            }
        }


        return id;

    }


    public  String nomDomaineFromId(int id){
        String nom = null;
        String query = "SELECT nom FROM "+TABLE_DOMAINES+" WHERE "+COLUMN_DOMAINE_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                nom = cursor.getString(cursor.getColumnIndex(COLUMN_DOMAINE_NOM));

            }
        }


        return nom;

    }



    public  String nomDiplomeFromId(int id){
        String nom = "popo";
        String query = "SELECT nom FROM "+TABLE_DIPLOMES+" WHERE "+COLUMN_DIPLOME_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                nom = cursor.getString(cursor.getColumnIndex(COLUMN_DIPLOME_NOM));

            }
        }


        return nom;

    }

    public  String nomTypeOffreFromId(int id){
        String nom = null;
        String query = "SELECT nom FROM "+TABLE_TYPE_OFFRES+" WHERE "+COLUMN_TYPE_OFFRE_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OFFRE_NOM));

            }
        }


        return nom;

    }






    public  String nomTypeEvenementFromId(int id){
        String nom = null;
        String query = "SELECT nom FROM "+TABLE_TYPE_EVENEMENTS+" WHERE "+COLUMN_TYPE_EVENEMENT_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_EVENEMENT_NOM));

            }
        }


        return nom;

    }



    public  String nomTypeLieuFromId(int id){
        String nom = null;
        String query = "SELECT nom FROM "+TABLE_TYPE_LIEUX+" WHERE "+COLUMN_TYPE_LIEU_ID+" = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_LIEU_NOM));

            }
        }


        return nom;

    }

    public ArrayList<String> listeDesDomaines(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_DOMAINES_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_DOMAINE_NOM));
                list.add(nom);
            }
        }

        return list;

    }

    public ArrayList<String> listeDesTypesEvenements(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_TYPEEVENEMENTS_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_EVENEMENT_NOM));
                list.add(nom);
            }
        }

        return list;

    }


    public ArrayList<String> listeDesTypesLieux(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_TYPELIEUX_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_LIEU_NOM));
                list.add(nom);
            }
        }

        return list;

    }


    public  void updateOffreSync(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_OFFRE_SYNC_STATUS, COLUMN_TABLE_SYNCHRONIZED);
//        db.update(TABLE_OFFRES, contentValues, COLUMN_OFFRE_ID + " = ?", new String[] {id});
        db.delete(TABLE_OFFRES, COLUMN_OFFRE_ID+" = ?",new String[] {id});



    }
    public  void updateEvenementSync(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EVENEMENTS, COLUMN_EVENEMENT_ID+" = ?",new String[] {id});



    }

    public  void updateLieuSync(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIEUX, COLUMN_LIEU_ID+" = ?",new String[] {id});



    }





    public ArrayList<String> listeDesTypesOffres(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_TYPEOFFRES_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OFFRE_NOM));
                list.add(nom);
            }
        }

        return list;

    }

    public ArrayList<String> listeDesDiplomes(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_DIPLOMES_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_DIPLOME_NOM));

                list.add(nom);
            }
        }

        return list;

    }

    public CharSequence[] tableauDeDomaines(){

        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(LISTES_DOMAINES_EN_LOCAL_APPROUVES,null);
        if (cursor.getCount() >0){
            i = cursor.getCount();
            CharSequence domaines[] = new CharSequence[i];
            while (cursor.moveToNext()){
                String nom = cursor.getString(cursor.getColumnIndex(COLUMN_DOMAINE_NOM));
                System.out.println(nom);
                domaines[i] = nom;
                System.out.println(domaines[i]);
            }
            return  domaines;
        }
        return  null;
    }


}
