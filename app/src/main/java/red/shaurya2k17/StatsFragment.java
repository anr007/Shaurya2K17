package red.shaurya2k17;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


public class StatsFragment extends Fragment implements OnChartValueSelectedListener {

    PieChart mChart;
    SwipeRefreshLayout swipeContainer;
    ArrayList<PieEntry> Scores = new ArrayList<PieEntry>();
    ArrayList<String> Labels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mChart = (PieChart) view.findViewById(R.id.pie_chart_stats);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.srStats);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefresh();

        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        //mChart.setHoleRadius(58f);
        //mChart.setTransparentCircleRadius(61f);

        mChart.setHoleRadius(52f);
        mChart.setTransparentCircleRadius(55f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setDrawEntryLabels(true);
        mChart.setCenterText("STATS");
        mChart.setCenterTextSize(24);


        mChart.setOnChartValueSelectedListener(this);

        mChart.animateY(1600, Easing.EasingOption.EaseInOutQuad);

        refreshData();
        // create pie data set
        PieDataSet dataSet = new PieDataSet(Scores,"Teams");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(8);


        // add many colors
        dataSet.setColors(new int[]{R.color.mgreen,R.color.mblue,R.color.morange,R.color.mred,
                R.color.mpink},getActivity());

        // instantiate pie data object now
        PieData data = new PieData(dataSet);


        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);


        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();


        // customize legends

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setXEntrySpace(15);
        l.setYEntrySpace(2);
        l.setTextSize(14f);
        l.setFormSize(10f);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        ((Home)getActivity()).showDialog(getContext(),Labels.get((int)h.getX()));

    }

    @Override
    public void onNothingSelected() {

        Toast.makeText(getContext(),"Select any Team",Toast.LENGTH_SHORT).show();
    }

    void swipeRefresh()
    {


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.mgreen,
                R.color.mblue, R.color.morange, R.color.mred);
    }

    void refreshData()
    {
        mChart.invalidate();
        Scores.clear();
        Labels.clear();
        Set<Map.Entry<String, String>> set = ((Home) getActivity()).wData.entrySet();
        for(Map.Entry<String, String> data : set){
            Scores.add(new PieEntry(Integer.parseInt(data.getValue()), data.getKey()));
            Labels.add(data.getKey());
        }
        if(swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(false);
        mChart.notifyDataSetChanged();
        mChart.animateXY(1200, 1200);
    }



}

