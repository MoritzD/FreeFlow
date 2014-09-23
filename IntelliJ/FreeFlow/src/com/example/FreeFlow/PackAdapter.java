package com.example.FreeFlow;

/**
 * Created by Sami on 23.09.14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Sami on 22.09.14.
 */
public class PackAdapter extends ArrayAdapter<Pack> {

    private List<Pack> list;
    Context context;


    public PackAdapter(Context context, int resource, List<Pack> objects){

        super(context, resource, objects);
        this.context = context;
        this.list = objects;

    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {

            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            itemView = vi.inflate(R.layout.list_pack, null);


            ViewHolder vh = new ViewHolder();

            vh.name = (TextView) itemView.findViewById(R.id.pack_name);
            vh.description = (TextView) itemView.findViewById(R.id.pack_description);

            itemView.setTag(vh);

        }


        ViewHolder vh = (ViewHolder) itemView.getTag();
        vh.name.setText(list.get(position).getName());
        vh.description.setText(list.get(position).getDescription());
        return itemView;
    }


    private static class ViewHolder {
        TextView name;
        TextView description;
    }






}
