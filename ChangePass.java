package lav.pepbill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashwin on 12/21/2017.
 */

public class ChangePass extends AppCompatActivity {
    EditText currentpass_et,newpass_et,confirmpass_et;
    String currentpass,newpass,confirmpass,phone;
    Button submit_b;
    Intent intent;
    String URL = "http://pepbill.in/pepbill/firstproject/employee_changepassword1.php";
    JSONObject params = new JSONObject();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chngepassword);

        intent=getIntent();
        phone=intent.getStringExtra("PHONE");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        currentpass_et=(EditText)findViewById(R.id.et_currentpass);
        newpass_et=(EditText)findViewById(R.id.et_newpass);
        confirmpass_et=(EditText)findViewById(R.id.et_confirmnewpass);
        submit_b=(Button)findViewById(R.id.b_submit);

        submit_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentpass=currentpass_et.getText().toString();
                newpass=newpass_et.getText().toString();
                confirmpass=confirmpass_et.getText().toString();

                if (newpass.equals(confirmpass)){
                    upload();
                }else {
                    Toast.makeText(getApplicationContext(),"PASSWORDS DONT MATCH",Toast.LENGTH_SHORT).show();
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

    public void upload(){
        try {
            params.put("emp_phone", phone);
            params.put("old_pass", currentpass);
            params.put("new_pass", newpass);
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
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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
