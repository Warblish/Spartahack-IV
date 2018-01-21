package park.spartahacktutorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static android.R.attr.key;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<WaterData> listings = new ArrayList<WaterData>();
    public ArrayList<WaterData> chosen = new ArrayList<WaterData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadJson();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, Listings.class), 1);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            int index = data.getIntExtra("food", 0);
            chosen.add(listings.get(index));
            updateGrid();
        }
    }
    public void updateGrid() {
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.main_layout);
        layout.removeAllViews();
        for(int i = 0; i < chosen.size(); i++) {
            WaterData list = chosen.get(i);
            TextView view = new TextView(this);
            view.setText(list.food + ": " + list.quantity);
            view.setTag(list);
            view.setClickable(true);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(view);
        }
    }

    public void loadJson() {
        try {
            JSONObject dataArray = new JSONObject(extractJson("USimpact.json"));
            JSONObject usData = (JSONObject) dataArray.get("USA");
            Iterator<String> keys = usData.keys();
            while(keys.hasNext()) {
                Log.wtf("test_main", "iterating");
                String key = (String)keys.next();
                try {
                    int i = Integer.valueOf(key);
                } catch (Exception e) {
                    continue;
                }

                JSONObject foodObj = (JSONObject) usData.get(key);
                listings.add(new WaterData(foodObj.getString("Name"),
                        foodObj.getInt("Blue"),
                        foodObj.getInt("Green"),
                        foodObj.getInt("Grey"),
                        Integer.valueOf(key)));
            }
        } catch (Exception e) {
            Log.wtf("test_main", "error in json");
            Log.wtf("test_main", e.getMessage());
        }
    }

    public String extractJson(String filename) {
        try {
            InputStream str = getAssets().open(filename);
            int av = str.available();
            byte[] bytes = new byte[av];
            str.read(bytes);
            str.close();
            return new String(bytes);
        } catch (Exception e) {

        }
        return "";
    }

}
