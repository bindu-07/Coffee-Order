package com.binduhait.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    public void increment(View view) {
        if (quantity<100) {
            quantity = quantity + 1;
        }
        else {
            Toast.makeText(this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity>1) {
            quantity = quantity - 1;
        }
        else {
            Toast.makeText(this, "You can not have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
         String name = nameField.getText().toString();
         //Log.v("MainActivity","Name" +name);

        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasCheckBox =WhippedCreamCheckBox.isChecked();
        //Log.v("MainActivity","Has Whipped Cream: " + hasCheckBox);

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate =ChocolateCheckBox.isChecked();
       // Log.v("MainActivity","Has Chocolate: " + hasChocolate);

        int price = calculatePrice(hasCheckBox,hasChocolate);
        String priceMessage = createOrderSummary(price,hasCheckBox,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        quantityTextView.setText("" + number);
    }
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate){
        int basePrice = 5;
        if (addWhippedCream){
            basePrice +=1;
        }
        if (addChocolate){
            basePrice += 2;
        }
        return quantity * basePrice;

    }

    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String AddName){
        String priceMessage = "Name: " +AddName;
        priceMessage += "\nAdd whipped Cream ? " + addWhippedCream;
        priceMessage += "\nAdd chocolate ?" + addChocolate ;
        priceMessage +=  "\nQuantity: "+quantity;
        priceMessage +=  "\nTotal: ₹" + price +"\nThank You!";
        return priceMessage;
    }





}

