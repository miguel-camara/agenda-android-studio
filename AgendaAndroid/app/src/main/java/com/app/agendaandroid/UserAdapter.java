package com.app.agendaandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.agendaandroid.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<User> userList;

    public UserAdapter(List<User> userList) { this.userList = userList; }

    public void setQuoteList( List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quoteRow = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.user_row, viewGroup, false );
        return new MyViewHolder( quoteRow );
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int  i ) {

        User user = userList.get( i );

        String name = user.getName();
        String category = user.getCategory();
        String date = user.getDate();
        String hour = user.getHour();

        myViewHolder.name.setText( name );
        myViewHolder.category.setText( category );
        myViewHolder.date.setText( date );
        myViewHolder.hour.setText( hour );
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, date, hour;

        MyViewHolder( View itemView ) {
            super(itemView);

            this.name = itemView.findViewById( R.id.txtViewName );
            this.category = itemView.findViewById( R.id.txtViewCategory );
            this.date = itemView.findViewById( R.id.txtViewDate );
            this.hour = itemView.findViewById( R.id.txtViewHour );
        }
    }
}