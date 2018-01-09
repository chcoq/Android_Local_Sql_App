package sm.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sm.fr.localsqlapp.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView contactListView;
    private List<Map<String,String>>contactList;
    private Integer selectedIndex;
    private Map<String,String>selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Référence au widget ListView sur le layout
        ListView contactListView = findViewById(R.id.contactListView);
        //Récupération  de la  ligne des contacts
        contactList= this.getAllContatcts();
        //Création d'un contactArrayAdapter
        ContactArrayAdapter contatctAdapter = new ContactArrayAdapter(this, contactList);
        //Définition de l'adapter de notre listView
        contactListView.setAdapter(contatctAdapter);

        //Définition d'un écouteur d'événement pour
        contactListView.setOnItemClickListener(this);
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
    private List<Map<String,String>> getAllContatcts(){
        //instanciation de la connexion à la base de données
        DatabaseHandler db= new DatabaseHandler(this);

        //Exécution de la requête de selection
        Cursor cursor= db.getReadableDatabase().rawQuery("SELECT name,first_name,email FROM contacts",null);
        //Instenciation de la ligne qui renvera les données.
        List<Map<String ,String>>contactList = new ArrayList<>();

        //Parcours du curseur
        while (cursor.moveToNext()){
            Map<String ,String> contactCols= new HashMap<>();
            //remplissage du tableau associatif en fonction des données du curseur
            contactCols.put("name",cursor.getString(0));
            contactCols.put("first_name",cursor.getString(1));
            contactCols.put("email",cursor.getString(2));
            //Ajout du Map à la liste
            contactList.add(contactCols);
        }
        return contactList;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.selectedIndex= i;
        this.selectedPerson = contactList.get(i);
        Toast.makeText(this,"ligne"+i +"cliquer",Toast.LENGTH_SHORT).show();
    }
}
