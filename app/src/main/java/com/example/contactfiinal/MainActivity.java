package com.example.contactfiinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;
import com.example.contactfiinal.Adapters.MyAdapter;
import com.example.contactfiinal.Model.MyContacts;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadContacts();
        
    }

    private void loadContacts() {
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.NUMBER);

        ArrayList<MyContacts> arrayList = new ArrayList<>();
        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (number.length() > 0)
                {
                    Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract
                    .CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id},null);

                    if (phoneCursor.getCount() > 0){
                        while (phoneCursor.moveToNext())
                        {
                            String phoneNumberValue = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            MyContacts myContacts = new MyContacts(name,phoneNumberValue);

                            arrayList.add(myContacts);

                        }
                    }
                    phoneCursor.close();
                }
            }

            MyAdapter myAdapter = new MyAdapter(this,arrayList);
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }
        else 
        {
            Toast.makeText(getApplicationContext(), "No Contacts Found", Toast.LENGTH_SHORT).show();
        }
    }


}