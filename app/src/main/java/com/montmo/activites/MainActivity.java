package com.montmo.activites;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuDrawerLayour();


    }

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
                traiter = true;
                break;
            case R.id.menu_aide:
                Toast.makeText(getApplicationContext(), R.string.action_aide,
                        Toast.LENGTH_LONG).show();
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
