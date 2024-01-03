package com.example.test003;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test003.R;
import com.example.test003.Customer.Customer;
import com.example.test003.Customer.CustomerAdapter;
import com.example.test003.ItemTouchHelper.ItemTouchHelperCallback;
import com.google.android.material.divider.MaterialDividerItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btn1,btn2,btn3;
    // 각각 생년월일과 추가, 삭제 버튼

    EditText ed1, ed2;
    // 각각 이름과 전화번호

    TextView tv2;
    // 어댑터의 개수

    RecyclerView recyclerView;

    CustomerAdapter adapter;

    ItemTouchHelper helper;

    SimpleDateFormat s = new SimpleDateFormat("yyyy년 MM월 dd일");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        adapter = new CustomerAdapter(width);

        recyclerView = findViewById(R.id.recy);
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        // 리사이클러뷰끼리 구분선 만들기
        //recyclerView.addItemDecoration(new MaterialDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(recyclerView);


        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed2.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        tv2 = findViewById(R.id.tv2);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed1.getText().toString();
                String birth = btn1.getText().toString();
                String number = ed2.getText().toString();

                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if(birth.equals("생년월일")){
                    Toast.makeText(MainActivity.this, "생년월일을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else if(number.equals("")){
                    Toast.makeText(MainActivity.this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("추가하기");
                    builder.setMessage(name + "  " + birth + "\n" + number);
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adapter.add(new Customer(ed1.getText().toString(), btn1.getText().toString(), ed2.getText().toString()));
                            if(adapter.isDup(adapter.getItemCount()-1)){
                                adapter.sub(adapter.getItemCount()-1);
                                Toast.makeText(MainActivity.this, "중복되었습니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                recyclerView.setAdapter(adapter);
                                tv2.setText(adapter.getItemCount() + "명");
                            }
                        }
                    });

                    builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Customer customer = new Customer("이희웅", " 1998년 6월 11일", "010-5579-9413");
        adapter.add(customer);
        recyclerView.setAdapter(adapter);
        /*
        adapter.setListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("삭제하기");

                Customer customer = adapter.getItemCustomer(position);
                String name = customer.getName();
                String birth = customer.getbirth();
                String number = customer.getNumber();
                builder.setMessage(name + "  " + birth + "\n" + number);

                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.sub(position);
                        recyclerView.setAdapter(adapter);
                        tv2.setText(adapter.getItemCount() + "명");
                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/
    }

    private void setDate(){
        Calendar initCalendar = Calendar.getInstance();
        try {
            Date initDate = s.parse("2000년 01월 01일");
            initCalendar.setTime(initDate);

            int year = initCalendar.get(Calendar.YEAR);
            int month = initCalendar.get(Calendar.MONTH);
            int day_of_month = initCalendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(this, onDateSetListener, year, month, day_of_month);
            dialog.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day_of_month) {
            Calendar setCalendar = Calendar.getInstance();
            setCalendar.set(Calendar.YEAR, year);
            setCalendar.set(Calendar.MONTH, month);
            setCalendar.set(Calendar.DAY_OF_MONTH, day_of_month);

            Date setDate = setCalendar.getTime();
            btn1.setText(s.format(setDate));
        }
    };
}