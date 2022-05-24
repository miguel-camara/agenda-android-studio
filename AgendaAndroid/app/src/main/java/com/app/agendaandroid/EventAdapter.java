package com.app.agendaandroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.agendaandroid.controller.EventController;
import com.app.agendaandroid.controller.FavoriteController;
import com.app.agendaandroid.controller.UserController;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.Favorite;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;
    private List<Favorite> favoriteList;
    private Context context;
    private EventController eventController;
    private UserController userController;
    private FavoriteController favoriteController;
    private Long id;

    public EventAdapter(List<Event> eventList, Context context, Long id) {
        this.eventList = eventList;
        this.context = context;
        eventController = new EventController( context );
        userController = new UserController( context );
        favoriteController = new FavoriteController( context );
        this.id = id;
    }

    public void setEventList( List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quoteRow = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.user_row, viewGroup, false );
        return new MyViewHolder( quoteRow );
    }

    private int setImage( String category ) {

        switch (category) {
            case "Actividad Física":
                return R.drawable.ic_baseline_directions_run;
            case "Trabajo":
                return R.drawable.ic_baseline_work;
            case "Compras":
                return  R.drawable.ic_baseline_shopping_cart;
            case "Recreativo":
                return R.drawable.ic_baseline_directions_bike;
            default:
                return R.drawable.ic_baseline_event;
        }
    }


    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int  i ) {
        Event event = eventList.get( i );

        String category = event.getCategory();
        String date = event.getDate();
        String hour = event.getHour();

        myViewHolder.category.setText( category );
        myViewHolder.date.setText( date );
        myViewHolder.hour.setText( hour );
        myViewHolder.ivIcon.setImageResource( setImage( category ) );

        myViewHolder.ivFavorite.setImageResource( favoriteController.isFavorite( new Favorite( id, event.getId() ) ) ? R.drawable.ic_baseline_favorite :  R.drawable.ic_baseline_favorite_border );

        myViewHolder.ivFavorite.setOnClickListener( view ->  {

            if ( favoriteController.isFavorite( new Favorite( id, event.getId() ) )) {
                myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_border );
                favoriteController.deleteFavorite( new Favorite( id, event.getId() ) );
            } else {
                myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite );
                favoriteController.createFavorite( new Favorite( id, event.getId() ) );
            }

        } );

        myViewHolder.ivEdit.setOnClickListener( view ->  {
            Event deleteEvent = eventList.get(i);

            Intent intent = new Intent( context, EditUserActivity.class );
            intent.putExtra("id", deleteEvent.getId() );
            intent.putExtra( "category", deleteEvent.getCategory() );
            intent.putExtra( "date", deleteEvent.getDate() );
            intent.putExtra( "hour", deleteEvent.getHour() );

            context.startActivity( intent );
        } );

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView category, date, hour;
        ImageView ivFavorite, ivIcon, ivEdit;


        MyViewHolder( View itemView ) {
            super(itemView);

            this.category = itemView.findViewById( R.id.txtViewCategory );
            this.date = itemView.findViewById( R.id.txtViewDate );
            this.hour = itemView.findViewById( R.id.txtViewHour );
            this.ivFavorite = itemView.findViewById( R.id.ivFavorite );
            this.ivIcon = itemView.findViewById( R.id.ivIcon );
            this.ivEdit = itemView.findViewById( R.id.ivEdit );

        }
    }
}