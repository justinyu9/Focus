package cmps121.focus;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Tasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);


        StringBuilder text = new StringBuilder();
//        ArrayList<String> taskList = (ArrayList<String>) getIntent().getSerializableExtra("list");
//        String id = (String) getIntent().getSerializableExtra("taskName");
//        SharedPreferences reader = getSharedPreferences(id, MODE_PRIVATE);
//        taskList.add(id);
//        String[] tasks = new String[taskList.size()];
//        tasks = taskList.toArray(tasks);

        try {
            // open the file for reading we have to surround it with a try

            InputStream inStream = openFileInput("Test3.txt");//open the text file for reading

            // if file the available for reading
            if (inStream != null) {

                // prepare the file for reading
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);

                String line = "";
                //We initialize a string "line"

                while (( line = buffReader.readLine()) != null) {
                    //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
                    //listArray.add(line);
                    text.append(line);
                    //to display the text in text line
                    text.append(" ");

                }
            }}

        //now we have to surround it with a catch statement for exceptions
        catch (IOException e) {
            e.printStackTrace();
        }

        String last = text.toString();
        String[] tasks = last.split(" ");

        for(int i=0; i<tasks.length; i++){
            tasks[i] = tasks[i].replace('|','\n');
        }

        //listArray = Arrays.asList(tasks);
        /*result=text.getString();
        listArray.add(line);*/
        //listArray.add(getInput);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tasks);
        final ListView show = (ListView) findViewById(R.id.listview1);

        show.setAdapter(adapter);
        Button myButton = (Button) findViewById(R.id.Add);
        Button myButton2 = (Button) findViewById(R.id.Start);

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences read = getSharedPreferences(show.getItemAtPosition(i).toString(), MODE_PRIVATE);
                SharedPreferences.Editor taskAtHand = getSharedPreferences("taskAtHand", MODE_PRIVATE).edit();
                taskAtHand.putString("taskAtHand", show.getItemAtPosition(i).toString());
                String text = read.getString("time", "No name defined");
                taskAtHand.putString("time", text);
                taskAtHand.apply();
                if(isMyServiceRunning(MyService.class)){
                    stopService(new Intent(Tasks.this, MyService.class));
                    startService(new Intent(Tasks.this, MyService.class));
                }
                else {
                    startService(new Intent(Tasks.this, MyService.class));
                }
                Intent intent = new Intent(Tasks.this, Start.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        myButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Tasks.this, input_info.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        myButton2.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(Tasks.this, SecondActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void startService(View view){
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }
    public void stopService(View view){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
