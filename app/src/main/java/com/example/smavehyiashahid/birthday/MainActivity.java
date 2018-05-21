package com.example.smavehyiashahid.birthday;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=1;
    boolean hasWhippedCream;
    boolean hasChocolate;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        display(quantity);
        orderToppingChoc(view);
        name = getName();
        int price = checkPrice();
        String summary = createOrderSummary(price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.coffeeOrder,name));
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment (View view) {
        quantity++;
        if (quantity>=100){
            Toast myToast = Toast.makeText(this, getString(R.string.toastmsg),Toast.LENGTH_LONG);
            myToast.show();
            quantity=100;
        }
        display(quantity);

    }

    public void decrement(View view) {
       quantity--;
       if (quantity <= 1){
           quantity=1;
       }
        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */


    private int checkPrice(){
        int coffeePrice = 2;
        int price = quantity*coffeePrice;
        if(hasWhippedCream){
            price+=(1*quantity);
        }
        if(hasChocolate){
                price+=(2*quantity);
        }
        return price;
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private String createOrderSummary(int price){
        String summary = getString(R.string.NAME,name);
        summary+= "\n" + getString(R.string.QUANTITY)+" " +quantity;
        summary+= "\n" +getString(R.string.WHIPPEDCREAM)+" " +hasWhippedCream;
        summary+= "\n"+getString(R.string.CHOC)+" " +hasChocolate;
        summary+= "\n"+getString(R.string.total)+" " +NumberFormat.getCurrencyInstance().format(price);
        summary+= "\n"+ getString(R.string.thankyou);
        return summary;
    }

    public void orderTopping(View view) {
        CheckBox orderTopping = (CheckBox) findViewById(R.id.order_topping);
        hasWhippedCream= orderTopping.isChecked();
    }


    public void orderToppingChoc(View view){
        CheckBox orderTopping = (CheckBox) findViewById(R.id.order_topping_choc);
        hasChocolate = orderTopping.isChecked();
    }
    public String getName(){
        EditText nameView=(EditText) findViewById(R.id.name);
        return nameView.getText().toString();
    }
}
