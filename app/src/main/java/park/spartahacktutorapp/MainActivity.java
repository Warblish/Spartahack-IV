package park.spartahacktutorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<WaterData> listings = new ArrayList<WaterData>();
    public ArrayList<WaterData> chosen = new ArrayList<WaterData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listings.add(new WaterData("Beef", 1,1,1,1));
        listings.add(new WaterData("Corn", 1,1,1,1));
        listings.add(new WaterData("Chicken", 1,1,1,1));
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

}
