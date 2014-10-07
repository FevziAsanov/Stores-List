package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import helper_classes.TableConstants;
import model.Author;
import model.Product;

/**
 * Created by fevzi on 03.10.14.
 */
public class DBAdapter {

    private String LOG_TAG = "myLogsInAdapter";
    private DBAdapter.SQLHelper sqlHelper;

    public DBAdapter(Context context)
    {
        sqlHelper = new SQLHelper(context);

    }
    public void deleteAll()
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        int clearCount = db.delete(TableConstants.PRODUCT, null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        clearCount = db.delete(TableConstants.AUTHOR, null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        db.close();
    }

    public void insertProducts(ArrayList<Product> products)
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        ContentValues cvAth = new ContentValues();
        ContentValues cvPr = new ContentValues();
        int ref_auth[] = new int[products.size()];
        int j=0;

        for (int i = 0; i < products.size(); i++) {

            cvAth.put(TableConstants.EMAIL, products.get(i).getAuthor().getEmail());
            cvAth.put(TableConstants.NAME, products.get(i).getAuthor().getName());
            cvAth.put(TableConstants.TOKEN, products.get(i).getAuthor().getToken());

            db.insert(TableConstants.AUTHOR, null, cvAth);

        }
        String [] s ={"%"};
        Cursor c= db.rawQuery(" SELECT "+TableConstants.ID+
                " FROM " +TableConstants.AUTHOR+
                " WHERE "+TableConstants.ID+" LIKE ?", s);


        if(c.moveToFirst())
        {
            do {
                ref_auth[j] = c.getInt(c.getColumnIndex(TableConstants.ID));
                j++;

            }while (c.moveToNext());

        }else   Log.d(LOG_TAG, "0 rows");

        c.close();

        for (int i = 0; i < products.size(); i++) {

            cvPr.put(TableConstants.ID, products.get(i).getId());
            cvPr.put(TableConstants.LONGITUDE, products.get(i).getLng());
            cvPr.put(TableConstants.LATITUDE, products.get(i).getLat());
            cvPr.put(TableConstants.TITLE, products.get(i).getTitle());
            cvPr.put(TableConstants.AVATAR, products.get(i).getAvatar());
            cvPr.put(TableConstants.DESCRIPTION, products.get(i).getDescription());
            cvPr.put(TableConstants.AUTHOR_ID, ref_auth[i]);

            db.insert(TableConstants.PRODUCT, null, cvPr);
        }
        db.close();
    }
    public List<Product> getAllProducts()
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();


        Cursor c = db.query(TableConstants.PRODUCT,null, null, null, null, null, null);
        Cursor c1 = db.query(TableConstants.AUTHOR,null, null, null, null, null, null);

        if (c.moveToLast() && c1.moveToLast()) {

            int  titleColIndex = c.getColumnIndex(TableConstants.TITLE);
            int  descColIndex = c.getColumnIndex(TableConstants.DESCRIPTION);

            do {
                Product product = new Product();
                Author author = new Author();

                Log.d(LOG_TAG,
                        ", " +TableConstants.TITLE+" = " + c.getString(titleColIndex) +
                                "," +TableConstants.DESCRIPTION+" = " + c.getString(descColIndex));

                product.setTitle(c.getString(titleColIndex));
                product.setId(c.getInt(c.getColumnIndex(TableConstants.ID)));
                product.setLat(c.getInt(c.getColumnIndex(TableConstants.LATITUDE)));
                product.setLng(c.getInt(c.getColumnIndex(TableConstants.LONGITUDE)));
                product.setDescription(c.getString(descColIndex));
                author.setName(c1.getString(c1.getColumnIndex(TableConstants.NAME)));
                author.setEmail(c1.getString(c1.getColumnIndex(TableConstants.EMAIL)));
                product.setAuthor(author);

                products.add(product);

            } while (c.moveToPrevious()&&c1.moveToPrevious());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        c1.close();

        db.close();

        return  products;
    }

    public String [] getDescription(int id)
    {
        String [] s = new String[3];
        int author_id=0;
        String [] args = {""+id};
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT "+TableConstants.DESCRIPTION+" , "+TableConstants.AUTHOR_ID +
                " FROM "+TableConstants.PRODUCT+"" +
                " WHERE "+TableConstants.ID+"=?",args);

        if(c.moveToLast()) {
            do {
                s[0] = c.getString(c.getColumnIndex(TableConstants.DESCRIPTION));
                author_id = c.getInt(c.getColumnIndex(TableConstants.AUTHOR_ID));

            } while (c.moveToPrevious());
        }
        else Log.d(LOG_TAG, "rows 0");

        c.close();

        String [] ref_author ={""+author_id};

        Cursor c1 = db.rawQuery("SELECT "+TableConstants.NAME +","+TableConstants.EMAIL +
                " FROM "+ TableConstants.AUTHOR+
                " WHERE "+TableConstants.ID+"=?",ref_author);

        if(c1.moveToLast()) {
            do {
                s[1] = c1.getString(c1.getColumnIndex(TableConstants.NAME));
                s[2] = c1.getString(c1.getColumnIndex(TableConstants.EMAIL));

            } while (c1.moveToPrevious());
        }
        else Log.d(LOG_TAG, "rows 0");
         c1.close();

        return s;
    }

    /**
     * Created by fevzi on 30.09.14.
     */
    private static class SQLHelper extends SQLiteOpenHelper {

        final String LOG_TAG = "myLogs";

        public SQLHelper(Context context) {
            super(context, TableConstants.DATABASE_NAME, null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");

            db.execSQL("CREATE TABLE "+TableConstants.PRODUCT+"( "+
                    TableConstants.ID+" INTEGER PRIMARY KEY, "+
                    TableConstants.LONGITUDE+" INTEGER, " +
                    TableConstants.LATITUDE+" INTEGER, " +
                    TableConstants.TITLE+" TEXT , " +
                    TableConstants.DESCRIPTION+ " TEXT, " +
                    TableConstants.AVATAR+" TEXT, " +
                    TableConstants.AUTHOR_ID+" INTEGER );");

            db.execSQL("CREATE TABLE "+TableConstants.AUTHOR+"( "+TableConstants.ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableConstants.NAME+" TEXT," +
                    TableConstants.EMAIL+" TEXT," +
                    TableConstants.TOKEN+ " TEXT );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }
}
