package lav.pepbill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Ashwin on 12/21/2017.
 */

public class ProfileEmp extends AppCompatActivity {
    ImageView image_iv;
    TextView name_tv, id_tv, designation_tv, dob_tv, doj_tv, mobile_tv,email_tv,address_tv,blood_tv, marital_tv;
    Button edit_b;
    String dob,phone,blood,email,address,doj,marital,un,id,design,im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileemp);

        image_iv=(ImageView)findViewById(R.id.iv_image);
        id_tv = (TextView)findViewById(R.id.tv_id);
        name_tv = (TextView)findViewById(R.id.tv_name);
        designation_tv = (TextView)findViewById(R.id.tv_designation);
        dob_tv = (TextView)findViewById(R.id.tv_dob);
        doj_tv = (TextView)findViewById(R.id.tv_doj);
        mobile_tv = (TextView)findViewById(R.id.tv_mobile);
        email_tv = (TextView)findViewById(R.id.tv_email);
        address_tv= (TextView)findViewById(R.id.tv_address);
        blood_tv = (TextView)findViewById(R.id.tv_blood);
        marital_tv = (TextView)findViewById(R.id.tv_marital);
        edit_b=(Button)findViewById(R.id.b_edit);

        Intent intent = getIntent();
        dob=intent.getStringExtra("DOB");
        Log.e("DOBB",dob);
        phone=intent.getStringExtra("PHONE");
        blood=intent.getStringExtra("BLOOD");
        email=intent.getStringExtra("EMAIL");
        address=intent.getStringExtra("ADDRESS");
        doj=intent.getStringExtra("DOJ");
        marital=intent.getStringExtra("MARITAL");
        un=intent.getStringExtra("UN");
        id=intent.getStringExtra("ID");
        design=intent.getStringExtra("DESIGN");
        im=intent.getStringExtra("IM");

      //  Glide.with(ProfileEmp.this).load("http://192.168.0.107/" + im)
      //          .into(image_iv);
        id_tv.setText(id);
        name_tv.setText(un);
        designation_tv.setText(design);
        dob_tv.setText(dob);
        doj_tv.setText(doj);
        mobile_tv.setText(phone);
        email_tv.setText(email);
        address_tv.setText(address);
        blood_tv.setText(blood);
        marital_tv.setText(marital);

        edit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentedit = new Intent(ProfileEmp.this,EditProfileEmp.class);
                intentedit.putExtra("UN",un);
                intentedit.putExtra("DOB",dob);
                intentedit.putExtra("Mobile",phone);
                intentedit.putExtra("Email",email);
                intentedit.putExtra("Blood",blood);
                intentedit.putExtra("Marital",marital);
                intentedit.putExtra("ID",id);
                intentedit.putExtra("DESIGNATION",design);
                intentedit.putExtra("DOJ",doj);
                startActivity(intentedit);
            }
        });
    }
}
