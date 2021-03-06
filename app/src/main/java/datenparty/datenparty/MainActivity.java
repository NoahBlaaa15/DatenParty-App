package datenparty.datenparty;



import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        final ListView listView = (ListView) findViewById(R.id.artikelliste);
        ArrayList<Artikel> values = new ArrayList<Artikel>();
        final ArtikelAdapter adapter = new ArtikelAdapter(this, values);
        listView.setAdapter(adapter);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final Artikel listItem = (Artikel)listView.getItemAtPosition(position);

                //alert.setTitle("Do you want to logout?");
                alert.setTitle(listItem.ueberschrift);
                alert.setMessage(listItem.inhalt);

                alert.setPositiveButton("Schließen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.setNegativeButton("Weiter lesen ...",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listItem.link));
                                startActivity(browserIntent);
                            }
                        });

                alert.show();


            }
        });






        new Thread() {
                public void run() {
                    try {
                        datenholen(adapter);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

    }


        public void datenholen(final ArrayAdapter<Artikel> adapter ) throws IOException, ParseException, JSONException {
           // try {
                Document d = Jsoup.connect("http://maschini.de:5001/api").get();
                JSONParser parser = new JSONParser();
                final org.json.simple.JSONArray array = (org.json.simple.JSONArray) parser.parse(d.getElementsByTag("body").text());


            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    for(int i=0; i<array.size(); i++) {
                        JSONObject artikel = (JSONObject) array.get(i);
                        Artikel a = new Artikel();
                        a.ueberschrift = artikel.get("heading").toString();
                        a.inhalt = artikel.get("article").toString();
                        a.link = artikel.get("link").toString();
                        a.quelle = artikel.get("author").toString();
                        a.category = artikel.get("category").toString();
                        adapter.add(a);
                    }
                        adapter.notifyDataSetChanged();


                }
            });




          //  } catch (IOException | ParseException e) {
           // }



        }

    }

