package com.exercise.mission13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2; // 고객정보명, 추가된 인원수
    EditText ed1, ed2; // 이름과 전화번호 입력하는 text
    Button btn1, btn2; // 생년월일과 입력한 정보를 추가하는 버튼
    RecyclerView recyclerView;
    static int i = 0;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        PersonAdapter adapter = new PersonAdapter();

        tv1 = findViewById(R.id.main_tv1);
        tv2 = findViewById(R.id.main_tv2);

        ed1 = findViewById(R.id.main_ed1);
        ed2 = findViewById(R.id.main_ed2);

        btn1 = findViewById(R.id.main_btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btn2 = findViewById(R.id.main_btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed1.getText().toString();
                String birth = btn1.getText().toString();
                String mobile = ed2.getText().toString();
                if(name.equals("") || birth.equals("생년월일") || mobile.equals("")){
                    Toast.makeText(MainActivity.this, "빈칸이 없는지 확인해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("안내");
                    builder.setMessage("추가 하시겠습니까?");
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            adapter.addItem(new Person(name, birth, mobile));
                            tv2.setText(++i + "명");
                            recyclerView.setAdapter(adapter);
                        }
                    });

                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 아무것도 하지 않는다.
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        recyclerView.setAdapter(adapter);

        adapter.setOnLongClickPerson(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Person clickPerson = adapter.getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("안내");
                builder.setMessage(clickPerson.getName() +"님을 삭제하시겠습니까?");

                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.subItem(clickPerson);
                        tv2.setText(--i + "명");
                        recyclerView.setAdapter(adapter);
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 아무것도 하지 않음.
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void showDateDialog() {
        String birthDateStr = simpleDateFormat.format(new Date());
        Calendar calendar = Calendar.getInstance();
        Date curBirthDate = new Date();

        try{
            curBirthDate = simpleDateFormat.parse(birthDateStr);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        calendar.setTime(curBirthDate);

        int curYear = calendar.get(Calendar.YEAR);
        int curMonth = calendar.get(Calendar.MONTH);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, birthDateSetListener, curYear ,curMonth, curDay);
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, month);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Date selectedDate = selectedCalendar.getTime();
            setDate(selectedDate);
        }
    };

    private void setDate(Date date){
        selectedDate = date;
        String selectedStr = simpleDateFormat.format(date);
        btn1.setText(selectedStr);
    }
}