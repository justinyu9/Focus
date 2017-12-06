package cmps121.focus.pokeapi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alston-PC on 12/4/2017.
 */

public class PokemonDatabase extends SQLiteOpenHelper {
    private final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PokeCollection.db";
    public static final String TABLE_PRODUCTS = "PokeCollection";
    public static final String COL_1 = "name";
    public static final String COL_2 = "id";
    public static final String COL_3 = "hp";
    public static final String COL_4 = "attack";
    public static final String COL_5 = "defense";
    public static final String COL_6 = "height";
    public static final String COL_7 = "weight";

    public PokemonDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PRODUCTS + "(name TEXT, id TEXT, hp TEXT, attack TEXT, defense TEXT, height TEXT, weight TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public boolean insertData(String name, String id, String hp, String attack, String defense, String height, String weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, id);
        contentValues.put(COL_3, hp);
        contentValues.put(COL_4, attack);
        contentValues.put(COL_5, defense);
        contentValues.put(COL_6, height);
        contentValues.put(COL_7, weight);
        long result = db.insert(TABLE_PRODUCTS, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PRODUCTS, null);
        return res;
    }
}
