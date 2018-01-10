package sm.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sm.fr.localsqlapp.database.ContactDAO;
import sm.fr.localsqlapp.database.DatabaseHandler;
import sm.fr.localsqlapp.model.Contact;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final String LIFE_CYCLE = "cycle de vie";
    private ListView contactListView;
    private List<Contact> contactList;
    private Integer selectedIndex;
    private Contact selectedPerson;
    private Intent Intention;
    private  DatabaseHandler db;
    private  ContactDAO dao;
    private ContactArrayAdapter contactAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instenciation de la connexion à  la base de données
        this.db = new DatabaseHandler( this);
        //Instanciation du DAO pour les contacts
        this.dao= new ContactDAO(this.db);

        //Référence au widget ListView sur le layout
         contactListView = findViewById(R.id.contactListView);
        contactListInit();
        //Récupération des données persistées sur le layout
        if(savedInstanceState!=null){
            //Récupération de l'index de séléction sauvergarder
            this.selectedIndex = savedInstanceState.getInt("selectedIndex");
            if (this.selectedIndex !=null){
                this.selectedPerson = this.contactList.get(this.selectedIndex);
            }
        }
        Log.i(LIFE_CYCLE, "onCreate: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==1 && requestCode == RESULT_OK){
            Toast.makeText(this,"Mise à jour effectuée",Toast.LENGTH_SHORT).show();
            this.contactListInit();
        }
    }

    private void contactListInit() {
        //Récupération  de la  liste des contacts
        contactList = this.dao.findAll();
        //Création d'un contactArrayAdapter
         contactAdapter = new ContactArrayAdapter(this, contactList);
        //Définition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);

        //Définition d'un écouteur d'événement pour
        contactListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajout des entrées du fichier mainOptionMenu
        //au menu contextuel de l'activité
        getMenuInflater().inflate(R.menu.main_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent Intention= new Intent(this, FormActivity.class);
        switch (item.getItemId()) {
            case R.id.mainMenuOptionDelete:
                this.deleteSelectedContact();
                break;
            case R.id.mainMenuOptionEdit:
                //passage des paramètres à l'intention
                Intention.putExtra("name",this.selectedPerson.getName());
                Intention.putExtra("first_name",this.selectedPerson.getFirstName());
                Intention.putExtra("email",this.selectedPerson.getEmail());
                Intention.putExtra("id",String.valueOf(this.selectedPerson.getId()));

                //Lancement de l'activité FormActivity
                startActivityForResult(Intention, 1);
                this.contactListInit();
                break;
        }

        return true;
    }

    //suppression du contact selectionné
    private void deleteSelectedContact() {
        if (this.selectedIndex != null) {
            try{
                Long id = this.selectedPerson.getId();
                this.dao.deleteOneById(Long.valueOf(id));
                //Rénitialisation de la laiste de contacts
               this.contactListInit();
            }catch ( SQLiteException ex){
                Toast.makeText(this,"Impossible de supprimer",Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Vous devez selectionner un contact", Toast.LENGTH_LONG).show();
        }

    }



    /**
     * lancement de l'activité formulaire au clic sur un bouton
     *
     * @param view
     */
    public void onAddContact(View view) {
        Intent FormIntent = new Intent(this, FormActivity.class);
        startActivity(FormIntent);
    }




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.selectedIndex = i;
        this.selectedPerson = contactList.get(i);
        Toast.makeText(this, "ligne" + i + "cliquer", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LIFE_CYCLE,"onSart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LIFE_CYCLE, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LIFE_CYCLE, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LIFE_CYCLE,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LIFE_CYCLE,"onDestroy");
    }

    /**
     * Persistance des données avant déstruction de mon acticité
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (this.selectedIndex != null){
            outState.putInt("selectedIndex",this.selectedIndex);
        }

        super.onSaveInstanceState(outState);
    }
}
