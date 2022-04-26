package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private TextView textViewAdd;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxLimon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String name;
    private String password;
    private String drink;
    private StringBuilder builderAdditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = getIntent();
        if (intent.hasExtra("name")&&intent.hasExtra("password")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        textViewWelcome = findViewById(R.id.textViewWelcome);
        String hello = String.format(getString(R.string.welcome_client),name);
        textViewWelcome.setText(hello);
        textViewAdd = findViewById(R.id.textViewAdd);
        drink = getString(R.string.radio_tea);
        String additions = String.format(getString(R.string.addition_text),drink);
        textViewAdd.setText(additions);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxLimon = findViewById(R.id.checkboxLimon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        builderAdditions = new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton radioButton = (RadioButton) view;
        int id = radioButton.getId();
        if (id == R.id.radioButtonTea){
            drink = getString(R.string.radio_tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLimon.setVisibility(View.VISIBLE);
        }else if (id == R.id.radioButtonCoffee){
            drink = getString(R.string.radio_coffee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLimon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.addition_text),drink);
        textViewAdd.setText(additions);
    }

    public void onClickSendOrder(View view) {
        builderAdditions.setLength(0);
        if (checkBoxSugar.isChecked()){
            builderAdditions.append(getString(R.string.checkbox_sugar)).append(" ");
        }
        if (checkBoxMilk.isChecked()){
            builderAdditions.append(getString(R.string.checkbox_milk)).append(" ");
        }
        if (checkBoxLimon.isChecked()&&drink.equals(getString(R.string.radio_tea))){
            builderAdditions.append(getString(R.string.checkbox_limon)).append(" ");
        }
        String optionOfDrink = "";
        if (drink.equals(getString(R.string.radio_tea))){
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        }else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order),name,password,drink,optionOfDrink);
        String additions;
        if (builderAdditions.length()>0){
            additions = getString(R.string.need_additions)+builderAdditions.toString();
        }else {
            additions = "";
        }
        String fullOrder = order + additions;

        Intent intent = new Intent(this,OrderDetailActivity.class);
        intent.putExtra("order",fullOrder);
        startActivity(intent);
    }
}