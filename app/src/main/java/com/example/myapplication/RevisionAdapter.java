package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entites.Revision;

public class RevisionAdapter extends RecyclerView.Adapter<RevisionAdapter.RevisionViewHolder> {

    private final Context context;
    private final List<Revision> dataset;

    public RevisionAdapter(Context context, List<Revision> dataset) {
        this.context = context;
        this.dataset = dataset;
    }

    // ViewHolder inner class
    public static class RevisionViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button detailsButton;

        public RevisionViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.item_title);
            detailsButton = view.findViewById(R.id.button_details);
        }
    }


    @NonNull
    @Override
    public RevisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View adapterLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liste_rev_item, parent, false);
        return new RevisionViewHolder(adapterLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RevisionViewHolder holder, int position) {
        Revision revision = dataset.get(position);
        holder.textView.setText(revision.getLibelle());

        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, RevisionDetailActivity.class);
            intent.putExtra("id_revision", revision.getId());
            intent.putExtra("dateRevision", revision.getDateR());
            intent.putExtra("libelle", revision.getLibelle());
            intent.putExtra("idPersonnel", revision.getId_PERSONNEL());
            intent.putExtra("idModele", revision.getId_MODELE());
            intent.putExtra("idAvion", revision.getId_AVION());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}