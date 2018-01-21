package park.spartahacktutorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import static park.spartahacktutorapp.MainActivity.listings;

public class Listings extends AppCompatActivity {

    public int index = -1;
    public String searchText = "";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_listings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        SearchView searchBar = (SearchView) findViewById(R.id.searcher);
        searchBar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((SearchView) v).setIconified(false);
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchText = newText;
                updateListings();
                return true;
            }
        });
        updateListings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateListings() {
        LinearLayout layout = (LinearLayout) this.findViewById(R.id.listing_layout);
        layout.removeAllViews();
        /*
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/text_margin"
        android:textSize="20dp"
        android:gravity="center"*/
        DateFormat formatting = new DateFormat();

        for(int i = 0; i < listings.size(); i++) {
            WaterData list = listings.get(i);
            Button view = new Button(this);
            view.setTag(list);
            String textBlock = list.food;
            if(!textBlock.toLowerCase().contains(searchText.toLowerCase())) continue;
            view.setText(textBlock);
            view.setClickable(true);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    labelClick(v);
                }
            });
            layout.addView(view);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == Activity.RESULT_OK) {
            int qty = data.getIntExtra("qty", 1);
            listings.get(index).quantity = qty;
            finishDialog();
        } else {
            index = -1;
        }
    }
    public void labelClick(View clicked) {
        index = listings.indexOf((WaterData)clicked.getTag());
        InfoActivity.data = (WaterData)clicked.getTag();
        startActivityForResult(new Intent(Listings.this, InfoActivity.class), 2);
    }

    public void finishDialog() {
        if(index!=-1) {
            setResult(Activity.RESULT_OK);
        } else {
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }
}
