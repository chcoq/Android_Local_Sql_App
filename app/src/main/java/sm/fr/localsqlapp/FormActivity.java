package sm.fr.localsqlapp;



import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sm.fr.localsqlapp.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText firstNameEditText;
    EditText emailEditText;
    String  contactId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        //Récupération des Données
        Intent intention = getIntent();
    String name = intention.getStringExtra("name");
    String first_name = intention.getStringExtra("first_name");
    String email = intention.getStringExtra("email");
    String id = intention.getStringExtra("id");


    //Récupération de l'id dans une variable Globale
    this.contactId = id;
    //Référence aux editText
        this.emailEditText=findViewById(R.id.editTextEmail);
        this.nameEditText = findViewById(R.id.editTextNom);
        this.firstNameEditText= findViewById(R.id.editTextPrenom);

        //Affichage des données dans les champs de saisie
        this.firstNameEditText.setText(first_name);
        this.nameEditText.setText(name);
        this.emailEditText.setText(email);

    }
    public void onValid(View v) {
        Button clikedButton = (Button) v;

        //Récupération de la saisie de l'utilisateur
        EditText nameEditText = (EditText) findViewById(R.id.editTextNom);
        EditText firstnameEditText = (EditText) findViewById(R.id.editTextPrenom);
        EditText emailEditText = (EditText) findViewById(R.id.editTextEmail);


        //Instanciation de la connexion à l a base de données
        DatabaseHandler db = new DatabaseHandler(this);




        //Définition  des données à inserer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name", String.valueOf(firstnameEditText.getText()));
        insertValues.put("name", String.valueOf(nameEditText.getText()));
        insertValues.put("email", String.valueOf(emailEditText.getText()));
        //Insertiondes données
        try {
            if(this.contactId !=null){
                //Mise à jour d'un contact existant
                String[] params = {contactId};
                db.getWritableDatabase().update("contacts",
                        insertValues,
                        "id=?",
                        params);
                setResult(RESULT_OK);
                finish();

            }else //insertion d'un nouveau contact
            db.getWritableDatabase().insert("contacts", null, insertValues);
            Toast.makeText(this,"Insertion Ok",Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.editTextNom)).setText("");
            ((EditText) findViewById(R.id.editTextPrenom)).setText("");
            ((EditText) findViewById(R.id.editTextEmail)).setText("");



        }catch(SQLiteException ex){
            Log.e("SQL EXCEPTION", ex.getMessage());
        }
    }
}
