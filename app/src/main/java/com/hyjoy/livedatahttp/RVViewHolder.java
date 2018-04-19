package com.hyjoy.livedatahttp;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 通用的ViewHolder
 * Created by hychou on 16/5/10.
 */
public class RVViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public RVViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        itemView.setTag(R.id.rv_holder_item_id, this); // fix databinding tag问题
    }

    public RVViewHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        mViews = new SparseArray<>();
        itemView.setTag(R.id.rv_holder_item_id, this);
        setOnItemClickListener(listener);
    }


    /**
     * 通过view的资源ID获取视图控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    public RVViewHolder setTextColor(int resId, int color) {
        TextView view = getView(resId);
        if (view != null) {
            view.setTextColor(color);
        }
        return this;
    }

    public RVViewHolder setText(int resId, String text) {
        TextView view = getView(resId);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    public RVViewHolder setText(int resId, SpannableStringBuilder builder) {
        TextView view = getView(resId);
        if (view != null) {
            view.setText(builder);
        }
        return this;
    }

    public RVViewHolder setImageResource(int resId, int drawableId) {
        ((ImageView) getView(resId)).setImageResource(drawableId);
        return this;
    }


    /**
     * @param resId
     * @param isChecked
     * @return
     */
    public RVViewHolder setChecked(int resId, boolean isChecked) {
        CheckBox checkBox = getView(resId);
        if (checkBox != null) {
            checkBox.setChecked(isChecked);
        }
        return this;
    }

    public RVViewHolder setSelected(int resId, boolean isSelected) {
        View view = getView(resId);
        if (view != null) {
            view.setSelected(isSelected);
        }
        return this;
    }

    /**
     * @param resId
     * @param isEnable
     * @return
     */
    public RVViewHolder setEnable(int resId, boolean isEnable) {
        View view = getView(resId);
        if (view != null) {
            view.setEnabled(isEnable);
        }
        return this;
    }

    public RVViewHolder setBackgroundColor(int resId, int color) {
        View view = getView(resId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public RVViewHolder setVisibility(int resId, int visibility) {
        View view = getView(resId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public RVViewHolder setOnChildViewClickListener(int resId, final OnItemClickListener listener) {
        final View view = getView(resId);
        if (listener != null && view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RVViewHolder holder = (RVViewHolder) itemView.getTag(R.id.rv_holder_item_id);
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
        return this;
    }

    public RVViewHolder setOnItemClickListener(final OnItemClickListener listener) {
        if (listener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RVViewHolder holder = (RVViewHolder) itemView.getTag(R.id.rv_holder_item_id);
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
        return this;
    }

    public RVViewHolder setOnItemLongClickListener(final OnItemLongClickListener listener) {
        if (listener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RVViewHolder holder = (RVViewHolder) itemView.getTag(R.id.rv_holder_item_id);
                    listener.onItemLongClick(v, holder.getAdapterPosition());
                    return true;
                }
            });
        }
        return this;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
