package com.app.cita_sqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cita_sqlite.model.Quote;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {

    private List<Quote> quoteList;

    public QuoteAdapter( List<Quote> quoteList ) { this.quoteList = quoteList; }

    public void setQuoteList( List<Quote> quoteList ) {
        this.quoteList = quoteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View quoteRow = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.quote_row, viewGroup, false );
        return new MyViewHolder( quoteRow );
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int  i ) {

        Quote quote = quoteList.get( i );

        String name = quote.getName();
        String category = quote.getCategory();
        String date = quote.getDate();
        String hour = quote.getHour();

        myViewHolder.name.setText( name );
        myViewHolder.category.setText( category );
        myViewHolder.date.setText( date );
        myViewHolder.hour.setText( hour );
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
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