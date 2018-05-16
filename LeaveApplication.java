package lav.pepbill;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 12/21/2017.
 */

public class LeaveApplication extends AppCompatActivity {
    ImageView image_iv,employer1_iv,employer2_iv,employer3_iv;
    TextView name_tv, id_tv, designation_tv,leavtype_tv,medical_tv,casual_tv,emergency_tv,from_tv,to_tv;
    EditText reason_et;
    Button requeststatus_b;
    Intent intent;
    DatePickerDialog datepicker;
    String month,date,todate,tomonth,reason,phone,leavetype;
    long epoch,toepoch;

    String URL ="http://pepbill.in/pepbill/firstproject/employee_leavemanagement.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveapplication);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        image_iv=(ImageView)findViewById(R.id.iv_image);
        employer1_iv=(ImageView)findViewById(R.id.iv_employer1);
        employer2_iv=(ImageView)findViewById(R.id.iv_employer2);
        employer3_iv=(ImageView)findViewById(R.id.iv_employer3);
        name_tv=(TextView)findViewById(R.id.tv_name);
        id_tv=(TextView)findViewById(R.id.tv_id);
        designation_tv=(TextView)findViewById(R.id.tv_designation);
        leavtype_tv=(TextView)findViewById(R.id.tv_leavtype);
        medical_tv=(TextView)findViewById(R.id.tv_medical);
        casual_tv=(TextView)findViewById(R.id.tv_casual);
        emergency_tv=(TextView)findViewById(R.id.tv_emergency);
        from_tv=(TextView)findViewById(R.id.tv_from);
        to_tv=(TextView)findViewById(R.id.tv_to);
        reason_et=(EditText)findViewById(R.id.et_reason);
        requeststatus_b=(Button)findViewById(R.id.b_requeststatus);
        requeststatus_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaveApplication.this, RequestStatus.class);
                startActivity(intent);
            }
        });

        intent=getIntent();
        phone=intent.getStringExtra("PHONE");
        name_tv.setText(intent.getStringExtra("UN"));
        id_tv.setText(intent.getStringExtra("ID"));
        designation_tv.setText(intent.getStringExtra("DESIGN"));

        medical_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leavtype_tv.setText("MEDICAL");
                leavetype = "medical";
            }
        });

        casual_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leavtype_tv.setText("CASUAL");
                leavetype = "casual";
            }
        });

        emergency_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leavtype_tv.setText("EMERGENCY");
                leavetype = "emergency";
            }
        });

        from_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(LeaveApplication.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        from_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);
                        date=year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("DATE",date);
                        String y = year + " "
                                + monthOfYear + " " + dayOfMonth;
                        Log.e("y",y);
                        if ((monthOfYear+1)==1){
                            month = "January";
                        }else if ((monthOfYear+1)==2){
                            month = "February";
                        }else if ((monthOfYear+1)==3){
                            month = "March";
                        }else if ((monthOfYear+1)==4){
                            month = "April";
                        }else if ((monthOfYear+1)==5){
                            month = "May";
                        }else if ((monthOfYear+1)==6){
                            month = "June";
                        }else if ((monthOfYear+1)==7){
                            month = "July";
                        }else if ((monthOfYear+1)==8){
                            month = "August";
                        }else if ((monthOfYear+1)==9){
                            month = "September";
                        }else if ((monthOfYear+1)==10){
                            month = "October";
                        }else if ((monthOfYear+1)==11){
                            month = "November";
                        }else if ((monthOfYear+1)==12){
                            month = "December";
                        }
                        String z = month;
                        Log.e("MONTH",z);
                        String x = z+" "+dayOfMonth+" "+year+" 23:11:52.454 UTC";
                        Log.e("x",x);
                        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
                        Date date = null;
                        try {
                            date = df.parse(x);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("DATE ERROR", String.valueOf(e));
                        }
                         epoch = date.getTime();
                        Log.e("EPOCH", String.valueOf(epoch));

                    }
                }, mYear, mMonth, mDay);
                datepicker.show();
            }
        });

        to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(LeaveApplication.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        to_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);
                        todate=year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("DATE",todate);
                        String y = year + " "
                                + monthOfYear + " " + dayOfMonth;
                        Log.e("y",y);
                        if ((monthOfYear+1)==1){
                            tomonth = "January";
                        }else if ((monthOfYear+1)==2){
                            tomonth = "February";
                        }else if ((monthOfYear+1)==3){
                           tomonth = "March";
                        }else if ((monthOfYear+1)==4){
                            tomonth = "April";
                        }else if ((monthOfYear+1)==5){
                            tomonth = "May";
                        }else if ((monthOfYear+1)==6){
                            tomonth = "June";
                        }else if ((monthOfYear+1)==7){
                            tomonth = "July";
                        }else if ((monthOfYear+1)==8){
                            tomonth = "August";
                        }else if ((monthOfYear+1)==9){
                            tomonth = "September";
                        }else if ((monthOfYear+1)==10){
                            tomonth = "October";
                        }else if ((monthOfYear+1)==11){
                            tomonth = "November";
                        }else if ((monthOfYear+1)==12){
                            tomonth = "December";
                        }
                        String z = tomonth;
                        Log.e("TO MONTH",z);
                        String x = z+" "+dayOfMonth+" "+year+" 23:11:52.454 UTC";
                        Log.e("to x",x);
                        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
                        Date date = null;
                        try {
                            date = df.parse(x);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.e("TO DATE ERROR", String.valueOf(e));
                        }
                         toepoch = date.getTime();
                        Log.e("TO EPOCH", String.valueOf(toepoch));

                    }
                }, mYear, mMonth, mDay);
                datepicker.show();
            }
        });

        employer1_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               reason = reason_et.getText().toString();
                Log.e("REASON",reason);
                update();
            }
        });

        employer2_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = reason_et.getText().toString();
            }
        });

        employer3_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason = reason_et.getText().toString();
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

    public void update(){
        try {
            params.put("emp_phone", phone);
            params.put("emp_email", "prabhu.palani1990@gmail.com");
            params.put("emp_leavetype", leavetype);
            params.put("emp_datefrom", epoch);
            params.put("emp_dateto", toepoch);
            params.put("emp_reason", reason);
            Log.e("PARAM", reason);
            Log.e("PARMS",phone);
        } catch (JSONException e) {
            Log.e("PARAM ERROR", "" + e);
            e.printStackTrace();
        }

        showProgressDialog();
        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.POST, URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("METHOD PARAMS", String.valueOf(params));
                Log.e("URL", URL);
                Log.e("RES", response.toString());
                try {
                    String status = response.getString("status");
                    Log.e("STATUS", status);
                    if (status.equals("1")) {
                        Log.e("SUCCESS", "success");
                        String message = response.getString("message");
                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("INSERT", String.valueOf(e));
                }
                hideProgressDialog();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOGIN ERROR", String.valueOf(error));
                hideProgressDialog();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Content-Type", "application/json");

                return map;
            }
        };
        obj.setRetryPolicy(new DefaultRetryPolicy(
                40000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(obj, "tag");
    }
}
