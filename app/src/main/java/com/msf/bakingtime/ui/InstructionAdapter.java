package com.msf.bakingtime.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msf.bakingtime.R;
import com.msf.bakingtime.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{

    private List<Step> mInstructions;
    private OnInstructionListener mListener;

    InstructionAdapter(List<Step> listSteps, OnInstructionListener listener){
        this.mInstructions = listSteps;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction, parent, false);
        return new InstructionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionViewHolder holder, int position) {
        Step instruction = getInstruction(position);
        holder.mInstructionId.setText(String.valueOf(instruction.getId()));
        holder.mInstructionName.setText(instruction.getShortDescription());
    }

    private Step getInstruction(int position) {
        return mInstructions.get(position);
    }


    public interface OnInstructionListener {
        void onItemClick(Step step);
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    class InstructionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.instruction_id)
        TextView mInstructionId;

        @BindView(R.id.instruction_name)
        TextView mInstructionName;

        InstructionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Step instruction = getInstruction(getAdapterPosition());
            mListener.onItemClick(instruction);
        }
    }

}