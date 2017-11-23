package baking.amr.www.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by amr on 11/15/17.
 */

public class itemclick {
    private final RecyclerView recyclerView;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                clickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                return longClickListener.onItemLongClicked(recyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (clickListener != null) {
                view.setOnClickListener(viewClickListener);
            }
            if (longClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private itemclick(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setTag(R.id.itemclick, this);
        this.recyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static itemclick addTo(RecyclerView view) {
        itemclick support = (itemclick) view.getTag(R.id.itemclick);
        if (support == null) {
            support = new itemclick(view);
        }
        return support;
    }

    public static itemclick removeFrom(RecyclerView view) {
        itemclick support = (itemclick) view.getTag(R.id.itemclick);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public itemclick setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
        return this;
    }

    public itemclick setOnItemLongClickListener(OnItemLongClickListener listener) {
        longClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.itemclick, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
