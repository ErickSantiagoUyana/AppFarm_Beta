package com.example.kappgranja.ui.management;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kappgranja.R;


import java.util.ArrayList;

public class CowListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    //Changes this
    private ArrayList<Cow> AnimalList;

    public CowListAdapter(Context context, int layout, ArrayList<Cow> AnimalList) {
        this.context = context;
        this.layout = layout;
        this.AnimalList = AnimalList;
    }

    @Override
    public int getCount() {
        return AnimalList.size();
    }

    @Override
    public Object getItem(int position) {
        return AnimalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtYear;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.name_animal);
            holder.txtYear = (TextView) row.findViewById(R.id.year_animal);
            holder.imageView = (ImageView) row.findViewById(R.id.image_animal);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Cow cow = AnimalList.get(position);

        holder.txtName.setText(cow.getName());
        holder.txtYear.setText(cow.getPrice());

        byte[] foodImage = cow.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
