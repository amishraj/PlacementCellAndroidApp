package com.example.hp.homepage;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class companyAdapter extends FirestoreRecyclerAdapter<Company, companyAdapter.companyHolder> {

    public static final int NO_POSITION= -1;
    Dialog mydialog;
    DocumentSnapshot documentSnapshot;
    private OnItemClickListener listener;

    public companyAdapter(@NonNull FirestoreRecyclerOptions<Company> options, OnItemClickListener onItemClickListener) {
        super(options);
        this.listener= onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull companyHolder holder, int position, @NonNull Company model) {
        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescription.setText(model.getDescription());
        holder.textViewMincg.setText(String.valueOf(model.getMincg()));
    }

    @NonNull
    @Override
    public companyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listlayout,
                parent, false);

        companyHolder vHolder= new companyHolder(v, listener);

        return vHolder;
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class companyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout item_company;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewMincg;
        OnItemClickListener onItemClickListener;

        public companyHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textViewTitle= itemView.findViewById(R.id.textTitle);
            textViewDescription= itemView.findViewById(R.id.textViewDesc);
            textViewMincg= itemView.findViewById(R.id.textMincg);

            item_company= (LinearLayout) itemView.findViewById(R.id.comapany_list);

            this.onItemClickListener= onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            if(position!= RecyclerView.NO_POSITION && listener!=null){
                onItemClickListener.OnItemCLick(getSnapshots().getSnapshot(position), position);
            }

        }
    }

    public interface OnItemClickListener{
        void OnItemCLick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
