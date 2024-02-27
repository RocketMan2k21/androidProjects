package com.example.contactappmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactappmanagerapp.databinding.ActivityAddNewContactBinding;
import com.example.contactappmanagerapp.db.entity.Contact;

import java.util.ArrayList;

public class AddNewContactActivity extends AppCompatActivity {

    Contact contact;

    AddNewContactActivityClickHandlers clickHandler1;
    ActivityAddNewContactBinding addNewContactBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contact();

        //main binder
        addNewContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_contact);
        addNewContactBinding.setContact(contact);

        //binding the click handler
        clickHandler1 = new AddNewContactActivityClickHandlers(this);
        addNewContactBinding.setClickHandler1(clickHandler1);
    }

    public class AddNewContactActivityClickHandlers{
        Context context;


        public AddNewContactActivityClickHandlers(Context context){
            this.context = context;
        }

        public void onSubmitClick(View view){
            if(TextUtils.isEmpty(contact.getName())){
                Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("NAME", contact.getName());
                i.putExtra("EMAIL", contact.getEmail());

                setResult(RESULT_OK, i);
                finish();
            }
        }


    }
}