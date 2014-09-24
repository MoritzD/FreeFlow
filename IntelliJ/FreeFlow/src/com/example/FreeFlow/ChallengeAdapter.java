package com.example.FreeFlow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sami on 22.09.14.
 */
public class ChallengeAdapter extends ArrayAdapter<Challenge> {

    Context context;
    private List<Challenge> list;


    public ChallengeAdapter(Context context, int resource, List<Challenge> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;

    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {

            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            itemView = vi.inflate(R.layout.list_challenge, null);


            ViewHolder vh = new ViewHolder();

            vh.name = (TextView) itemView.findViewById(R.id.challenge_name);
            itemView.setTag(vh);

        }
        ViewHolder vh = (ViewHolder) itemView.getTag();
        vh.name.setText(list.get(position).mName);
        return itemView;
    }


    private static class ViewHolder {
        TextView name;
    }


}
