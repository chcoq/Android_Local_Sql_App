package sm.fr.localsqlapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class ContactArrayAdapter extends ArrayAdapter {

    //variable
    private Activity context;
    private List<Map<String, String>>data;

    private LayoutInflater inflater;

            //Constructeur d'objet
    public ContactArrayAdapter(@NonNull Context context, @NonNull List<Map<String,String>>data) {
        super(context, 0, data);

        this.data = data;
        this.context = (Activity) context;
        this.inflater = this.context.getLayoutInflater();
    }

    //Implémentation de Array Adapter


    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        //Instanciation  de la vue
        View view = this.inflater.inflate(R.layout.list_view_contact,parent,false);

    //récupération des données d'une ligne
    Map<String ,String> contactData= this.data.get(position);

    //Liaision entre les données et la vue
        TextView nameTextView = view.findViewById(R.id.listtextViewName);
        nameTextView.setText(contactData.get("name"));

        TextView firstnameTextView = view.findViewById(R.id.listtextViewFirstName);
        firstnameTextView.setText(contactData.get("first_name"));

        TextView emailTextView = view.findViewById(R.id.listtextViewEmail);
        emailTextView.setText(contactData.get("email"));

        return view;
    }
}
