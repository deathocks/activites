package com.montmo.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Julien on 2016-12-08.
 */
public class Dinosaurs1 extends AppCompatActivity {
    private Dinosaurs dinosaurs;
    private Spinner listDino;
    private ImageButton imgButtonDino;
    private PopupMenu popupMenu;

    public static final String NOM_PACKAGE = Pret1.class.getPackage().getName();
    public static final String CLE_PRET = NOM_PACKAGE + ".DINOSAURS1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino);

        menuDrawerLayour();

        RecupererComposant();

        dinosaurs = new Dinosaurs();

        String durer = listDino.getItemAtPosition(0).toString();
        dinosaurs.setNom(durer);

        //Définir l'écouteur du spinner des mois dans une variable.
        listDino.setOnItemSelectedListener(ecouterSpinner);

        menuContextuel();
    }

    private void RecupererComposant() {
        listDino = (Spinner) findViewById(R.id.liste_dino);
        imgButtonDino = (ImageButton) findViewById(R.id.image_dino);
    }

    public void menuContextuel() {
        //Récuperer le champ text pour le menu contextuel pop-pu
        imgButtonDino = (ImageButton) findViewById(R.id.image_dino);
        //Créer l'objet PopupMenu en précisant le contexte de l'activité
        // et la vue à laquelle il sera attaché.


        popupMenu = new PopupMenu(this, imgButtonDino);

        //Désérialiser le menu contextuel pop-up. Transformer en véritable objet.
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());

        //Gérer les sélections des entrées du menu contextuel pop-up.
        popupMenu.setOnMenuItemClickListener(ecouterPopupMenu);

        //Gérer les sélections des entrées du menu contextuel pop-up.
        imgButtonDino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vue) {
                //Afficher le menu contextuel pop-up.
                popupMenu.show();
            }
        });
    }

    //Variable qui gère les choix du PopupMenu.
    PopupMenu.OnMenuItemClickListener ecouterPopupMenu =
            new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Retourne false par défault, car elle ne sait pas gérer l'item.
                    boolean traiter = false;

                    // item.getItemId() contient l'identifiant de l'item cliqué
                    switch (item.getItemId()) {
                        case R.id.menu_annuler:
                            Toast.makeText(getApplicationContext(), R.string.choix_annuler_search, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.menu_search:
                            Toast.makeText(getApplicationContext(), R.string.choix_search, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent = new Intent(Dinosaurs1.this, Dinosaurs2.class);
                            startActivity(intent);

                            break;
                    }
                    return traiter;
                }

            };

    private AdapterView.OnItemSelectedListener ecouterSpinner =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    //Quand changement dans la sélection.
                    // parent.getItemAtPosition( position ) permet de
                    // récupérer l'item qui a été sélectionné.
                    String itemChoisi =
                            parent.getItemAtPosition(position).toString();
                    //Affiche un toast de l'item choisi.
                    Toast.makeText(getApplicationContext(), itemChoisi,
                            Toast.LENGTH_SHORT).show();
                    String nom = listDino.getItemAtPosition(position).toString();
                    dinosaurs.setNom(nom);


                    int img = getResources().getIdentifier("com.montmo.activites:mipmap/" + itemChoisi.toLowerCase(), null, null);

                    imgButtonDino.setImageResource(img);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Quand la sélection disparait.
                    // Quand l'item choisi n'est plus disponible
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_barre_actions, menu);
        return true;
    }

    private ListView listView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //Méthode pour tester la création d'un menu glissant à gauche de l'application
    private void menuDrawerLayour() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.list_navigation);

        //Lier le drawerLayout à la barred'actions.
        //Utilisation de la flèche de remontée pour afficher ;e menu.
        // Le constructeur prend en paramètres : l'activité qui accueille le drawer, le drawerLayout
        // et deux chaines utilisées à l'ouvertures et à la fermeture du menu (pour l'accessibilité).
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir_menu, R.string.fermer_menu);

        //Basculer entre l'icone hamburger et l'icone de la fleche de remontée.
        drawerToggle.setDrawerIndicatorEnabled(true);

        //Lier le drawerToggle au drawerLayout
        drawerLayout.addDrawerListener(drawerToggle);

        //Récuperer une référence à la bare avec Appcompat
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        //Ajouter la flèche de remonté et la rendre cliquable
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Défénir l'écouteur de la liste dans une variable
        listView.setOnItemClickListener(ecouterListView);
    }

    //Définition de la variable qui écoute la liste listeNavigation du menu glissant
    private AdapterView.OnItemClickListener ecouterListView =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    //parent.getItemAtPosition(position) permet de
                    //recuperer l'item qui a été sélectionné.
                    String itemChoisi = parent.getItemAtPosition(position).toString();
                    String message = "Item : " + itemChoisi;

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    //Exemple d'intention explicite.
                    //La nouvelle intention contient le contexte de l'activité
                    // appelant et le nom de l'activité.
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if (position == 0) {
                        intent = new Intent(Dinosaurs1.this, Pret1.class);
                        startActivity(intent);
                    } else if (position == 1) {
                        intent = new Intent(Dinosaurs1.this, Conifere1.class);
                        startActivity(intent);
                    } else if (position == 2) {
                        intent = new Intent(Dinosaurs1.this, Dinosaurs1.class);
                        startActivity(intent);
                    }


                    //Fermer le draerLayout après une selection
                    drawerLayout.closeDrawers();

                }
            };

    //Méthode appelée après les méthodes onStart et onRestoreInstnceState
    //pour finaliser les initialisation.
    //Ici, on veut synchroniser le drawerToggle pour qu'il bascule entre l'icone hamburger
    //et l'icone de la fleche de remontee dans la barre d'actions.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //Gestionnaire d'événements pour la barre d'actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Return false by default
        boolean traiter = super.onOptionsItemSelected(item);

        // item.getItemid() contient l'identifiant de l'item cliqué
        switch (item.getItemId()) {
            //Traitement de chaque item de la barre d'actions
            case R.id.menu_accueil:
                Toast.makeText(getApplicationContext(), R.string.action_accueil,
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                traiter = true;
                break;
            case R.id.menu_aide:
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Dinosaurs1.this);
                boiteAlert.setTitle(R.string.action_aide);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_dinosaurs);

                //Écouteur pour le bouton qui se trouvera tout à droite.
                boiteAlert.setPositiveButton(R.string.txt_alertdialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Traitement pour le bouton tout à droite.
                        Toast.makeText(getApplicationContext(), R.string.txt_alertdialog_ok,
                                Toast.LENGTH_LONG).show();
                    }
                });
                //Créer un AlertDialog
                AlertDialog boiteAlertDialog = boiteAlert.create();
                //Afficher la boite de dialog
                boiteAlertDialog.show();


                traiter = true;
                break;

            default:
                break;
        }

        //Deleguer le clic de l'icone hamburger ou de la fleche de remontee au DrawerLayout
        //et non a la barre d'action
        if (drawerToggle.onOptionsItemSelected(item)) {
            traiter = true;

        }

        //Return true si bien gere
        return traiter;
    }
}
