package com.montmo.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Julien on 2016-12-08.
 */
public class Dinosaurs2 extends AppCompatActivity {
    private WebView vueWeb;
    private WebSettings param;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        menuDrawerLayour();

        //Récuperer le composant Webview ppour y afficher un contenu Web.<
        vueWeb = (WebView) findViewById(R.id.web_page);

        //Accéder aux parametres du composant WebView.
        param = vueWeb.getSettings();

        //Activer JavaScript.
        param.setJavaScriptEnabled(true);

        //Afficher les boutons de zoom et activer le pincer.
        param.setBuiltInZoomControls(true);

        //Gestionnaire pendant le chargement.
        vueWeb.setWebChromeClient(ecouterPendantChargementWeb);
        //Gestionnaire en cas d'errr ou fin normal du chargement
        vueWeb.setWebViewClient(ecouterFinChargement);

        Intent intent = getIntent();
        Dinosaurs dino = intent.getParcelableExtra(Dinosaurs1.CLE_DINO);

        //Charger l'adresse url.
        vueWeb.loadUrl("http://ark.gamepedia.com/" + dino.getNom());

    }

    //Traitement pendant chargement du contenu web
    WebChromeClient ecouterPendantChargementWeb = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int progress){
            Toast.makeText(getApplicationContext(), Integer.toString(progress) + " %",
                    Toast.LENGTH_SHORT).show();
        }
    };

    //Traitement en cas d'erreur ou fin du chargement
    WebViewClient ecouterFinChargement = new WebViewClient(){
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error){
            String messErreur = res.getString(R.string.erreur_chargement_web);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                messErreur = messErreur.concat(error.getDescription().toString());
            }
            Toast.makeText(getApplicationContext(), messErreur, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onPageFinished(WebView view, String url){
            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
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
                    Intent intent;
                    if (position == 0) {
                        intent = new Intent(Dinosaurs2.this, Pret1.class);
                        startActivity(intent);
                    } else if (position == 1) {
                        intent = new Intent(Dinosaurs2.this, Conifere1.class);
                        startActivity(intent);
                    } else if (position == 2) {
                        intent = new Intent(Dinosaurs2.this, Dinosaurs1.class);
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
                traiter = true;
                break;
            case R.id.menu_aide:
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Dinosaurs2.this);
                boiteAlert.setTitle(R.string.action_aide);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_web_dinosaurs);

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