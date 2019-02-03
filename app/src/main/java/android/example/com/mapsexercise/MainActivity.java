package android.example.com.mapsexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText long_edittext;
    private EditText lat_edittext;
    private Button mapButton;
    public static final String LAT_KEY = "latitude";
    public static final String LONG_KEY = "longitude";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat_edittext = findViewById(R.id.lat_edittext);
        long_edittext = findViewById(R.id.long_edittext);
        findViewById(R.id.map_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lat_edittext != null && long_edittext != null) {
                    String latitude = lat_edittext.getText().toString().trim();
                    String longitude = long_edittext.getText().toString().trim();
                    Bundle extras = new Bundle();
                    extras.putString(LONG_KEY, longitude);;
                    extras.putString(LAT_KEY, latitude);
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });



    }
}
