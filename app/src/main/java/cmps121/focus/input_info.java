package cmps121.focus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStreamWriter;

public class input_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button done;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        //get the spinner from the xml.
        Spinner dropdown = (Spinner)findViewById(R.id.startTime);
        Spinner dropdown1 = (Spinner)findViewById(R.id.DueSpinner);
        Spinner dropdown2 = (Spinner)findViewById(R.id.reminderSpin);
        Spinner dropdown3 = (Spinner)findViewById(R.id.typeSpin);
        Spinner dropdown4 = (Spinner)findViewById(R.id.DifficultSpinner);
        Spinner dropdown5 = (Spinner)findViewById(R.id.ImportanceSpinner);

        //create a list of items for the spinner.
        String[] items3 = new String[]{"Pick Type","Class","Work","Errands","Custom"};
        String[] items = new String[]{"Takes","less than 30 Minutes","an hour","1.5 hours","2 hours","2.5 hours","3 hours","3.5 hours","4 hours","4.5 hours","5 hours","6 hours","7 hours","8 hours","9 hours","10 hours"};
        String[] items1 = new String[]{"Due in","1 Day","2-3 Days","a week","2-3 weeks","a month","2 months","a year",};
        String[] items2 = new String[]{"Pick Reminder","10 min before","30 min before","1 hr before","2 hrs before","Custom"};
        String[] items4 = new String[]{"1 = easy, 10 = hard ","1","2","3","4","5", "6", "7", "8", "9", "10"};
        String[] items5 = new String[]{"1 = not very, 10 = very","1","2","3","4","5", "6", "7", "8", "9", "10"};


        //create an adapter to describe how the items are displayed, adapters are used in several places in android
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items5);


        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown1.setAdapter(adapter1);
        dropdown2.setAdapter(adapter2);
        dropdown3.setAdapter(adapter3);
        dropdown4.setAdapter(adapter4);
        dropdown5.setAdapter(adapter5);

        Button cancel = (Button) findViewById(R.id.Cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(input_info.this, Tasks.class);
                //intent.putExtra("Text 2", edit_text_2.getText().toString());
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        done=(Button)findViewById(R.id.doneButton);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.taskName);
                Spinner startBox = (Spinner) findViewById(R.id.startTime);
                Spinner endBox = (Spinner) findViewById(R.id.DueSpinner);
                Spinner reminderBox = (Spinner) findViewById(R.id.reminderSpin);
                Spinner typeBox = (Spinner) findViewById(R.id.typeSpin);
                Spinner difficultyBox = (Spinner) findViewById(R.id.DifficultSpinner);
                Spinner importanceBox = (Spinner) findViewById(R.id.ImportanceSpinner);

                String taskName = name.getText().toString();
                String startTime = startBox.getSelectedItem().toString();
                String dueTime = endBox.getSelectedItem().toString();
                String remindTime = reminderBox.getSelectedItem().toString();
                String type = typeBox.getSelectedItem().toString();
                String difficulty = difficultyBox.getSelectedItem().toString();
                String importance = importanceBox.getSelectedItem().toString();

                if (taskName.equals("") ||type.equals("Pick Type") || startTime.equals("Takes") || dueTime.equals("Due in") || remindTime.equals("Pick Reminder") || difficulty.equals("1 = easy, 10 = hard ") || importance.equals("1 = not very, 10 = very")) {
                    Toast.makeText(input_info.this, "Please fill out all information", Toast.LENGTH_LONG).show();
                }
                else {
                    try {

                        // open myfilename.txt for writing


                        OutputStreamWriter out = new OutputStreamWriter(openFileOutput("Test1.txt", MODE_APPEND));

                        // write the contents to the file
                        String temp = taskName;

                                out.write(temp);
                                out.write(" ");

                        //System.out.print(cells);


                        // close the file

                        out.close();


                    } catch (java.io.IOException e) {

                        //do something if an IOException occurs.
                        Toast.makeText(input_info.this, "Error!", Toast.LENGTH_LONG).show();


                    }


                    SharedPreferences.Editor editor = getSharedPreferences("TaskName", MODE_APPEND).edit();
                    editor.putString(taskName, taskName);
                    editor.apply();
                    editor = getSharedPreferences("Time", MODE_APPEND).edit();
                    editor.putString(taskName, startTime);
                    editor.apply();
                    editor = getSharedPreferences("DueDate", MODE_APPEND).edit();
                    editor.putString(taskName, dueTime);
                    editor.apply();
                    editor = getSharedPreferences("Type", MODE_APPEND).edit();
                    editor.putString(taskName, type);
                    editor.apply();
                    editor = getSharedPreferences("Difficulty", MODE_APPEND).edit();
                    editor.putString(taskName, difficulty);
                    editor.apply();
                    editor = getSharedPreferences("Importance", MODE_APPEND).edit();
                    editor.putString(taskName, importance);
                    editor.apply();
                    editor = getSharedPreferences("Reminder", MODE_APPEND).edit();
                    editor.putString(taskName, remindTime);
                    editor.apply();
                    editor = getSharedPreferences("Deleted", MODE_APPEND).edit();
                    editor.putString(taskName, "False");
                    editor.apply();

                    SharedPreferences read = getSharedPreferences("Time", MODE_APPEND);
                    String s = read.getString(taskName, "No name defined");
                    Toast.makeText(input_info.this, s, Toast.LENGTH_LONG).show();
                    // finish(); //return to home
                    Intent intent = new Intent(input_info.this, Tasks.class);
                    //intent.putExtra("Text 2", edit_text_2.getText().toString());
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}