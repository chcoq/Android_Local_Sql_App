package sm.fr.localsqlapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
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
     *
     * @param id
     * @return
     */
    public Contact findOnById(int id) throws SQLiteException {
        //Exécution de la requête
        String[] params = {String.valueOf(id)};
        String sql = "SELECT id,name,first_name,email FROM contacts WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase()
                .rawQuery(sql, params);
        //Instanciation d'un contact
        Contact contact = new Contact();
        //Hydratation du contact
        if (cursor.moveToNext()) {
            contact = hydrateContact(cursor);
        }

        //Fermeture du curseur
        cursor.close();
        return contact;
    }

    private Contact hydrateContact(Cursor cursor) {
        Contact contact = new Contact();

        contact.setId(cursor.getLong(0));
        contact.setFirstName(cursor.getString(1));
        contact.setName(cursor.getString(2));
        contact.setEmail(cursor.getString(3));
        return contact;
    }

    /**
     * @return List<Contact> une liste de contact
     */
    public List<Contact> findAll() throws SQLiteException {
        //Instanciation  de la liste des contacts
        List<Contact> contactList = new ArrayList<>();

        //Exécution de la requête sql
        String sql = "SELECT id,name,first_name,email FROM contacts";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, null);
        //Boucle sur le curseur
        while (cursor.moveToNext()) {
            contactList.add(this.hydrateContact(cursor));
        }
        //Fermeture du curseur
        cursor.close();
        return contactList;
    }

    /**
     * Suppression d'un contatct en fonction de sa clef primaire
     *
     * @ params
     * throws SQLiteException
     */

    public void deleteOneById(Long id) throws SQLiteException {
        String[] params = {id.toString()};
        String sql = "DELETE FROM contacts WHERE id=?";
        this.db.getWritableDatabase().execSQL(sql, params);
    }

    public void persist(Contact entity) {
        if (entity.getId() == null) {
            this.insert(entity);
        } else {
            this.update(entity);
        }
    }

    private ContentValues getContenteValuesFormEntity(Contact entity) {
        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        values.put("first_Name", entity.getFirstName());
        values.put("email", entity.getEmail());
        return values;
    }

    private void insert(Contact entity) {
        Long id = this.db.getWritableDatabase().insert("contacts", null, this
                .getContenteValuesFormEntity(entity));
        entity.setId(id);

    }

    private void update(Contact entity) {
        String[] params = {entity.getId().toString()};
        this.db.getWritableDatabase().update(
                "contacts",
                this.getContenteValuesFormEntity(entity),
                "id=?",
                params

        );
    }




}//fin class ContactDAO
