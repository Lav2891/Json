package lav.pepbill;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 12/19/2017.
 */

public class Register extends AppCompatActivity {
    Spinner type_s, gender_s;
    EditText name_et,phno_et,email_et,address_et,accno_et,password_et,confirmpass_et,designation_et;
    ImageView image_iv;
    Button upload_b,submit_b;
    TextView dob_tv,joindate_tv;
    DatePickerDialog datepicker;
    Validate validate = new Validate();
    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    String encoded;
    String URL = "http://192.168.0.114/firstproject/employee_signup.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;
    String type;
    String name;
    String gender;
    String dob;
    String joindate;
    String phno;
    String email;
    String address;
    String accno;
    String password;
    String confirmpass;
    String designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        type_s=(Spinner)findViewById(R.id.s_type);
        gender_s=(Spinner)findViewById(R.id.s_gender);
        name_et=(EditText)findViewById(R.id.et_name);
       // dob_et=(EditText)findViewById(R.id.et_dob);
        dob_tv=(TextView)findViewById(R.id.tv_dob);
        //joiningdate_et=(EditText)findViewById(R.id.et_joiningdate);
        joindate_tv=(TextView)findViewById(R.id.tv_joiningdate);
        phno_et=(EditText)findViewById(R.id.et_phno);
        email_et=(EditText)findViewById(R.id.et_email);
        designation_et=(EditText)findViewById(R.id.et_designation);
        address_et=(EditText)findViewById(R.id.et_address);
        accno_et=(EditText)findViewById(R.id.et_accno);
        password_et=(EditText)findViewById(R.id.et_password);
        confirmpass_et=(EditText)findViewById(R.id.et_confirmpass);
        image_iv=(ImageView)findViewById(R.id.iv_image);
        upload_b=(Button)findViewById(R.id.b_upload);
        submit_b=(Button)findViewById(R.id.b_submit);

        String[] items = new String[]{"Select", "Employer", "Employee"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        type_s.setAdapter(adapter);

        String[] itemsgender = new String[]{"Select", "Male", "Female"};
        ArrayAdapter<String> adaptergender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsgender);
        gender_s.setAdapter(adaptergender);

       dob_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dob_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
                datepicker.show();
            }
        });

        joindate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        joindate_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
                datepicker.show();
            }
        });

      phno_et.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
          }

          @Override
          public void afterTextChanged(Editable s) {
              int length = s.length();
              if (length!=10){
                  phno_et.setError("Ten Digits required");
              }
          }
      });

        email_et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                validate.isEmailAddress(email_et, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count){

            }
        });

        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length<8){
                    password_et.setError("Weak Password");
                }
            }
        });

        upload_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 type = type_s.getSelectedItem().toString();
                name = name_et.getText().toString();
                 gender = gender_s.getSelectedItem().toString();
                 dob = dob_tv.getText().toString();
                 joindate = joindate_tv.getText().toString();
                 phno = phno_et.getText().toString();
                email = email_et.getText().toString();
                address = address_et.getText().toString();
                 accno = accno_et.getText().toString();
                password = password_et.getText().toString();
                confirmpass = confirmpass_et.getText().toString();
                designation = designation_et.getText().toString();

                if (!password.equals(confirmpass)){
                    Toast.makeText(getApplicationContext(),"Passwords Mismatch",Toast.LENGTH_SHORT).show();
                }else if (type.isEmpty()||name.isEmpty()||gender.isEmpty()||dob.isEmpty()||joindate.isEmpty()||phno.isEmpty()||email.isEmpty()||address.isEmpty()||accno.isEmpty()||password.isEmpty()||confirmpass.isEmpty()||image_iv.getDrawable() == null){
                    Toast.makeText(getApplicationContext(),"Fill all the details",Toast.LENGTH_SHORT).show();
                }else {
                    register();
                    Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void register(){
        try {
            params.put("emp_type",type);
            params.put("emp_fname",name);
            params.put("emp_email",email);
            params.put("emp_phone",phno);
            params.put("emp_dob",dob);
            params.put("emp_address",address);
            params.put("emp_gender",gender);
            params.put("emp_accountdetails",accno);
            params.put("emp_dateofjoining",joindate);
            params.put("emp_password",password);
            params.put("emp_designation",designation);
            params.put("emp_image",encoded);
        } catch (JSONException e) {
            Log.e("PARAM ERROR", ""+e);
            e.printStackTrace();
        }
        showProgressDialog();
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL,params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("ON RESPONSE", String.valueOf(response));
                try {
                    String status = response.getString("status");
                    Log.e("STAUTS",status);
                    if (status.equals("1")){
                        String message = response.getString("message");
                        Log.e("MESSAGE",message);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", String.valueOf(error));
                hideProgressDialog();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("Content-Type","application/json");

                return map;
            }
        };

        obj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(obj, "tag");

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("ONACTIVITYRESULT","onactivity");
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("XXX", "Image Path : " + path);
                    image_iv.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri){
        Log.e("GET PATH","get path");
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(res, options);
            Log.e("BITMAP", String.valueOf(bitmap));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            Log.e("ENCODED",encoded);
        }
        cursor.close();
        return res;
    }

    public void checkPermission() {

        if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity)Register.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);


        }else {
            gallery();
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gallery();
                } else {
                    //code for deny
                    Log.e("DENY","deny");
                }
                break;
        }
    }

    public void gallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoPickerIntent,"Select Picture"),SELECT_PICTURE);

    }
}
