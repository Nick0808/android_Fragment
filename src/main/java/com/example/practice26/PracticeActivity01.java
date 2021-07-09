package com.example.practice26;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
// Activity : 화면을 정적으로 분할, 더 이상 분할 할 수 없음
// Fragment : mini Activity
// Fragment는 재사용 가능 (프로그램 실행중에 추가, 제거, 교체 가능)

// Fragment 작성 방법
// 1. Fragment로 사용할 레이아웃 구성(XML파일)
// 2. Fragment class를 정의(Fragment class를 상속)
// - 내부 클래스(Activity 안) 설계
// - 외부 클래스(별도의 자바파일) 설계
// - 단 static 클래스로 정의
// - onCreateView()라는 콜백메소드를 오버라이딩해서 내부에 정의
// 3. Activity 영억에 플래그먼트 뷰를 배치
//  - 플래그먼트 매니저 객체를 정의
//  - 매니저가 트랜잭션 객체를 사용하여 배치

public class PracticeActivity01 extends AppCompatActivity {
    TextView txtScore1;
    Button btnCount1, btnReplace, btnInput;
    EditText edtCount;
    FragmentManager manager;
    FragmentTransaction transaction;
    int state = 0;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice01);
        setTitle("Frag_Practice");

        txtScore1 = (TextView)findViewById(R.id.linear_textView2);
        btnCount1 = (Button)findViewById(R.id.linear_button);
        btnCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = Integer.parseInt(txtScore1.getText().toString());
                txtScore1.setText(Integer.toString(score+count));
            }
        });

        edtCount = (EditText)findViewById(R.id.main_editText);
        btnInput = (Button)findViewById(R.id.main_button2);
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nstr = edtCount.getText().toString();
                if(!nstr.equals("")) { // 공백이 아니면
                    count = Integer.parseInt(nstr);
                } else {
                    Toast.makeText(PracticeActivity01.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 플래그먼트를 배치
        // 플래그먼트 매니저 객체를 정의
        manager = getSupportFragmentManager();
        // 플래그먼트 트랙잭션
        transaction = manager.beginTransaction();
        InitFragment frag = new InitFragment();
        // 트랜잭션에 의해 플래그먼트 추가
        // where what
        transaction.add(R.id.fragment_area, frag);
        transaction.commit(); // 트랜잭션 수행

        btnReplace = (Button)findViewById(R.id.main_button);
        btnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();

                if(state == 0) {
                    ScoreFragment frag = ScoreFragment.newInstance(count);
                    // 기존의 플래그먼트를 교체
                    transaction.replace(R.id.fragment_area, frag);
                    state = 1;
                } else {
                    InitFragment frag = new InitFragment();
                    transaction.replace(R.id.fragment_area, frag);
                    state = 0;
                }
                transaction.commit();
            }
        });
    }

    // Fragment class
    public static class InitFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Fragment로 사용할 View를 정의
            View view = inflater.inflate(R.layout.fragment_init, container, false);
            return view;
        }
        // 플래그먼트의 초기화 메소드
        public static InitFragment newInstance() {

            Bundle args = new Bundle();

            InitFragment fragment = new InitFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }

    public static class ScoreFragment extends Fragment {
        TextView txtScore2;
        Button btnCount2;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_score, container, false);
            txtScore2 = (TextView)view.findViewById(R.id.frag_Score_txtView);
            btnCount2 = (Button)view.findViewById(R.id.frag_score_button);
            btnCount2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int score = Integer.parseInt(txtScore2.getText().toString());
                    // 프래그먼트 자시느이 객체로 설정된 값을 꺼냄
                    int increase = getArguments().getInt("key_score");
                    txtScore2.setText(Integer.toString(score+increase));
                }
            });
            return view;
        }

        // 자신의 프래그먼트를 생성하는 메소드
        // 프래그먼트의 초기화
        // 매개변수를 정의하여 초기값을 받아들임
        public static ScoreFragment newInstance(int count) {
            // Bundle type : 값을 키와 같이 묶어서 표현한 자료
            Bundle args = new Bundle();
            // count를 args에 설정
            args.putInt("key_score", count);
            ScoreFragment fragment = new ScoreFragment();
            // 프래그먼트에 값(Bundle type)을 지정
            fragment.setArguments(args);
            return fragment;
        }
    }
}