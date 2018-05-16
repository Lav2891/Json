package lav.pepbill;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 1/5/2018.
 */

public class TaskManagement extends AppCompatActivity {
    TextView date_tv;
    EditText task1_et,task2_et,task3_et;
    Button choosefile1_b,choosefile2_b,choosefile3_b,submit_b;
    Spinner workstatus1_sp,workstatus2_sp,workstatus3_sp;
    DatePickerDialog datepicker;
    String month;
    String phone,task1,task2,task3,workstatus1,workstatus2,workstatus3,date;
    String URL = "http://pepbill.in/pepbill/firstproject/employee_taskmanagement.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskmanagementemp);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        Intent intent = getIntent();
         phone = intent.getStringExtra("PHONE");

        date_tv=(TextView)findViewById(R.id.tv_date);
        task1_et=(EditText)findViewById(R.id.et_task1);
        task2_et=(EditText)findViewById(R.id.et_task2);
        task3_et=(EditText)findViewById(R.id.et_task3);
        choosefile1_b=(Button)findViewById(R.id.b_choosefile1);
        choosefile2_b=(Button)findViewById(R.id.b_choosefile2);
        choosefile3_b=(Button)findViewById(R.id.b_choosefile3);
        workstatus1_sp=(Spinner)findViewById(R.id.sp_workstatus1);
        workstatus2_sp=(Spinner)findViewById(R.id.sp_workstatus2);
        workstatus3_sp=(Spinner)findViewById(R.id.sp_workstatus3);
        submit_b=(Button)findViewById(R.id.b_submit);

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(TaskManagement.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_tv.setText(year + "/"
                                + (monthOfYear + 1) + "/" + dayOfMonth);
                        date=year + "-"
                                + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("DATE",date);
                     /*   String y = year + " "
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
                        long epoch = date.getTime();
                        Log.e("EPOCH", String.valueOf(epoch));*/

                    }
                }, mYear, mMonth, mDay);
                datepicker.show();
            }
        });

        String[] workstatus = new String[]{"Select","Completed","Pending","Queries"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, workstatus);
        workstatus1_sp.setAdapter(adapter);
        workstatus2_sp.setAdapter(adapter);
        workstatus3_sp.setAdapter(adapter);

        workstatus1_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 workstatus1 = workstatus1_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        workstatus2_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 workstatus2 = workstatus2_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        workstatus3_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 workstatus3 = workstatus3_sp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        choosefile1_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefile();
            }
        });

        choosefile2_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefile();
            }
        });

        choosefile3_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosefile();
            }
        });

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task1 =  task1_et.getText().toString();
                Log.e("TASK1",task1);
                task2 = task2_et.getText().toString();
                task3 = task3_et.getText().toString();
                update();
            }
        });

    }

    public void choosefile(){
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, 50);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 50:
                if(resultCode==-1){
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    Toast.makeText(getApplicationContext(), filePath,
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
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
            params.put("task_date", date);
            params.put("task_1", task1);
            params.put("work_status1", workstatus1);
            params.put("upload_file1", choosefile1_b);
            params.put("task_2", task2);
            params.put("work_status2", workstatus2);
            params.put("upload_file2", choosefile2_b);
            params.put("task_3", task3);
            params.put("work_status3", workstatus3);
            params.put("upload_file3", choosefile3_b);
            Log.e("PARAM", phone);
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
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
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
