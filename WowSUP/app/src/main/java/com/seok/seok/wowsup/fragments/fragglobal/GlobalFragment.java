package com.seok.seok.wowsup.fragments.fragglobal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.retrofit.model.ResponseWordChart;
import com.seok.seok.wowsup.retrofit.remote.ApiUtils;
import com.seok.seok.wowsup.utilities.Common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalFragment extends Fragment {
    private List<Entry> words;
    private List<String> wordList;
    private List<Integer> colors;

    @BindView(R.id.frag_gb_chart)
    PieChart pieChart;

    public GlobalFragment() {
    }

    public static GlobalFragment newInstance() {
        return new GlobalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }
    @OnClick(R.id.frag_gb_btn_more) void goMore(){

    }
    public void init(){
        words = new ArrayList<>();
        wordList = new ArrayList<>();
        colors = new ArrayList<>();
        pieChart.setUsePercentValues(true);
        pieChart.setDescription(null);
        pieChart.setRotationEnabled(true);
        ApiUtils.getChartService().wordChart().enqueue(new Callback<List<ResponseWordChart>>() {
            @Override
            public void onResponse(Call<List<ResponseWordChart>> call, Response<List<ResponseWordChart>> response) {
                Log.d("WowSup_global_HTTP", "http trans Success");
                if(response.isSuccessful()){
                    List<ResponseWordChart> body = response.body();
                    for (int i = 0; i < body.size(); i++) {
                        words.add(new Entry(body.get(i).getWordCount(), i));
                        wordList.add(body.get(i).getWord());
                    }
                    PieDataSet dataSet = new PieDataSet((List)words, "");
                    //그래프 크기 조절
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(10f);

                    //PieData data = new PieData(wordList, dataSet);
//                    pieChart.setData(data);
//                    pieChart.invalidate();
//                    pieChart.getLegend ().setEnabled ( false );
//
//                    //글짜 크기 하고 색
//                    data.setValueFormatter(new MyValueFormatter());
//                    data.setValueTextSize(15f);
//                    data.setValueTextColor(Color.rgb(65,170,112));

                    for (int color : Common.WOWSUP_COLOR)
                        colors.add(color);
                    dataSet.setColors(colors);
                }
                pieChart.animateXY(1500, 1500);
            }
            @Override
            public void onFailure(Call<List<ResponseWordChart>> call, Throwable t) {
                Log.d("WowSup_global_HTTP", "http trans Failed");
            }
        });
    }
    public class MyValueFormatter extends ValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter() {
            // use one decimal if needed
            mFormat = new DecimalFormat("###,###,##0");
        }
        @Override
        public String getFormattedValue(float value) {
            // e.g. append a dollar-sign
            return mFormat.format(value) + "%";
        }
    }
}