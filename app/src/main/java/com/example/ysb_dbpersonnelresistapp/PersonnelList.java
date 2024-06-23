package com.example.ysb_dbpersonnelresistapp;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ysb_dbpersonnelresistapp.databinding.ActivityMainBinding;
import com.example.ysb_dbpersonnelresistapp.databinding.ActivityPersonnellistBinding;
public class PersonnelList extends AppCompatActivity {
    DBManager dbManager;
    SQLiteDatabase sqLitedb;
    //private AppBarConfiguration appBarConfiguration;
    private ActivityPersonnellistBinding binding;
    String str_name,str_studentid,str_phoneno,str_studentgrade;
    String str_hobby;
    String str_area;
    LinearLayout personlist;
    //  Uri photouri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnellist);

        binding = ActivityPersonnellistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        personlist=(LinearLayout) findViewById(R.id.personlist);


        try{
            //db를 열어서 테이블을 불러오고, moveTOnext 다음이 없을 때 까지 불러온다.
            dbManager = new DBManager(this);
            sqLitedb = dbManager.getReadableDatabase();
            Cursor cursor=sqLitedb.query("Personnel",null,null,
                                null,null,null,null);

            int i=0;
            while(cursor.moveToNext()){
                str_name=cursor.getString(cursor.getColumnIndex("name"));
                str_studentid=cursor.getString(cursor.getColumnIndex("studentid"));
                str_studentgrade=cursor.getString(cursor.getColumnIndex("grade"));
                str_phoneno=cursor.getString(cursor.getColumnIndex("phoneno"));
                str_hobby=cursor.getString(cursor.getColumnIndex("hobby"));
                str_area=cursor.getString(cursor.getColumnIndex("area"));

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20,10,20,10);
                layout.setId(i);
                layout.setTag(str_name);
                //이름
                TextView tv_name=new TextView(this);
                tv_name.setText(str_name);
                tv_name.setTextSize(30);
                tv_name.setBackgroundColor(Color.argb(50,0,255,0));
                layout.addView(tv_name);
                //학번
                TextView tv_studentid = new TextView(this);
                tv_studentid.setText(str_studentid);
                tv_studentid.setTextSize(15);
                layout.addView(tv_studentid);
                //학년
                TextView tv_grade = new TextView(this);
                tv_grade.setText(str_studentgrade);
                tv_grade.setTextSize(15);
                layout.addView(tv_grade);
                //전화번호
                TextView tv_phoneno = new TextView(this);
                tv_phoneno.setText(str_phoneno);
                tv_phoneno.setTextSize(15);
                layout.addView(tv_phoneno);


                personlist.addView(layout);
                i++;

                tv_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(PersonnelList.this,PersonnelDatail.class);
                        it.putExtra("it_name", tv_name.getText().toString());
                        startActivity(it);
                    }
                });
            }//while
        }catch(SQLiteException e){
            Toast.makeText(PersonnelList.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        dbManager.close();
        sqLitedb.close();
    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings2) {
            // 인물등록 액티비티 전환
            Intent it = new Intent(this,PersonnelReg.class);
            startActivity(it);
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}