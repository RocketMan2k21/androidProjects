package com.example.contactappmanagerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.contactappmanagerapp.adapter.ContactAdapter;
import com.example.contactappmanagerapp.databinding.ActivityMainBinding;
import com.example.contactappmanagerapp.db.ContactDatabase;
import com.example.contactappmanagerapp.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // Binder & Click handler
    MainActivityClickHandlers clickHandler;
    ActivityMainBinding mainBinding;

    //Contacts, adapter, recycler view
    ArrayList<Contact> contactsList = new ArrayList<>();
    ContactAdapter contactAdapter;
    RecyclerView recyclerView;

    // Database
    ContactDatabase contactDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Sets click handler
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandler = new MainActivityClickHandlers(this);
        mainBinding.setClickHandler(clickHandler);



        // setting up recycler view

        recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        //adapter
        contactAdapter = new ContactAdapter(contactsList, this);

        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                AddNewContact(new Contact("Roman Duda", "romahaduda@gmail.com", 0));
                Log.i("TAG", "Database was created");

            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.i("TAG", "Database was opened");
            }
        };

        //Initialising contact list
        contactDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        ContactDatabase.class,
                        "contactsDB")
                .addCallback(callback)
                         .build();

        // load data



        DisplayAllContactsInBackGround();

        recyclerView.setAdapter(contactAdapter);

        //Handling swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contact = contactsList.get(viewHolder.getAdapterPosition());
                DeleteContact(contact);
            }
        }).attachToRecyclerView(recyclerView);


        //getting contact data from second activity and creates new contact

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Log.i("RES", "getting intent");
            String name = data.getStringExtra("NAME");
            String email = data.getStringExtra("EMAIL");
            Log.i("RES", "Data: " + name + email);
            Contact contact = new Contact(name, email, 0);

            AddNewContact(contact);

        }
    }

    private void AddNewContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //on background
                long id = contactDatabase.getContactDAO().addContact(contact);
                Contact newContact = contactDatabase.getContactDAO().getContact(id);

                //after getting all contacts
                if(newContact != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            contactsList.add(newContact);
                            contactAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    private void DeleteContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //on background
                contactDatabase.getContactDAO().deleteContact(contact);
                contactsList.remove(contact);
                //after getting all contacts

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void DisplayAllContactsInBackGround() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                contactsList.addAll(contactDatabase.getContactDAO().getAllContacts());

                //after getting all contacts
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactAdapter.setContacts(contactsList);
                        contactAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }


    public class MainActivityClickHandlers{
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onFABClicked(View View){
            Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
            startActivityForResult(intent, 1);
        }
    }

}