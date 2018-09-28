package com.example.android.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    //boolean checkBox_checked = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


     /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if(quantity ==100) {
            //show error message to the screen
            Toast.makeText(MainActivity.this, "You cannot order above 100 coffee", Toast.LENGTH_SHORT).show();
            //exit the method because there is nothing to do in this method again
            return;
        }
            quantity = quantity + 1;
            display(quantity);

    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void decrement(View view) {

        if (quantity <2) {
            //show error message
            Toast.makeText(MainActivity.this, "You cannot order below 1 coffee", Toast.LENGTH_SHORT).show();
            //exit the method because there is nothing to do in this method again
            return;
        }

            quantity = quantity - 1;
            display(quantity);

    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // Figure out if the user wants whipped cream topping
           CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_checkbox);
          boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
          //log.v("MainActivity","whipped checked"+check);
          // Figure out if the user wants chocolate topping
          CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
          boolean hasChocolate = chocolateCheckBox.isChecked();
          EditText userInput = (EditText)findViewById(R.id.user_name);
          String textInput = userInput.getText().toString();

          Toast toast = Toast.makeText(MainActivity.this,textInput, Toast.LENGTH_SHORT);
          toast.setGravity(Gravity.CENTER, 0, 0);
          toast.show();

          // Calculate the price
          int price = calculatePrice(hasWhippedCream,hasChocolate);

        // Display the order summary on the screen
            String message = createOrderSummary(price, hasWhippedCream, hasChocolate, textInput);
            displayMessage(message);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        //emailIntent.setData(Uri.parse("mailto")); //only email apps should handle this.
        emailIntent.setType("*/*");
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order" + textInput);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(Intent.EXTRA_BCC,"yemi.bili07@cil-track.com");
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        // You can also attach multiple items by passing an ArrayList of Uris

        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("geo:6.3422,3.273"));
        if(emailIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(emailIntent);
        }


    }

    /**
     * Calculates the price of the order.
     *
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int basePrice = 5;
        if(hasWhippedCream)
        {
            //add $1 to baseprice
            basePrice = basePrice + 1;
        }
        if(hasChocolate){
            basePrice = basePrice + 2;
        }
       return quantity * basePrice;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @param pricePerCup is the price of a cup
     */

    private int calculatePrice(int quantity, int pricePerCup){
        return quantity *pricePerCup;
    }

    /**
    * create a new summary of order
     * @param priceOfOrder is the price per order
     * @param whippedcreamcheck is to check whether checkbox is checked
     * @param userName is to check whether checkbox is checked
     * return text summary
     */
    private String createOrderSummary(int priceOfOrder, boolean whippedcreamcheck, boolean hasChocolate, String userName){

        String orderSummary = "Your Name is:" +userName;
        orderSummary += "\nHas whipped cream checked:"+whippedcreamcheck;
        orderSummary +="\nAdd Chocolate?"+ hasChocolate;
        orderSummary += "\nQuantity:"+quantity+"\n";
        orderSummary +="Total:$" + priceOfOrder;
        orderSummary +="\n"+"Thank you !";
        return orderSummary;


    }


    /**
     * This method set visibility state of View object to zero, it removes the  view completely from screen
     */
    private void displayQuantity(String number) {
        View textView = (View) findViewById(R.id.order_summary_text_view);
        textView.setVisibility(View.GONE);

        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("" + number);
        //EditText datePicker = (EditText) findViewById(R.id.date_picker);
        //datePicker.setText(number);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int value) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + value);
    }

    /* This methods displays the message inside the textview order_summary_text_view

     */
    private void displayMessage(String message){
        TextView orderSummaryTextView = (TextView)findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}