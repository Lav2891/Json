package lav.pepbill;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ashwin on 2/7/2018.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.viewholder> {

    Context context;
    ArrayList<String> message;

    public TextAdapter(Context context, ArrayList<String> message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public TextAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textadapter, parent, false);

        TextAdapter.viewholder myViewHolder = new TextAdapter.viewholder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(TextAdapter.viewholder holder, int position) {
        String getmessage = message.get(position);
        holder.getmsg_tv.setText(getmessage);
        Toast.makeText(context,getmessage,Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView getmsg_tv;

        public viewholder(View itemView) {
            super(itemView);
            getmsg_tv = (TextView)itemView.findViewById(R.id.tv_getmsg);

        }
    }
}
