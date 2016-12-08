package com.montmo.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * Created by Julien on 2016-12-08.
 */
public class Pret2 extends AppCompatActivity {
    private Button boutonRetour;
    private TextView textMontant;
    private TextView textTaux;
    private TextView textDuree;
    private TextView textVersement;
    private TextView textInteret;
    private TextView textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);

        menuDrawerLayour();

        RecupererComposant();

        afficherResultats();

        boutonRetour.setOnClickListener(ecouterButton);

    }

    private void RecupererComposant() {
        boutonRetour = (Button) findViewById(R.id.txt_btn_retour);

        textMontant = (TextView) findViewById(R.id.id_titre_pret);
        textTaux = (TextView) findViewById(R.id.id_titre_taux);
        textDuree = (TextView) findViewById(R.id.id_titre_duree);
        textVersement = (TextView) findViewById(R.id.id_titre_versement);
        textInteret = (TextView) findViewById(R.id.id_titre_int_total);
        textTotal = (TextView) findViewById(R.id.id_titre_total);

    }

    private void afficherResultats() {
        Intent intent = getIntent();
        Pret pret = intent.getParcelableExtra(Pret1.CLE_PRET);


        textMontant.setText(String.format(Locale.getDefault(), "%1$.2f", pret.getMontant()));
        textDuree.setText(Integer.toString(pret.getDuree()));
        textTaux.setText(String.format(Locale.getDefault(), "%1$.2f", pret.getInteret()));
        textVersement.setText(String.format(Locale.getDefault(), "%1$.2f", pret.calculerVersement()));
        textInteret.setText(String.format(Locale.getDefault(), "%1$.2f", pret.calculerInteretTotal()));
        textTotal.setText(String.format(Locale.getDefault(), "%1$.2f", pret.calculerPretTotal()));


    }

    private View.OnClickListener ecouterButton =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

        actionBar.setSubtitle(R.string.sous_titre_infos_pret);
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
                        intent = new Intent(Pret2.this, Pret1.class);
                        startActivity(intent);
                    } else if (position == 1) {
                        intent = new Intent(Pret2.this, Conifere1.class);
                        startActivity(intent);
                    } else if (position == 2) {

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
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Pret2.this);
                boiteAlert.setTitle(R.string.aide_infos_pret);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_infos_pret);

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
