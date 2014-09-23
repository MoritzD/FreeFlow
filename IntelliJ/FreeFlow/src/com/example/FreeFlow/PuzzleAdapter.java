package com.example.FreeFlow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sami on 23.09.14.
 */
public class PuzzleAdapter extends ArrayAdapter<Puzzle> {

    private List<Puzzle> list;
    Context context;

    public PuzzleAdapter(Context context, int resource, List<Puzzle> puzzles) {
        super(context, resource, puzzles);

        this.context = context;
        this.list = puzzles;

    }


    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        if (itemView == null) {

            LayoutInflater vi = (LayoutInflater) parent.getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            itemView = vi.inflate(R.layout.grid_puzzle_square, parent, false);


            ViewHolder vh = new ViewHolder();

            vh.id = (TextView) itemView.findViewById(R.id.puzzle_id);
            itemView.setTag(vh);

        }

        ViewHolder vh = (ViewHolder) itemView.getTag();
        vh.id.setText(list.get(position).mId);
        return itemView;
    }


    private static class ViewHolder {
        TextView id;
    }

}
