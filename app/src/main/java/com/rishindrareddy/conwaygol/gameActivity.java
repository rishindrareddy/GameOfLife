package com.rishindrareddy.conwaygol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class gameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final gridActivity grid = (gridActivity)findViewById(R.id.gameGrid);
        Button next = (Button) findViewById(R.id.nextButton);
        Button reset = (Button)findViewById(R.id.resetButton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.nextGen();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.resetGame();
            }
        });


    }
}
