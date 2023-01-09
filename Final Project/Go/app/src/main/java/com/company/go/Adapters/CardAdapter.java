package com.company.go.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.company.go.Activities.DetailCar;
import com.company.go.Models.Car;
import com.company.go.Models.Review;
import com.company.go.R;
import com.company.go.Utils.PicassoTrustAll;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends ArrayAdapter<Car> {
    Context context;
    List<Car> cars;

    public CardAdapter(@NonNull Context context, int resource, List<Car> carList) {
        super(context, resource, carList);

        this.context = context;
        this.cars = carList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_card, null);

        Car car = cars.get(position);
        Button goBtn = view.findViewById(R.id.go_btn);
        TextView address = view.findViewById(R.id.address);
        TextView model = view.findViewById(R.id.model);
        TextView brand = view.findViewById(R.id.brand);
        TextView price = view.findViewById(R.id.price);
        TextView type = view.findViewById(R.id.type);
        TextView capacity = view.findViewById(R.id.capacity);
        TextView fuelType = view.findViewById(R.id.fuel_type);
        TextView color = view.findViewById(R.id.color);
        ImageView imageView = view.findViewById(R.id.carImage);

        address.setText(car.getLocation().get("name").toString());
        price.setText(car.getPriceFormat());
        type.setText(car.getInformation().get("type").toString());
        model.setText(car.getInformation().get("model").toString());
        brand.setText(car.getInformation().get("brand").toString());
        capacity.setText(car.getInformation().get("capacity").toString() + " Seats");
        fuelType.setText(car.getInformation().get("fuel_type").toString());
        color.setText(car.getInformation().get("color").toString());
        Picasso.get().load(car.getAvatar()).fit().into(imageView);

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailCar.class);
                intent.putExtra("id", car.getId());
                context.startActivity(intent);
            }
        });

        return (view);
    }
}
