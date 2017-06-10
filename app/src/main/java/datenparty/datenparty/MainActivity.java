package datenparty.datenparty;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            new Thread() {
                public void run() {
                    try {
                        Main m = new Main();
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

    public class Main {
        public Main() throws IOException, ParseException, JSONException {
           // try {
                Document d = Jsoup.connect("http://maschini.de:5001").get();
                JSONParser parser = new JSONParser();
                org.json.simple.JSONArray array = (org.json.simple.JSONArray) parser.parse(d.getElementsByTag("body").text());
                Log.d("DatenParty", array.get(0).toString());
          //  } catch (IOException | ParseException e) {
           // }
        }
    }
}
