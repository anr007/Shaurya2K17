package red.shaurya2k17.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import red.shaurya2k17.Match;
import red.shaurya2k17.R;


/**
 * Created by narayana on 1/3/17.
 */

public class MatchListViewAdapter extends ArrayAdapter<Match> {

    ArrayList<Match> dataset;
    Context mContext;

    private static class ViewHolder {
        TextView match_nam;
        TextView t1;
        TextView t2;
        TextView win;
    }

    public MatchListViewAdapter(ArrayList<Match> data, Context context) {
        super(context, R.layout.rv_template, data);
        this.dataset = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Match entry = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rv_template, parent, false);
            viewHolder.match_nam = (TextView) convertView.findViewById(R.id.mat_nam_lv_temp);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.t1_lv_temp);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.t2_lv_temp);
            viewHolder.win = (TextView) convertView.findViewById(R.id.win_lv_temp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.match_nam.setText(entry.getMat_nam());
        viewHolder.t1.setText(entry.getT1());
        viewHolder.t2.setText(entry.getT2());
        viewHolder.win.setText(entry.getWin());

        return convertView;
    }
}
