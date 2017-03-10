package red.shaurya2k17.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.List;

import red.shaurya2k17.Match;
import red.shaurya2k17.R;

/**
 * Created by reddy on 6/3/17.
 */

public class OngoingRecyclerViewAdapter extends RecyclerView.Adapter<OngoingRecyclerViewAdapter.MyViewHolder> {

    private List<Match> data;
    private int lastPosition = -1;
    private int lastAnimatedPosition = -1;
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView match_nam,t1,t2,t1s,t2s,t1n,t2n;
        public MyViewHolder(View view) {
            super(view);
            match_nam = (TextView) view.findViewById(R.id.mat_nam_ongoing_lv_temp);
            t1 = (TextView) view.findViewById(R.id.t1_ongoing_lv_temp);
            t2 = (TextView) view.findViewById(R.id.t2_ongoing_lv_temp);
            t1n = (TextView) view.findViewById(R.id.t1_name_score_ongoing_lv_temp);
            t2n = (TextView) view.findViewById(R.id.t2_name_score_ongoing_lv_temp);
            t1s = (TextView) view.findViewById(R.id.t1_score_ip_ongoing_lv_temp);
            t2s = (TextView) view.findViewById(R.id.t2_score_ip_ongoing_lv_temp);

        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Match match = data.get(position);
        holder.match_nam.setText(match.getMat_nam());
        holder.t1.setText(match.getT1());
        holder.t2.setText(match.getT2());
        holder.t1n.setText(match.getT1());
        holder.t2n.setText(match.getT2());
        holder.t1s.setText(match.getT1s());
        holder.t2s.setText(match.getT2s());
    }

    public OngoingRecyclerViewAdapter(List<Match> data, Context context) {
        this.data = data;
        mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ongoing_rv_template, parent, false);


        return new MyViewHolder(itemView);
    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }

    }

}
