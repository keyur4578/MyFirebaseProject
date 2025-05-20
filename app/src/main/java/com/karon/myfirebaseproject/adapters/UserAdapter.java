package com.karon.myfirebaseproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.karon.myfirebaseproject.R;
import com.karon.myfirebaseproject.models.User;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> users;
    OnUserClickListner listner;

    public interface OnUserClickListner
    {
        void onDeleteButtonClick(User obj);
        void onEditButtonClick(User obj);
    }

    public UserAdapter(Context context, ArrayList<User> users,OnUserClickListner listner)
    {
        this.context = context;
        this.users = users;
        this.listner = listner;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.single_user,null);
        TextView lblName = (TextView) view.findViewById(R.id.lblName);
        TextView lblEmail = (TextView) view.findViewById(R.id.lblEmail);
        TextView lblMobile = (TextView) view.findViewById(R.id.lblMobile);
        TextView lblAbout = (TextView) view.findViewById(R.id.lblAbout);
        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        Button btnEdit = (Button) view.findViewById(R.id.btnEdit);

        User obj = users.get(i);

        lblName.setText(obj.getFirstname());
        lblEmail.setText(obj.getEmail());
        lblMobile.setText(obj.getEmail());
        lblAbout.setText(obj.getAboutme());

        btnDelete.setOnClickListener(v->listner.onDeleteButtonClick(obj));
        btnEdit.setOnClickListener(v->listner.onEditButtonClick(obj));

        return view;
    }
}
