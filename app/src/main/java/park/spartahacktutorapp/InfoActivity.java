package park.spartahacktutorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NumberPicker picker = (NumberPicker) findViewById(R.id.picker);
        picker.setMaxValue(1000);
        picker.setMinValue(1);

        Button fab = (Button) findViewById(R.id.info_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker npicker = (NumberPicker) findViewById(R.id.picker);
                Intent result = new Intent();
                result.putExtra("qty", npicker.getValue());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }

}
