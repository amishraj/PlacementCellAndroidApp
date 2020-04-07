package com.example.hp.homepage;

import android.app.Dialog;
import android.media.AsyncPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class AppliedAdapter extends FirestoreRecyclerAdapter<applied, AppliedAdapter.AppliedHolder> {

    public static final int NO_POSITION= -1;
    Dialog mydialog;
    DocumentSnapshot documentSnapshot;
    private OnItemClickListener listener;

    public AppliedAdapter(@NonNull FirestoreRecyclerOptions<applied> options, OnItemClickListener onItemClickListener){
        super(options);
        this.listener= onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull AppliedHolder holder, int position, @NonNull applied model) {
        holder.textviewname.setText(model.getName());
        holder.textviewcgpa.setText(model.getCGPA());
        holder.textviewid.setText(model.getCollegeID());
        holder.textviewbranch.setText(model.getBranch());
    }

    @NonNull
    @Override
    public AppliedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.appliedlistlayout,
                parent,false);

        AppliedHolder vHolder= new AppliedHolder(v, listener);


        return vHolder;
    }

    class AppliedHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textviewname;
        TextView textviewcgpa;
        TextView textviewid;
        TextView textviewbranch;
        OnItemClickListener onItemClickListener;


        public AppliedHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            textviewname= itemView.findViewById(R.id.applied_name);
            textviewcgpa= itemView.findViewById(R.id.applied_cg);
            textviewbranch= itemView.findViewById(R.id.applied_branch);

            textviewid= itemView.findViewById(R.id.applied_id);

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

    public void setOnItemClickListener(AppliedAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

}
