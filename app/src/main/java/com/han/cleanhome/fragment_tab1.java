package com.han.cleanhome;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//파이어베이스
//import com.google.firebase.quickstart.auth.R;
//import com.google.firebase.quickstart.auth.databinding.ActivityFirebaseUiBinding;



public class fragment_tab1 extends Fragment {


    public fragment_tab1(Context mContext) {
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



    Context mContext;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        return view;
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





