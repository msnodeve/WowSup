package com.seok.seok.wowsup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seok.seok.wowsup.R;
import com.seok.seok.wowsup.utilities.Country;

import java.util.ArrayList;

public class CountryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Country> list;
    private TextView txtTitle;
    private ImageView imgView;

    public CountryAdapter(Context context, ArrayList<Country> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.country_spinner_item, viewGroup, false);
        }
        txtTitle = view.findViewById(R.id.sn_txt_country);
        imgView = view.findViewById(R.id.sn_img_country);
        Country country = list.get(i);
        txtTitle.setText(country.getCountry());
        imgView.setImageResource(country.getImgCountry());
        return view;
    }
}
