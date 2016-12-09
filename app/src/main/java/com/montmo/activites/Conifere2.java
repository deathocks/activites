package com.montmo.activites;

/**
 * Created by Lynx-Win7 on 2016-12-06.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Conifere2 extends AppCompatActivity {
    private Resources res;
    private Intent intent;
    private int choix;
    private Button btnRetour;
    private TextView titreConifere;
    private String[]tabChaines;
    private RecyclerView recyclerConifere;
    private ArrayList<Conifere> listeConifere;
    private ConifereAdapterRV conifereAdapterRV;

    // Ressources array des différents textes et images des items de la liste
    // pour aider à l'identification.
    private static final int[] TAB_AIGUILLES_CONIFERES = {
            R.array.tab_aiguilles_une,
            R.array.tab_aiguilles_faisceaux,
            R.array.tab_ecailles };
    private static final int[] TAB_IMAGES_AIGUILLES_CONIFERES = {
            R.array.tab_images_aiguilles_une,
            R.array.tab_images_aiguilles_faisceaux,
            R.array.tab_images_ecailles };
    // Ressources array des différents noms, images et pages web des conifères.
    private static final int[] TAB_CONIFERES = {
            R.array.tab_coniferes_aiguilles_une,
            R.array.tab_coniferes_aiguilles_faisceaux,
            R.array.tab_coniferes_ecailles };
    private static final int[] TAB_IMAGES_CONIFERES = {
            R.array.tab_images_coniferes_aiguilles_une,
            R.array.tab_images_coniferes_aiguilles_faisceaux,
            R.array.tab_images_coniferes_ecailles };
    private static final int[] TAB_WEB_CONIFERES = {
            R.array.tab_web_coniferes_aiguilles_une,
            R.array.tab_web_coniferes_aiguilles_faisceaux,
            R.array.tab_web_coniferes_ecailles };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conifere2);
        res = getResources();
        //Recuperer l'objet intent precedent
        intent = getIntent();
        //Recupere la ressource Extra
        choix = intent.getIntExtra(Conifere1.CLE_CONIFERE,0);
        menuDrawerLayour();
        recupererComposante();
        afficherEcouteListChoix();
    }

    private void afficherEcouteListChoix(){
        //Recuperer le composant RecyclerView
        recyclerConifere = (RecyclerView) findViewById(R.id.id_recycler_conifere);
        //si la liste a une taille on l'indiqu pour les performances
        recyclerConifere.setHasFixedSize(true);
        //creer le LayoutManager et l'appliquer a la liste
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerConifere.setLayoutManager(linearLayoutManager);
        //creerListeConifere();
        // TODO: 2016-12-09 doc 12, page 41 et 54 pis pleur criss

    }

    /*private ArrayList<Conifere> creerListeConifere(){

    }
*/
    private void recupererComposante(){
        btnRetour = (Button) findViewById(R.id.id_bouton_conifere);
        titreConifere = (TextView) findViewById(R.id.id_titre_conifere2);
        tabChaines = res.getStringArray ( R.array.tab_choix_aiguilles);
        titreConifere.setText(tabChaines[choix]);
        btnRetour.setOnClickListener(ecouterBtnRetour);
    }

    private View.OnClickListener ecouterBtnRetour = new View.OnClickListener(){
        @Override
        public void onClick (View vue){
            //setResult(RESULT_OK ,intent);
            finish();
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
        String[] tabSousTitre = res.getStringArray ( R.array.tab_choix_sous_titres);
        actionBar.setSubtitle(tabSousTitre[choix]);

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
                    Intent intent;
                    if(position == 0){
                        intent = new Intent(Conifere2.this, Pret1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (position == 1) {
                        intent = new Intent(Conifere2.this, Conifere1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (position == 2){
                        intent = new Intent(Conifere2.this, Dinosaurs1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Conifere2.this);
                boiteAlert.setTitle(R.string.aide_choix_conifere);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_choix_conifere);

                //Écouteur pour le bouton qui se trouvera tout à droite.
                boiteAlert.setPositiveButton(R.string.txt_alertdialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialog, int whichButton ){
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
