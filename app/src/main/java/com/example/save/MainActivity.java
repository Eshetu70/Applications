package com.example.save;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView weightID, gentertestID, weightViewID, drinkzieID, textViewaalcoholID, textViewVlueSeekPercen,
            drinksVlaueID, BAClevelID, statusID, statusValuesID;
    View viewStatusID;
    EditText enteredweight;
    RadioGroup radioGroupGenderID, radioGroupdrinksID;
    RadioButton radioButtonfemale, radioButtonmale, radioButton1oz, radioButton5oz, radioButton12oz;
    Button setweightID, resetID, addDrinkID;
    SeekBar seekBarID;
    Profile profile;
    ArrayList<Drinks> drinks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weightID = findViewById(R.id.weightID);
        gentertestID = findViewById(R.id.gentertestID);
        weightViewID = findViewById(R.id.weightViewID);
        drinkzieID = findViewById(R.id.drinkzieID);
        setweightID = findViewById(R.id.setweightID);
        radioGroupGenderID = findViewById(R.id.radioGroupGenderID);
        enteredweight = findViewById(R.id.enteredweight);
        textViewVlueSeekPercen = findViewById(R.id.textViewVlueSeekPercen);
        seekBarID = findViewById(R.id.seekBarID);
        addDrinkID = findViewById(R.id.addDrinkID);
        radioGroupdrinksID = findViewById(R.id.radioGroupdrinksID);
        drinksVlaueID = findViewById(R.id.drinksVlaueID);
        resetID = findViewById(R.id.resetID);
        BAClevelID =findViewById(R.id.BAClevelID);
        viewStatusID =findViewById(R.id.viewStatusID);
        statusValuesID =findViewById(R.id.statusValuesID);

        setweightID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterweight = enteredweight.getText().toString();

                try {
                    double weight = Double.valueOf(enterweight);
                    if (weight > 0) {
                        String gender = "Female";
                        if (radioGroupGenderID.getCheckedRadioButtonId() == R.id.radioButtonmale) {
                            gender = "Male";
                        }
                        profile = new Profile(gender, weight);
                        weightViewID.setText(String.valueOf("Weight: " + weight) + " lbs(" + gender + ")");
                        enteredweight.setText("");
                    }else {
                        Toast.makeText(MainActivity.this, "Enter valid Weight", Toast.LENGTH_SHORT).show();
                    }
                    drinks.clear();
                    calculateBAC();

                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Enter Valid Weight in lbs", Toast.LENGTH_SHORT).show();

                }

            }
        });
        seekBarID.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewVlueSeekPercen.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        addDrinkID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile == null) {
                    Toast.makeText(MainActivity.this, "Setup Weight and Gender First", Toast.LENGTH_SHORT).show();
                } else {
                    double size = 1;
                    if (radioGroupdrinksID.getCheckedRadioButtonId() == R.id.radioButton5oz) {
                        size = 5;
                    } else if (radioGroupdrinksID.getCheckedRadioButtonId() == R.id.radioButton12oz) {
                        size = 12;

                    }
                    double percentage = seekBarID.getProgress();
                    if (percentage > 0) {
                        Drinks drink = new Drinks(size, percentage);
                        drinks.add(drink);
                        calculateBAC();
                    } else {
                        Toast.makeText(MainActivity.this, "Set up for alcohol", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        resetID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile = null;
                drinks.clear();
                weightViewID.setText("");
                radioGroupGenderID.check(R.id.radioButtonfemale);
                enteredweight.setText("");
                radioGroupdrinksID.check(R.id.radioButton1oz);
                seekBarID.setProgress(12);
                calculateBAC();

            }
        });
    }
        void calculateBAC(){
            drinksVlaueID.setText("# Drinks: "+String.valueOf(drinks.size()));

       if(drinks.size()==0){
           BAClevelID.setText("BAC Level: 0.000");
           viewStatusID.setBackgroundColor(getColor(R.color.safe_color));
           statusValuesID.setText("You're Safe!");

       }else {
           double valueA =0.0;
           for (Drinks drink:drinks
                ) {
               valueA = valueA + drink.getDrink()*drink.getPercentage()/100.0;

           }
           double valueB =0.73;
           if(profile.getGender()=="Female"){
               valueB =0.66;
           }
           double bac =valueA * 5.14/(profile.getWeight()*valueB);
           BAClevelID.setText("BAC Level:" +String.format("%.3f", bac));
           if(bac<=0.08){
               statusValuesID.setText("You're Safe!");
               viewStatusID.setBackgroundColor(getColor(R.color.safe_color));
           } else if (bac<=0.2) {
               statusValuesID.setText("Be Careful!");
               viewStatusID.setBackgroundColor(getColor(R.color.careful_color));

           }else{
               statusValuesID.setText("Over the limit!");
               viewStatusID.setBackgroundColor(getColor(R.color.over_color));
           }
       }

    }
}