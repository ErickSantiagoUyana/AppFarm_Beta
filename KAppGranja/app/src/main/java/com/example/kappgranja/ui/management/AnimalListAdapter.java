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

public class AnimalListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    //Changes this
    private ArrayList<?> AnimalList;
    private String animal;


    public AnimalListAdapter(Context context, int layout, ArrayList<?> AnimalList, String animal) {
        this.context = context;
        this.layout = layout;
        this.animal = animal;

        if(animal == "COWS")
            this.AnimalList = (ArrayList<Cow>) AnimalList;
        if(animal == "PIGS")
            this.AnimalList = (ArrayList<Pig>) AnimalList;
        if(animal == "SHEEPS")
            this.AnimalList = (ArrayList<Sheep>) AnimalList;
        if(animal == "GOATS")
            this.AnimalList = (ArrayList<Goat>) AnimalList;

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


        if(animal == "COWS") {
            Cow cow = ((Cow) AnimalList.get(position));

            holder.txtName.setText(cow.getName());
            holder.txtYear.setText(cow.getAge());

            byte[] foodImage = cow.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }
        if(animal == "PIGS") {
            Pig pig = ((Pig) AnimalList.get(position));

            holder.txtName.setText(pig.getName());
            holder.txtYear.setText(pig.getAge());

            byte[] foodImage = pig.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }
        if(animal == "SHEEPS") {
            Sheep sheep = ((Sheep) AnimalList.get(position));

            holder.txtName.setText(sheep.getName());
            holder.txtYear.setText(sheep.getAge());

            byte[] foodImage = sheep.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }
        if(animal == "GOATS") {
            Goat goat = ((Goat) AnimalList.get(position));

            holder.txtName.setText(goat.getName());
            holder.txtYear.setText(goat.getAge());

            byte[] foodImage = goat.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.imageView.setImageBitmap(bitmap);
        }

        return row;
    }
}
