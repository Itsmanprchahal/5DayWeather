package com.example.admin.a5dayweather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Adapter extends BaseAdapter {


    Context context;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> descri = new ArrayList<>();
    ArrayList<String> Max = new ArrayList<>();
    ArrayList<String> Min = new ArrayList<>();
    ArrayList<String> Wind = new ArrayList<>();
    ArrayList<String> Degre = new ArrayList<>();



    public Adapter(Context context, ArrayList<String> arrayList, ArrayList<String> date, ArrayList<String> descri,ArrayList<String> max,ArrayList<String> min,ArrayList<String> wind,ArrayList<String> degre) {
        this.context = context;
        this.arrayList = arrayList;
        this.date = date;
        this.descri = descri;
        this.Max = max;
        this.Min = min;
        this.Wind = wind;
        this.Degre = degre;


    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_list, null);

            holder.Temp = convertView.findViewById(R.id.temp1);
            holder.Date = convertView.findViewById(R.id.date1);
            holder.Descri = convertView.findViewById(R.id.descri1);
            holder.imageView = convertView.findViewById(R.id.image1);
            holder.Max = convertView.findViewById(R.id.max_temp);
            holder.Min = convertView.findViewById(R.id.min_temp);
            holder.Wind = convertView.findViewById(R.id.wind);
            holder.Deg = convertView.findViewById(R.id.deg);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.Temp.setText(arrayList.get(position));
        holder.Date.setText(date.get(position));
        holder.Descri.setText(descri.get(position));
        holder.Max.setText(Max.get(position));
        holder.Min.setText(Min.get(position));
        holder.Wind.setText(Wind.get(position));
        holder.Deg.setText(Degre.get(position));

       // Log.d("log",""+descri);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk");
        final int time = Integer.parseInt(simpleDateFormat.format(calendar.getTimeInMillis()));


        if (time >= 7 && time < 18) {

            if (descri.get(position).contains("Clouds")) {
                holder.imageView.setImageResource(R.drawable.clouds);
            } else if (descri.get(position).contains("Rain")) {
                holder.imageView.setImageResource(R.drawable.rain);
            } else if (descri.get(position).contains("Snow")) {
                holder.imageView.setImageResource(R.drawable.rainsnow);
            } else if (descri.get(position).contains("Clear")) {
                holder.imageView.setImageResource(R.drawable.sunny);
            }

        }else {

            if (descri.get(position).contains("Clouds")) {
                holder.imageView.setImageResource(R.drawable.mostlyclear);
            } else if (descri.get(position).contains("Rain")) {
                holder.imageView.setImageResource(R.drawable.rain);
            } else if (descri.get(position).contains("Snow")) {
                holder.imageView.setImageResource(R.drawable.rainsnow);
            } else if (descri.get(position).contains("Clear")) {
                holder.imageView.setImageResource(R.drawable.clear);
            }
        }

        Log.d("123", "....." + position);


        return convertView;
    }

    public class ViewHolder {
        TextView Temp;
        TextView Date;
        TextView Descri;
        ImageView imageView;
        TextView Max;
        TextView Min;
        TextView Wind;
        TextView Deg ;
    }
}
