package sm.fr.localsqlapp;



import android.app.ActionBar;
import android.content.ContentValues;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
        insertValues.put("first_name", String.valueOf(firstnameEditText));
        insertValues.put("name", String.valueOf(nameEditText));
        insertValues.put("email", String.valueOf(emailEditText));
        //Insertiondes données
        try {
            db.getWritableDatabase().insert("contacts", null, insertValues);
            Toast.makeText(this,"Insertion Ok",Toast.LENGTH_LONG).show();

        }catch(SQLiteException ex){
            Log.e("SQL EXCEPTION", ex.getMessage());
        }
    }
}
