package com.example.practice26;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MenuItem practiceMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // 옵션메뉴 : Activity의 주요 메뉴
    // Action Bar에서 나타남
    // Activity class 내에 메소드를 오버라이딩


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        practiceMenu = menu.add(1, 0, 0, "practice1");
        practiceMenu = menu.add(1, 1, 0, "practice2");
        practiceMenu = menu.add(1, 2, 0, "practice3");
        SubMenu subEtc = menu.addSubMenu(1, 5, 0, "other");
        subEtc.add(1, 11, 0, "other1");
        subEtc.add(1, 12, 0, "other2");
        return super.onCreateOptionsMenu(menu);
    }

    // 옵션메뉴 항목을 선택했을 때 호출되는 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = item.getTitle().toString();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if(item.getItemId() == 0) {
            Intent practiceIntent = new Intent(MainActivity.this, PracticeActivity01.class);
            startActivity(practiceIntent);
        } else if(item.getItemId() == 1) {

        }
        return super.onOptionsItemSelected(item);
    }
}