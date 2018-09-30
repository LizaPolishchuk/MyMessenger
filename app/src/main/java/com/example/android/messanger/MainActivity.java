package com.example.android.messanger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int MAX_MSG_LENGTH = 150;
    DatabaseReference mRef;
    EditText editText;
    String message;
    RecyclerView recyclerView;
    DataAdapter dataAdapter;
    final ArrayList<String> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edit_text);

        dataAdapter = new DataAdapter(this, messages);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataAdapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference("messages");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // String value = dataSnapshot.getValue(String.class);
              //  Log.d("myLogs", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("myLogs", "onCancelled: Failed to read value", databaseError.toException());
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg = dataSnapshot.getValue(String.class);
                messages.add(msg);
                dataAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messages.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onClick(View view){
        message = editText.getText().toString();
        if(message.equals("")){
            Toast.makeText(this, "Введите сообщение!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(message.length()> MAX_MSG_LENGTH){
            Toast.makeText(this, "Слишком длинное сообщение!", Toast.LENGTH_SHORT).show();
            return;
        }
        mRef.push().setValue(message);
        editText.setText("");
    }

}

