package com.example.ysb_dbpersonnelresistapp;
import android.content.Intent;
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

//import com.example.b_personnelregistapp.databinding.ActivityPersonnelinfoBinding;
import com.example.ysb_dbpersonnelresistapp.databinding.ActivityPersonnelinfoBinding;
public class PersonnelInfo extends AppCompatActivity {
     DBManager dbManager;
     SQLiteDatabase sqLitedb;
    //private AppBarConfiguration appBarConfiguration;
    private ActivityPersonnelinfoBinding binding;

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
        setContentView(R.layout.activity_personnelinfo);

        binding = ActivityPersonnelinfoBinding.inflate(getLayoutInflater());
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
        str_studentid= iit.getStringExtra("it_studentid");
        str_phoneno=iit.getStringExtra("it_phoneno");
        str_studentgrade=iit.getStringExtra("it_studentgrade");
        str_hobby=iit.getStringExtra("it_hobby");
        str_area =iit.getStringExtra("it_area");
        str_photouri=iit.getStringExtra("it_photouri");

        tv_name.setText(str_name);
        tv_studentid.setText(str_studentid);
        tv_studentgrade.setText(str_studentgrade);
        tv_phoneno.setText(str_phoneno);
        tv_hobby.setText(str_hobby);
        tv_area.setText(str_area);

        photouri= Uri.parse(str_photouri);
        img_photo.setImageURI(photouri);

    }//onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnelinfo, menu);
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
        } else if(id == R.id.action_settings1){
            //홈
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            finish();
            return true;
        }else if(id == R.id.action_settings4){
            //조회
            Intent it = new Intent(this,PersonnelList.class);
            startActivity(it);
            finish();
            return true;
        }else if(id == R.id.action_settings5){
            //삭제| 지금 보고 있는 데이터를 레코드에서 삭제한다.
            try{
                //db를 이 액티비티에서 연다
                dbManager=new DBManager(this);
                // 리더블버전 라이터블 버전이 있음 쓰기 전용으로 해야지 삭제가 가능함.
                sqLitedb=dbManager.getWritableDatabase();
                sqLitedb.delete(//레코드를 삭제해야함 name 이라고 하는 필드를 삭제할거니 스트링 배열의
                                // str 네임의 삭제가 이루어짐
                 "Personnel"   ,"name=?"  ,
                  new String[]  {str_name});
               sqLitedb.close();
               dbManager.close();

            }catch(SQLiteException e){
            //carth는 SQLiteException 을 잡는다. (예외처리)
               Toast.makeText(getApplicationContext(),
                       e.getMessage(),Toast.LENGTH_LONG).show();
           }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}