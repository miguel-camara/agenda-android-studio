package com.app.agendaandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.agendaandroid.controller.FavoriteController;
import com.app.agendaandroid.model.Event;
import com.app.agendaandroid.model.Favorite;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;
    private final Context context;
    private final FavoriteController favoriteController;
    private final Long id_user;

    public EventAdapter(List<Event> eventList, Context context, Long id_user) {
        this.eventList = eventList;
        this.context = context;
        favoriteController = new FavoriteController( context );
        this.id_user = id_user;
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

        myViewHolder.ivFavorite.setImageResource( favoriteController.isFavorite( new Favorite(id_user, event.getId() ) ) ? R.drawable.ic_baseline_favorite :  R.drawable.ic_baseline_favorite_border );

        myViewHolder.ivFavorite.setOnClickListener( view ->  {

            if ( favoriteController.isFavorite( new Favorite(id_user, event.getId() ) )) {
                myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_border );
                favoriteController.deleteFavorite(  favoriteController.getFavorite( new Favorite(id_user, event.getId() ) ) );
            } else {
                myViewHolder.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite );
                favoriteController.createFavorite( new Favorite(id_user, event.getId() ) );
            }

        } );

        myViewHolder.ivEdit.setOnClickListener( view ->  {
            Event editEvent = eventList.get(i);

            Intent intent = new Intent( context, EditUserActivity.class );
            intent.putExtra("id", editEvent.getId() );
            intent.putExtra( "category", editEvent.getCategory() );
            intent.putExtra( "date", editEvent.getDate() );
            intent.putExtra( "hour", editEvent.getHour() );

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