package cmps121.focus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation translate = AnimationUtils.loadAnimation(this,R.anim.translate);
        final Animation translate2 = AnimationUtils.loadAnimation(this,R.anim.translate2);

        Button myButton =  findViewById(R.id.button);
        myButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                view.startAnimation(translate);
                view.startAnimation(translate2);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
        }
        });
    }
}
