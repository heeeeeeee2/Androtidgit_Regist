package com.example.ysb_dbpersonnelresistapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ysb_dbpersonnelresistapp.databinding.ActivityPersonneldetailBinding;
import com.example.ysb_dbpersonnelresistapp.databinding.ActivityPersonnelinfoBinding;

public class PersonnelDatail extends AppCompatActivity {
     DBManager dbManager;
     SQLiteDatabase sqLitedb;
    //private AppBarConfiguration appBarConfiguration;
    private ActivityPersonneldetailBinding binding;

    TextView tv_name,tv_studentid,tv_studentgrade;
    TextView tv_phoneno,tv_hobby,tv_area;
    ImageView img_photo;

    String str_name,str_studentid,str_phoneno,str_studentgrade;
    String str_hobby;
    String str_area;
    String str_photouri;
    Uri photouri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personneldetail);

        binding = ActivityPersonneldetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        tv_name=(TextView) findViewById(R.id.name);
        tv_studentid=(TextView) findViewById(R.id.studentid);
        tv_studentgrade = (TextView) findViewById(R.id.studentgrade);
        tv_phoneno= (TextView) findViewById(R.id.phoneno);
        tv_hobby=(TextView) findViewById(R.id.hobby);
        tv_area=(TextView) findViewById(R.id.area);
        img_photo=(ImageView) findViewById(R.id.photo);

        Intent iit = getIntent();
        str_name = iit.getStringExtra("it_name");

            try{
                //db를 이 액티비티에서 연다
                dbManager=new DBManager(this);
                // 리더블버전 라이터블 버전이 있음 쓰기 전용으로 해야지 삭제가 가능함.
                sqLitedb=dbManager.getWritableDatabase();
                Cursor cursor = sqLitedb.rawQuery(
                        "select * from Personnel where name =? ",
                        new String[]{str_name},null);
                if(cursor != null && cursor.moveToNext()){
                    str_studentid=cursor.getString(cursor.getColumnIndex("studentid"));
                    str_studentgrade=cursor.getString(cursor.getColumnIndex("grade"));
                    str_phoneno=cursor.getString(cursor.getColumnIndex("phoneno"));
                    str_hobby=cursor.getString(cursor.getColumnIndex("hobby"));
                    str_area=cursor.getString(cursor.getColumnIndex("area"));
                    str_photouri=cursor.getString(cursor.getColumnIndex("photo"));
                    cursor.close();
                    sqLitedb.close();
                    dbManager.close();
                }//if

            }catch(SQLiteException e){
            //carth는 SQLiteException 을 잡는다. (예외처리)
               Toast.makeText(getApplicationContext(),
                       e.getMessage(),Toast.LENGTH_LONG).show();
           }
        tv_name.setText(str_name);
        tv_studentid.setText(str_studentid);
        tv_studentgrade.setText(str_studentgrade);
        tv_phoneno.setText(str_phoneno);
        tv_hobby.setText(str_hobby);
        tv_area.setText(str_area);

        photouri= Uri.parse(str_photouri);
        img_photo.setImageURI(photouri);

    }

}




