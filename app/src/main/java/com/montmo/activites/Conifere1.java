package com.montmo.activites;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lynx-Win7 on 2016-12-06.
 */

public class Conifere1 extends AppCompatActivity {
    public static final String CLE_CONIFERE = "ClefConifere";
    public static final String CLE_CONIFERE_IMAGE = "ClefidImage";
    public static final String CLE_CONIFERE_NOM = "ClefNomConifere";
    public static final String CLE_CONIFERE_WEB = "ClefWebConifere";
    public static final int REQUETE_TEXT_CONIFERE = 1;
    private String nomConifere;
    private int idImageConifere;
    private String webConifere;
    private Resources res;
    private ListView listViewConifere;
    private TextView textViewConifere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conifere1);
        res = getResources();

        menuDrawerLayour();
        recupererComposante();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_barre_actions, menu);
        return true;
    }

    private void recupererComposante(){
        listViewConifere = (ListView) findViewById(R.id.id_list_conifere);
        textViewConifere = (TextView) findViewById(R.id.id_img_conifere);
        listViewConifere.setOnItemClickListener(ecouterListViewConifere);
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
        actionBar.setSubtitle(R.string.titre_conifere);

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
                    if(position == 0){
                        //intent = new Intent(Conifere1.this, Pret1.class);
                        startActivity(intent);
                    } else if (position == 1) {
                        intent = new Intent(Conifere1.this, Conifere1.class);
                        startActivity(intent);
                    } else if (position == 2){

                    }
                    //Fermer le draerLayout après une selection
                    drawerLayout.closeDrawers();

                }
            };
    //Définition de la variable qui écoute la liste des coniferes
    private AdapterView.OnItemClickListener ecouterListViewConifere =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    //parent.getItemAtPosition(position) permet de
                    //recuperer l'item qui a été sélectionné.
                    //String itemChoisi = parent.getItemAtPosition(position).toString();

                    //Exemple d'intention explicite.
                    //La nouvelle intention contient le contexte de l'activité
                    // appelant et le nom de l'activité.
                    Intent intent = new Intent(Conifere1.this, Conifere2.class);
                    intent.putExtra(CLE_CONIFERE,position);
                    startActivityForResult(intent,REQUETE_TEXT_CONIFERE);

                }
            };

    private View.OnClickListener ecouterTextViewConifere = new View.OnClickListener(){
        @Override
        public void onClick (View vue){
            Intent intent = new Intent(Conifere1.this, Conifere3.class);
            intent.putExtra(CLE_CONIFERE_NOM,nomConifere);
            intent.putExtra(CLE_CONIFERE_WEB,webConifere);
            startActivity(intent);
        }
    };


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        //Verifier qe c'est la bonne activité
        if( requestCode == REQUETE_TEXT_CONIFERE){
            //Preparer le message a afficher
            String message = res.getString(R.string.mess_res_arbre);
            //Est-ce que le boutn pour terminer
            //a ete clique dans l'ecran suivant
            if (resultCode == Activity.RESULT_OK){
                //Appui du bouton Terminer
                idImageConifere = data.getIntExtra("ClefidImage", 0);
                nomConifere = data.getStringExtra("ClefNomConifere");
                webConifere = data.getStringExtra("ClefWebConifere");
                message += "\n" + nomConifere;
                textViewConifere.setText(message);
                textViewConifere.setCompoundDrawablesWithIntrinsicBounds( idImageConifere, 0, 0, 0 );
                textViewConifere.setOnClickListener(ecouterTextViewConifere);
            }else{
                //Appui du bouton back
                textViewConifere.setText("");
                textViewConifere.setCompoundDrawablesWithIntrinsicBounds(android.R.color.transparent, 0, 0, 0);
            }
        }
    }

    @Override
    public void onSaveInstanceState( Bundle savedInstanceState){
        if(BuildConfig.DEBUG){
            Log.i(this.getClass().getSimpleName(), "onSaveInstanceState est appelée");
        }
        savedInstanceState.putInt("idImageConifere",idImageConifere );
        savedInstanceState.putString("nomConifere", nomConifere);
        savedInstanceState.putString("webConifere", webConifere);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void  onRestoreInstanceState(Bundle savedInstanceState){
        if(BuildConfig.DEBUG){
            Log.i(this.getClass().getSimpleName(), "onRestoreInstanceState est appelée");
        }
        super.onRestoreInstanceState(savedInstanceState);
        idImageConifere = savedInstanceState.getInt("idImageConifere");
        nomConifere = savedInstanceState.getString("nomConifere");
        webConifere = savedInstanceState.getString("webConifere");

        if (nomConifere.isEmpty()){
            //Appui du bouton Terminer
            idImageConifere = data.getIntExtra("ClefidImage", 0);
            nomConifere = data.getStringExtra("ClefNomConifere");
            webConifere = data.getStringExtra("ClefWebConifere");
            String message = res.getString(R.string.mess_res_arbre);
            message += "\n" + nomConifere;
            textViewConifere.setText(message);
            textViewConifere.setCompoundDrawablesWithIntrinsicBounds( idImageConifere, 0, 0, 0 );
            textViewConifere.setOnClickListener(ecouterTextViewConifere);
        }else{
            //Appui du bouton back
            textViewConifere.setText("");
            textViewConifere.setCompoundDrawablesWithIntrinsicBounds(android.R.color.transparent, 0, 0, 0);
        }
    }

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
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Conifere1.this);
                boiteAlert.setTitle(R.string.action_aide);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_conifere);

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
