package com.example.ysb_dbpersonnelresistapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ysb_dbpersonnelresistapp.databinding.ActivityMainBinding;
import com.example.ysb_dbpersonnelresistapp.databinding.ActivityPersonnelregBinding;

import java.net.URI;

public class PersonnelReg extends AppCompatActivity {

    DBManager dbManager;
    SQLiteDatabase sqLitedb;

    //private AppBarConfiguration appBarConfiguration;
    private ActivityPersonnelregBinding binding;

    public final static int REQUEST_PHOTO_CODE = 1;

    EditText name, studentid, phoneno;
    RadioGroup studentgrade;
    RadioButton one, two, three;
    CheckBox hobby01, hobby02, hobby03;
    ImageView findphoto;
    Button register;
    Spinner area;
    String[] str_area = {"서울", "인천", "안양", "수원", "성남"};
    String str_name, str_studentid, str_phoneno, str_studentgrade;
    String str_hobby;
    String str_areaitem;
    Uri photouri;
    String str_photouri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnelreg);

        binding = ActivityPersonnelregBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        name = (EditText) findViewById(R.id.name);
        studentid = (EditText) findViewById(R.id.studentid);
        phoneno = (EditText) findViewById(R.id.phoneno);
        studentgrade = (RadioGroup) findViewById(R.id.studentgrade);
        one = (RadioButton) findViewById(R.id.one);
        two = (RadioButton) findViewById(R.id.two);
        three = (RadioButton) findViewById(R.id.three);
        hobby01 = (CheckBox) findViewById(R.id.hobby01);
        hobby02 = (CheckBox) findViewById(R.id.hobby02);
        hobby03 = (CheckBox) findViewById(R.id.hobby03);
        findphoto = (ImageView) findViewById(R.id.photo);
        register = (Button) findViewById(R.id.register);

        area = (Spinner) findViewById(R.id.area);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        str_area);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        area.setAdapter(adapter);

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_areaitem = str_area[i];
                // Toast.makeText(getApplicationContext(),str_areaitem,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "거주지를 선택하세요", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_name = name.getText().toString();
                str_studentid = studentid.getText().toString();
                str_phoneno = phoneno.getText().toString();
                if (one.isChecked())
                    str_studentgrade = one.getText().toString();
                else if (two.isChecked())
                    str_studentgrade = two.getText().toString();
                else
                    str_studentgrade = three.getText().toString();

                str_hobby = "";
                if (hobby01.isChecked())
                    str_hobby = str_hobby + hobby01.getText().toString();
                if (hobby02.isChecked())
                    str_hobby = str_hobby + hobby02.getText().toString();
                if (hobby03.isChecked())
                    str_hobby = str_hobby + hobby03.getText().toString();

                str_photouri = photouri.toString();

                try {
                    dbManager = new DBManager(PersonnelReg.this);
                    sqLitedb = dbManager.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("name", str_name);
                    values.put("studentid", str_studentid);
                    values.put("grade", str_studentgrade);
                    values.put("phoneno", str_phoneno);
                    values.put("hobby", str_hobby);
                    values.put("area", str_areaitem);
                    values.put("photo", str_photouri);

                    long newRowId = sqLitedb.insert("Personnel", null, values);
                    sqLitedb.close();
                    dbManager.close();
                    // Toast.makeText(PersonnelR    eg.this,"성공ㄴㅇ",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(PersonnelReg.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent iit = new Intent(PersonnelReg.this, PersonnelInfo.class);
                iit.putExtra("it_name", str_name);
                iit.putExtra("it_studentid", str_studentid);
                iit.putExtra("it_studentgrade", str_studentgrade);
                iit.putExtra("it_phoneno", str_phoneno);
                iit.putExtra("it_hobby", str_hobby);
                iit.putExtra("it_area", str_areaitem);
                iit.putExtra("it_photouri", photouri.toString());
                startActivity(iit);

            }
        });//등록완료버튼
    }//oncreate
    public void findPhoto(View view) {
        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        it.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(it, REQUEST_PHOTO_CODE);

    }//findPhoto
    public void onActivityResult(int requestcode, int resultcode, Intent it) {
        super.onActivityResult(requestcode, resultcode, it);
        if (requestcode == REQUEST_PHOTO_CODE) {
            if (resultcode == RESULT_OK) {
                photouri = it.getData();
                findphoto.setImageURI(photouri);
            }
        } else {
            Toast.makeText(getApplicationContext(), "사진선택오류", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnelreg, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            // 홈 액티비티 전환
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}








