package datenparty.datenparty;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
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
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.artikelliste);
        ArrayList<String> values = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);



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


        public void datenholen(final ArrayAdapter<String> adapter ) throws IOException, ParseException, JSONException {
           // try {
                Document d = Jsoup.connect("http://maschini.de:5001/alt").get();
                JSONParser parser = new JSONParser();
                final org.json.simple.JSONArray array = (org.json.simple.JSONArray) parser.parse(d.getElementsByTag("body").text());


            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    for(int i=0; i<array.size(); i++) {
                        org.json.simple.JSONObject artikel = (org.json.simple.JSONObject) array.get(i);
                        adapter.add(artikel.get("heading").toString());
                    }
                        adapter.notifyDataSetChanged();


                }
            });




          //  } catch (IOException | ParseException e) {
           // }



        }

    }

