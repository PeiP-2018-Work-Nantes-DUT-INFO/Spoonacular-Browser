package com.simoncorp.spoonacular_browser;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simoncorp.spoonacular_browser.IngredientItemFragment.OnListFragmentInteractionListener;
import com.simoncorp.spoonacular_browser.viewmodel.StringItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link StringItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyIngredientItemRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientItemRecyclerViewAdapter.ViewHolder> {

    private final List<StringItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyIngredientItemRecyclerViewAdapter(List<StringItem> items,
                                               OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        String details = mValues.get(position).details;
        if(details == null || details.isEmpty()) {
            holder.mDetailsView.setVisibility(View.GONE);
        } else {
            holder.mDetailsView.setText(mValues.get(position).details);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetailsView;
        public StringItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDetailsView = (TextView) view.findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
