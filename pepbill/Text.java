package lav.pepbill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashwin on 2/6/2018.
 */

public class Text extends AppCompatActivity {

    EditText editmsg_et;
    Button sendmsg_b;
    String msg;
    RecyclerView text_rv;
    LinearLayoutManager lm;
    TextAdapter adapter;
    ArrayList<String> message = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        editmsg_et=(EditText)findViewById(R.id.et_editmsg);
        sendmsg_b=(Button)findViewById(R.id.b_sendmsg);
        text_rv=(RecyclerView)findViewById(R.id.rv_text);
        lm=new LinearLayoutManager(this);
        text_rv.setLayoutManager(lm);
        adapter = new TextAdapter(Text.this,message);
        text_rv.setAdapter(adapter);

        Intent intent = getIntent();
      String x = intent.getStringExtra("PH");
        Toast.makeText(getApplicationContext(),x,Toast.LENGTH_SHORT).show();

        sendmsg_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg=editmsg_et.getText().toString();
                message.add(msg);
                editmsg_et.setText("");
            }
        });
    }
}
