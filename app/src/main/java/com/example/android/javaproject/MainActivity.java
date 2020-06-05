package com.example.android.javaproject;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;
    int totalprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this,"you cant get more than this.",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this,"you cant get less than this.",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox=(CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();

        EditText nameField=(EditText)findViewById(R.id.name_field);
        String value=nameField.getText().toString();

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage= createOrderSummary(value,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"COFFEE ORDER FOR "+value);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int baseprice=5;
        if (addWhippedCream){
            baseprice=baseprice+1;
        }
        if (addChocolate){
            baseprice=baseprice+2;
        }
        totalprice=baseprice*quantity;
        return totalprice;


    }
    private String createOrderSummary(String value,int price,boolean addWhippedCream,boolean addChocolate){
        String priceMessage="Name: "+value;
        priceMessage+="\nAdd Whipped Cream: "+addWhippedCream;
        priceMessage+="\nAdd chocolate: "+addChocolate;
        priceMessage+="\nquantity: "+quantity;
        priceMessage+="\nTotal: $"+totalprice;
        priceMessage+="\nThank You";
        return priceMessage;

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
