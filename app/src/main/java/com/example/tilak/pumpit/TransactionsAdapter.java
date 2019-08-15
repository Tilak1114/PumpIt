package com.example.tilak.pumpit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TransactionsAdapter extends FirestoreRecyclerAdapter<Transaction, TransactionsAdapter.TransactionHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private ItemclickListener itemclickListener;

    Context context;
    public TransactionsAdapter(@NonNull FirestoreRecyclerOptions<Transaction> options, Context context, ItemclickListener itemclickListener) {
        super(options);
        this.context = context;
        this.itemclickListener = itemclickListener;
    }

    public interface ItemclickListener{
        void onItemClick(String name, String timestamp, String category,
                         String amount, String inOrOut);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransactionHolder holder, int position, @NonNull final Transaction model) {
        holder.category.setText("Category:"+model.getCategory());
        holder.title.setText(model.getTitle());
        holder.timedate.setText(model.getTimedate());

        holder.bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemclickListener.onItemClick(model.getTitle(), model.getTimedate(),
                        model.getCategory(), model.getAmount(), model.getInorout());
            }
        });

        if(model.getInorout().equals("in")){
            holder.circle.setBackgroundResource(R.drawable.circleinc);
            holder.amount.setText("+"+model.getAmount());
        }
        else if(model.getInorout().equals("out")){
            holder.circle.setBackgroundResource(R.drawable.circleexp);
            holder.amount.setText("-"+model.getAmount());
            holder.amount.setTextColor(context.getColor(android.R.color.holo_red_dark));
        }
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trans_rv_lay,
                viewGroup,false);
        return new TransactionHolder(view);
    }

    class TransactionHolder extends RecyclerView.ViewHolder {
        View topline, bottomline, circle;
        RelativeLayout bck;
        TextView timedate, title, category, amount;
        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            bck = itemView.findViewById(R.id.transbacklay);
            topline = itemView.findViewById(R.id.toplinerv);
            bottomline = itemView.findViewById(R.id.bottomlinerv);
            circle = itemView.findViewById(R.id.cshflowtype);
            timedate = itemView.findViewById(R.id.timedatetranstv);
            title = itemView.findViewById(R.id.titletranstv);
            category = itemView.findViewById(R.id.catetranstv);
            amount = itemView.findViewById(R.id.amounttrans);
        }
    }
}
