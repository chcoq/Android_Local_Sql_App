package sm.fr.localsqlapp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import sm.fr.localsqlapp.model.Contact;

public class ContactDAO {
    private DatabaseHandler db;

    public ContactDAO() {
    }

    public ContactDAO(DatabaseHandler db) {
        this.db = db;
    }

    /**
     * Récupération d'un contatct en fonction  de sa clef primaire
     * @param id
     * @return
     */
    public Contact findOnById(int id) throws SQLiteException{
        //Exécution de la requête
        String [] params ={String.valueOf(id)};
        String sql="SELECT id,name,first_name,email FROM contacts WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase()
                .rawQuery(sql,params);
        //Instanciation d'un contact
        Contact contact = new Contact();
        //Hydratation du contact
        if(cursor.moveToNext()){
            contact.setId(cursor.getLong(0));
            contact.setFirstName(cursor.getString(1));
            contact.setName(cursor.getString(2));
            contact.setEmail(cursor.getString(3));
        }

        //Fermeture du curseur
        cursor.close();
        return contact;
    }
}//fin class ContactDAO
