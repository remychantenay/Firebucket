package com.cremy.firebucket.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cremy.firebucket.R;
import com.cremy.firebucket.databinding.ItemTaskBinding;
import com.cremy.firebucket.databinding.ItemTaskHeaderBinding;
import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.presentation.ui.viewmodels.TaskHeaderViewModel;
import com.cremy.firebucket.presentation.ui.viewmodels.TaskViewModel;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.Calendar;
import java.util.List;

/**
 * Created by remychantenay on 08/05/2016.
 */
public final class TaskWithHeadersAdapter extends RecyclerView.Adapter<TaskWithHeadersAdapter.BindingHolder>
        implements StickyRecyclerHeadersAdapter<TaskWithHeadersAdapter.BindingHolder> {

    private final static int TYPE_DEFAULT = 0;

    private Context context;
    private List<TaskModel> model;

    public TaskWithHeadersAdapter(Context context,
                                  List<TaskModel> model) {
        this.context = context;
        this.model = model;
    }

    /**
     * Allows to set new items
     * @param model
     */
    public void setItems(List<TaskModel> model) {
        this.model = model;
        notifyDataSetChanged();
    }

    /**
     * Allows to add MULTIPLE items
     * @param model
     */
    public void addItems(List<TaskModel> model) {
        this.model.addAll(model);
        notifyDataSetChanged();
    }

    /**
     * Allows to delete an item with a given item index
     * @param index
     */
    public void deleteItem(final int index) {
        this.model.remove(index);
        notifyItemRemoved(index);
    }

    /**
     * Allows to get the item with a given adapter position
     * @param position
     * @return
     */
    public TaskModel getItem(int position) {
        return this.model.get(position);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: Complete if other types are needed
/*        if (viewType == TYPE_DEFAULT) {
        ItemTaskBinding itemTaskBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_task,
                parent,
                false);
        return new BindingHolder(itemTaskBinding);
        }*/

        ItemTaskBinding itemTaskBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_task,
                parent,
                false);
        return new BindingHolder(itemTaskBinding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final int viewType = getItemViewType(position);

        ItemTaskBinding itemTaskBinding = (ItemTaskBinding) holder.binding;
        itemTaskBinding.setViewModel(new TaskViewModel(this.context, this.model.get(position)));
    }

    /**
     * We use the transaction dates as header id's as Wirecard doesn't provide anything better.
     * @param position
     * @return
     */
    @Override
    public long getHeaderId(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(getItem(position).getDeadlineMs()));
        if (calendar == null) {
            return 0;
        }
        final long idHeader = calendar.get(Calendar.DAY_OF_YEAR)+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR);
        return idHeader;
    }

    @Override
    public BindingHolder onCreateHeaderViewHolder(ViewGroup parent) {
        ItemTaskHeaderBinding itemTaskHeaderBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_task_header,
                parent,
                false);
        return new BindingHolder(itemTaskHeaderBinding);
    }

    @Override
    public void onBindHeaderViewHolder(BindingHolder holder, int position) {
        /* IMPORTANT: The code below is meant to be temporary
        * Here, we should use the code above (android data binding) but the StickyHeader
        * library is not compatible yet, so we manually bind the data */
        TaskHeaderViewModel taskHeaderViewModel = new TaskHeaderViewModel(model.get(position));
        TextView textView = (TextView) holder.itemView.findViewById(R.id.textview_header);
        final String displayedDate = taskHeaderViewModel.getDisplayedDate(context);
        textView.setText(displayedDate);
        if (!displayedDate.equals(context.getResources().getString(R.string.today))
            && !displayedDate.equals(context.getResources().getString(R.string.tomorrow))) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.grey_400));
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return this.model.size();
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_DEFAULT; // Default value
    }



    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(ItemTaskHeaderBinding binding) {
            super(binding.item);
            this.binding = binding;
        }

        public BindingHolder(ItemTaskBinding binding) {
            super(binding.item);
            this.binding = binding;
        }
    }
}