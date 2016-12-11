/**
 * Auteure : Julien Canuel & Benoit-Lynx Hamel
 * Fichier : Dinosaurs.java
 * Date    : 12 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 */
package com.montmo.activites;

import android.os.Parcel;
import android.os.Parcelable;

public class Dinosaurs implements Parcelable {

    // Variables d'instances.
    private String nom;

    // Constructeur de base.
    public Dinosaurs() {
        this("");
    }

    // Constructeur avec des valeurs.
    public Dinosaurs(String nom) {
        this.setNom(nom);
    }

    // Accesseur, mutateur pour le nom.

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    //==============================================================================
    // Ce qui suit concerne tout ce qui faut implémenter pour l'interface Parcelable.

    // Implémentation de la méthode qui précise si l'objet contient des objets spéciaux de type
    // File Descriptor (descripteur de fichier).

    @Override
    public int describeContents() {
        return 0;
    }

    // Implémentation de la méthode qui sert à écrire le contenu de l'objet dans un Parcel.

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getNom());
    }

    // Constructeur interne prenant comme argument un Parcel pour notre Dinosaurs.
    // Permet de reconstruire l’objet à partir d’un Parcel.
    // Pour construire une instance de Dinosaurs à partir d’un Parcel.

    protected Dinosaurs(Parcel in) {
        // Les valeurs sont lues dans le même ordre que celui défini dans la méthode writeToParcel.
        this.setNom(in.readString());
    }

    // Création d'un objet CREATOR permettant de créer une instance de l'objet Dinosaurs depuis
    // un Parcel. Correspond à la conversion Parcel vers l'objet personnalisé.
    // Le CREATOR permet d’indiquer comment l'objet de type Parcelable sera créé.

    public static final Creator<Dinosaurs> CREATOR = new Creator<Dinosaurs>() {

        // Implémentation de la méthode qui permet de créer un objet Dinosaurs depuis un Parcel.
        @Override
        public Dinosaurs createFromParcel(Parcel in) {
            return new Dinosaurs(in);
        }

        // Implémentation de la méthode qui permet de créer un tableau de Dinosaurs dont la
        // taille est spécifiée en paramètre.
        @Override
        public Dinosaurs[] newArray(int size) {
            return new Dinosaurs[size];
        }
    };
}
