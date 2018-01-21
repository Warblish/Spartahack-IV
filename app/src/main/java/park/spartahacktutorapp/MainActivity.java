package park.spartahacktutorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    int index = -1;
    public static ArrayList<WaterData> listings = new ArrayList<WaterData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadJson();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        Button fab = (Button) findViewById(R.id.fab);
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
        if(requestCode == 3 && resultCode == Activity.RESULT_OK) {
            int qty = data.getIntExtra("qty", 1);
            listings.get(index).quantity = qty;
            updateGrid();
        }
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            updateGrid();
        }
        index = -1;
    }
    public void updateGrid() {
        TableLayout table = (TableLayout) this.findViewById(R.id.main_table);
        table.removeAllViews();

        TableRow row = new TableRow(this);
        TextView title1 = new TextView(this);
        TextView title2 = new TextView(this);
        TextView title3 = new TextView(this);

        title1.setText("Food");
        title2.setText("Quantity");
        title3.setText("Phi");

        title1.setGravity(Gravity.CENTER);
        title2.setGravity(Gravity.CENTER);
        title3.setGravity(Gravity.CENTER);

        row.addView(title1);
        row.addView(title2);
        row.addView(title3);

        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        table.addView(row);

        for(int i = 0; i < listings.size(); i++) {
            WaterData list = listings.get(i);
            if(list.quantity==0) continue;
            TableRow view = new TableRow(this);
            TextView text1 = new TextView(this);
            TextView text2 = new TextView(this);
            TextView text3 = new TextView(this);

            text1.setTag(list);
            text2.setTag(list);
            text3.setTag(list);

            View.OnClickListener listener = (new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    labelClick(v);
                }
            });

            text1.setOnClickListener(listener);
            text2.setOnClickListener(listener);
            text3.setOnClickListener(listener);

            text1.setText(list.food);
            text2.setText(""+list.quantity);
            text3.setText(""+list.green);

            text1.setGravity(Gravity.CENTER);
            text2.setGravity(Gravity.CENTER);
            text3.setGravity(Gravity.CENTER);

            view.addView(text1);
            view.addView(text2);
            view.addView(text3);

            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            table.addView(view);
        }
    }

    public void loadJson() {
        try {
            JSONObject dataArray = new JSONObject(extractJson("USimpact.json"));
            JSONObject usData = (JSONObject) dataArray.get("USA");
            Iterator<String> keys = usData.keys();
            while(keys.hasNext()) {
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
    public void labelClick(View clicked) {
        index = listings.indexOf((WaterData)clicked.getTag());
        InfoActivity.data = (WaterData)clicked.getTag();
        startActivityForResult(new Intent(MainActivity.this, InfoActivity.class), 3);
    }

}
