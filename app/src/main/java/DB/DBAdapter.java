package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Author;
import model.Product;
import model.ResultProduct;

/**
 * Created by fevzi on 03.10.14.
 */
public class DBAdapter {

    private String LOG_TAG = "myLogsInFragment";
    private DBAdapter.SQLHelper sqlHelper;

    public DBAdapter(Context context)
    {
        sqlHelper = new SQLHelper(context);

    }
    public void deleteAll()
    {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        int clearCount = db.delete("Product", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        clearCount = db.delete("Author", null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
        db.close();
    }


    public void insert(ArrayList<Product> products)
    {


        SQLiteDatabase db = sqlHelper.getWritableDatabase();



        ContentValues cvAth = new ContentValues();
        ContentValues cvPr = new ContentValues();
        int ref_auth = 0;

        for (int i = 0; i < products.size(); i++) {

            ref_auth++;

            cvAth.put("email", products.get(i).getAuthor().getEmail());
            cvAth.put("name", products.get(i).getAuthor().getName());
            cvAth.put("token", products.get(i).getAuthor().getToken());


            cvPr.put("id", products.get(i).getId());
            cvPr.put("lng", products.get(i).getLng());
            cvPr.put("lat", products.get(i).getLat());
            cvPr.put("title", products.get(i).getTitle());
            cvPr.put("avatar", products.get(i).getAvatar());
            cvPr.put("description",products.get(i).getDescription());
            cvPr.put("author_ID", ref_auth);

            long rowID = db.insert("Author", null, cvAth);
            //    Log.d(LOG_TAG, "row inserted, ID = " + rowID);

            rowID = db.insert("Product", null, cvPr);
            //       Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        }

        db.close();
    }
    public List<Product> readingFromDB()
    {



        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        ArrayList<Product> products = new ArrayList<Product>();


        Cursor c = db.query("Product",null, null, null, null, null, null);
        Cursor c1 = db.query("Author",null, null, null, null, null, null);

        if (c.moveToLast() && c1.moveToLast()) {

            int  titleColIndex = c.getColumnIndex("title");
            int  descColIndex = c.getColumnIndex("description");

            do {
                Product product = new Product();
                Author author = new Author();

                Log.d(LOG_TAG,
                        ", title = " + c.getString(titleColIndex) +
                                ", description = " + c.getString(descColIndex));

                product.setTitle(c.getString(titleColIndex));
                product.setId(c.getInt(c.getColumnIndex("id")));
                product.setLat(c.getInt(c.getColumnIndex("lat")));
                product.setLng(c.getInt(c.getColumnIndex("lng")));
                product.setDescription(c.getString(descColIndex));
                author.setName(c1.getString(c1.getColumnIndex("name")));
                author.setEmail(c1.getString(c1.getColumnIndex("email")));
                product.setAuthor(author);

                products.add(product);

            } while (c.moveToPrevious()&&c1.moveToPrevious());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();



        db.close();

        return  products;
    }

    /**
     * Created by fevzi on 30.09.14.
     */
    private static class SQLHelper extends SQLiteOpenHelper {

        final String LOG_TAG = "myLogs";

        public SQLHelper(Context context) {
            super(context, "MyDB", null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            db.execSQL("create table Product( id integer primary key, lng integer, " +
                    "lat integer, " +
                    "title text , " +
                    "description text, " +
                    "avatar text, " +
                    "author_ID integer  );");

            db.execSQL("create table Author( id integer primary key autoincrement," +
                     " name text," +
                     " email text," +
                     " token text );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        }
    }
}
