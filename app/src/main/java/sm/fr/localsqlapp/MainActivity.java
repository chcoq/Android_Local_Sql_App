package sm.fr.localsqlapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
