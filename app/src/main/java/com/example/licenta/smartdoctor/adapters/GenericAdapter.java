package com.example.licenta.smartdoctor.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class GenericAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected List<T> mList;
    protected View.OnClickListener mListener;

    public GenericAdapter(Context context, List<T> list, View.OnClickListener listener) {
        mContext = context;
        mList = list;
        mListener = listener;
    }

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);
    public abstract void onBindData(RecyclerView.ViewHolder holder, T dataModel);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = setViewHolder(viewGroup);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        onBindData(holder, mList.get(i));
    }

    public void addItems(List<T> items) {
        mList = items;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
