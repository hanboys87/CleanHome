package com.han.cleanhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//여기서부터 파이어베이스 코드 시작
    // Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    // Views
    private ListView mListView;
    private EditText mEdtMessage;
    // Values
    private ChatAdapter mAdapter;
    private String userName;


    //임시 사용자 이름을 사용하기위해 (아직 Firebase Authentication을 적용하지 않았기 때문에..) userName을 설정하는
    //initValues() 함수가 추가 되었습니다. 
    protected void Create(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab1);
        initViews();
        initFirebaseDatabase();
        initValues();

    }

    private void initViews() {
        //mListView = (ListView) findViewById(R.id.list_message);
        mAdapter = new ChatAdapter(this, 0);
        mListView.setAdapter(mAdapter);

        //mEdtMessage = (EditText) findViewById(R.id.edit_message);
        findViewById(R.id.btn_send).setOnClickListener(this);
    }

    private void initFirebaseDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message");


// Firebase Realtime Database에 추가/편집 된 내용은 즉각 ChildEventListener를 통해서 이벤트가 전달 됩니다.
// 전달받은 이벤트를 통해 ChatData 를 생성하고 ChatAdapter에 추가 또는 삭제해 주는 코드로 수정 하였습니다!
// 살펴보면 onChildAdded(..) 통해 넘어온 DataSnapshot 객체를 이용해 ChatData로 형변환 (dataSnapshot.getvalue(ChatData.class)
// 한 이후에 firebaseKey값을 설정해주는 부분이 있는데요. 해당 key 값은 Firebase Realtime Database에 저장된 고유한 값이기 때문에
//나중에 리스트에서 삭제할 때나 변경할때 꼭 필요한 값입니다.
//여기에서는 서버에서 ChatData를 삭제할 때 호출되는 onChildRemoved(..) 콜백함수 내부에서 ChatAdapter의 내용을 지우기 위해 사용되었습니다.
                mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                mAdapter.add(chatData);
                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = mAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
                        mAdapter.remove(mAdapter.getItem(i));
                        break;
                    }
                }
            }
   
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }


    //임시 사용자 이름을 사용하기위해 (아직 Firebase Authentication을 적용하지 않았기 때문에..) userName을 설정하는
    //initValues() 함수가 추가 되었습니다.
    private void initValues() {
        userName = "Guest" + new Random().nextInt(5000);
    }


    protected void onDestory() {
        super.onDestroy();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }

    //기존에 String객체를 Firebase Realtime Database에 전송하던 것을 ChatData 객체를 생성해서 전송하는 방식으로 변경!
    public void onClick(View v) {
        String message = mEdtMessage.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            mEdtMessage.setText("");
            ChatData chatData = new ChatData();
            chatData.userName = userName;
            chatData.message = message;
            chatData.time = System.currentTimeMillis();
            mDatabaseReference.push().setValue(chatData);
        }
    }



    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        InitFragment();
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    //Intent openMain2Activity= new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(openMain2Activity);
                }
            }
        };
        timer.start();
    }

    //여기까지 파이어베이스 코드


    // 프래그먼트 초기화
    void InitFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fr_fragment, new fragment_tab1(mContext));
        fragmentTransaction.commit();
    }

    // 아래 버튼의 따른 프래그먼트를 스위칭해주는 부분
    @OnClick({R.id.bt_fragment0,R.id.bt_fragment1,R.id.bt_fragment2}) void BottomTabButton(View v){
        int id = v.getId();
        Fragment fr =  new fragment_tab0(mContext);
        if(id==R.id.bt_fragment0){
            fr = new fragment_tab0(mContext) ;
        }else if(id==R.id.bt_fragment1){
            fr = new fragment_tab1(mContext) ;
        }else if(id==R.id.bt_fragment2){
            fr = new fragment_tab2(mContext) ;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fr_fragment, fr);
        fragmentTransaction.commit();
    }

    public void bt_fragment2Clicked(View v){

        Intent myIntent = new Intent(getApplicationContext(), Alam.class);
        startActivity(myIntent);

    }
}