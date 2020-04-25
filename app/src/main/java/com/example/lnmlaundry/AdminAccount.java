package com.example.lnmlaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin_account);

        ImageView imageView = findViewById(R.id.adminDialogProfilePic);
        Picasso.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).placeholder(R.drawable.ic_account_circle_black_24dp).fit().into(imageView);

        TextView adminName = findViewById(R.id.adminName);
        TextView adminEmail = findViewById(R.id.adminEmail);

        adminName.append(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        adminEmail.append(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        findViewById(R.id.sign_outBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
                startActivity(new Intent(AdminAccount.this, signin_page.class));
            }
        });

    }
}
