package com.han.cleanhome;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

//파이어베이스
//import com.google.firebase.quickstart.auth.R;
//import com.google.firebase.quickstart.auth.databinding.ActivityFirebaseUiBinding;



public class fragment_tab1 extends Fragment {



    private Button btn_send;
    private EditText et_msg;
    private ListView lv_chating;

    private ArrayAdapter<String> arrayAdapter;

    private String str_name;
    private String str_msg;
    private String chat_user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("message");
    Context mContext;

    public fragment_tab1(Context mContext) {
        this.mContext =  mContext;
    }


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

//    myRef.setValue("Hello, World!");

//    private ListView list;
//    private EditText edit;
//    private Button button2;
//    private Adapter adapter;
//
//    private String userName;
//    private String message;
//
//    public void ChatData() {}
//
//    public void ChatData(String userName, String message) {
//        this.userName = userName;
//        this.message = message;
//
//    }
//
//
//    public String getUserName() {
//
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//
//        this.userName = userName;
//    }
//
//    public String getMessage() {
//
//        return message;
//    }
//
//    public void setMessage(String message) {
//
//        this.message = message;
//    }


    //파이어베이스
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

//    private ActivityFirebaseUiBinding mBinding;

//무엇인지 모르는 곳
//    public fragment_tab1(Context context) {
//        mContext = context;
//        // Required empty public constructor
//    }
//여기까지

//    public fragment_tab1( ) {
//
//        //파이어베이스 연동
//        mAuth = FirebaseAuth.getInstance();
//
//        // Required empty public constructor
//        this.userName = userName;
//        this.message = message;
//
//        listView = (ListView) listView.findViewById(R.id.listView);
//        editTextData3 = (EditText) editTextData3.findViewById(R.id.editTextDate3);
//        button2 = (Button) button2.findViewById(R.id.button2);
//
//        userName = "user" + new Random().nextInt(10000);


//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        listView.setAdapter((ListAdapter) adapter);



//        listView = (ListView) findViewById(R.id.listView);
//        editText = (EditText) findViewById(R.id.editText);
//        sendButton = (Button) findViewById(R.id.button);
//
//        userName = "user" + new Random().nextInt(10000);  // 랜덤한 유저 이름 설정 ex) user1234
//
//// 기본 Text를 담을 수 있는 simple_list_item_1을 사용해서 ArrayAdapter를 만들고 listview에 설정
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
//        listView.setAdapter(adapter);

//    }



//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        mBinding = ActivityFirebaseUiBinding.inflate(getLayoutInflater());
//        setContentView(mBinding.getRoot());
//
        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//
//        mBinding.signInButton.setOnClickListener(this);
//        mBinding.signOutButton.setOnClickListener(this);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        lv_chating = (ListView) view.findViewById(R.id.lv_chating);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        et_msg = (EditText) view.findViewById(R.id.et_msg);

        arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1);
        lv_chating.setAdapter(arrayAdapter);

        // 리스트뷰가 갱신될때 하단으로 자동 스크롤
        lv_chating.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        str_name = "Guest " + new Random().nextInt(1000);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                // map을 사용해 name과 메시지를 가져오고, key에 값 요청
                Map<String, Object> map = new HashMap<String, Object>();

                // key로 데이터베이스 오픈
                String key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference dbRef = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String, Object>();

                objectMap.put("str_name", str_name);
                objectMap.put("text", et_msg.getText().toString());

                dbRef.updateChildren(objectMap);
                et_msg.setText("");
            }
        });


        /*
        addChildEventListener는 Child에서 일어나는 변화를 감지
        - onChildAdded()   : 리스트의 아이템을 검색하거나 추가가 있을 때 수신
        - onChildChanged() : 리스트의 아이템의 변화가 있을때 수신
        - onChildRemoved() : 리스트의 아이템이 삭제되었을때 수신
        - onChildMoved()   : 리스트의 순서가 변경되었을때 수신
         */

        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatListener(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatListener(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void chatListener(DataSnapshot dataSnapshot) {
        // dataSnapshot 밸류값 가져옴
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_user = (String) ((DataSnapshot) i.next()).getValue();
            str_msg = (String) ((DataSnapshot) i.next()).getValue();

            // 유저이름, 메시지를 가져와서 array에 추가
            arrayAdapter.add(chat_user + " : " + str_msg);
        }

        // 변경된값으로 리스트뷰 갱신
        arrayAdapter.notifyDataSetChanged();
    }

//    //파이어베이스 연동
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }




}





