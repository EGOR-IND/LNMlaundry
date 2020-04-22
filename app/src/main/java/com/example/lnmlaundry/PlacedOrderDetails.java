package com.example.lnmlaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.lnmlaundry.PendingOrderAdapter.statusVal;

public class PlacedOrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order_details);

        TextView orderNo = (TextView)findViewById(R.id.podorderNo);
        TextView status = (TextView)findViewById(R.id.podstatus);
        TextView totalAmount = (TextView)findViewById(R.id.podtotalAmount);
        TextView totalClothes = (TextView)findViewById(R.id.podtotalClothes);
        TextView orderPlacingTime = (TextView)findViewById(R.id.podOrderPlacedTime);
        TextView pickUpOtp = (TextView)findViewById(R.id.podpickUpOtp);

        orderNo.append(PendingOrderAdapter.orderNoValue);
        status.append(statusVal);
        totalAmount.append(PendingOrderAdapter.totalAmountValue);
        totalClothes.append(PendingOrderAdapter.totalClothesValue);
        orderPlacingTime.append(PendingOrderAdapter.timeValue);
        if (statusVal == "Placed"){
            pickUpOtp.append(PendingOrderAdapter.pickUpOtpValue);
        }else if (statusVal == "Picked Up"){
            status.setBackgroundResource(R.color.pendingIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusVal == "Ready"){
            status.setBackgroundResource(R.color.pastIconEnabled);
            pickUpOtp.setVisibility(View.GONE);
        }else if (statusVal == "Delivered"){
            status.setBackgroundResource(R.color.DeliveredStatus);
            pickUpOtp.setVisibility(View.GONE);
        }
    }
}
