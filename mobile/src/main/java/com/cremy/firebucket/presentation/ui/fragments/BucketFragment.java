package com.cremy.firebucket.presentation.ui.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cremy.firebucket.App;
import com.cremy.firebucket.R;
import com.cremy.firebucket.di.bucket.BucketComponent;
import com.cremy.firebucket.di.bucket.BucketModule;
import com.cremy.firebucket.di.bucket.DaggerBucketComponent;
import com.cremy.firebucket.di.user.DaggerUserComponent;
import com.cremy.firebucket.di.user.UserComponent;
import com.cremy.firebucket.di.user.UserModule;
import com.cremy.firebucket.domain.models.BucketModel;
import com.cremy.firebucket.domain.models.TaskModel;
import com.cremy.firebucket.presentation.presenters.BucketMVP;
import com.cremy.firebucket.presentation.presenters.LoginMVP;
import com.cremy.firebucket.presentation.presenters.impl.BucketPresenter;
import com.cremy.firebucket.presentation.presenters.impl.LoginPresenter;
import com.cremy.firebucket.presentation.ui.activities.CreateTaskActivity;
import com.cremy.firebucket.presentation.ui.adapters.TaskWithHeadersAdapter;
import com.cremy.firebucket.presentation.ui.base.BaseFragment;
import com.cremy.firebucket.presentation.ui.widgets.MaterialDesignFlatButton;
import com.cremy.firebucket.presentation.ui.widgets.RecyclerViewDividerDecoration;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.greenrobotutils.library.ui.recyclerview.RecyclerViewUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.mockito.asm.tree.analysis.Frame;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BucketFragment#newInstance()} factory method to
 * create an instance of this fragment.
 */
public class BucketFragment extends BaseFragment
        implements BucketMVP.View,
        View.OnClickListener {

    public static final String TAG = BucketFragment.class.getName();

    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Nullable @BindView(R.id.cta_create)
    FloatingActionButton ctaCreate;
    @Nullable @BindView(R.id.container_placeholder)
    FrameLayout containerPlaceholder;

    @OnClick(R.id.cta_create)
    public void clickCtaCreateTask() {
        CreateTaskActivity.startMe(getContext());
    }

    @Inject
    BucketPresenter presenter;
    BucketComponent component;

    TaskWithHeadersAdapter adapter;

    @Override
    public void injectDependencies() {
        if (component == null) {
            component = DaggerBucketComponent.builder()
                    .appComponent(App.get(getContext()).getComponent())
                    .bucketModule(new BucketModule(this))
                    .build();
            component.inject(this);
        }
    }

    @Override
    public void attachToPresenter() {
        this.presenter.attachView(this);
    }

    @Override
    public void detachFromPresenter() {
        this.presenter.detachView();
    }

    public BucketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TransactionsFragment.
     */
    public static BucketFragment newInstance() {
        BucketFragment fragment = new BucketFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getArgs(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bucket, container, false);
        ButterKnife.bind(this, v);
        initRecyclerView();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getBucket();
    }

    @Override
    public void onAttach(Context context) {
        this.injectDependencies();
        this.attachToPresenter();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        detachFromPresenter();
        super.onDetach();
    }

    @Override
    public void getArgs(Bundle _bundle) {
        // Empty intentionally.
    }

    @Override
    public void onLandscape() {
        // Empty intentionally.
    }

    @Override
    public void onPortrait() {
        // Empty intentionally.
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        SnackBarUtils.showSimpleSnackbar(rootView, message);
    }

    @Override
    public void showNoNetwork() {
        // Empty intentionally.
    }

    @Override
    public void showBucket(ArrayList<TaskModel> model) {
        recyclerView.setVisibility(View.VISIBLE);
        if (containerPlaceholder != null) {
            containerPlaceholder.setVisibility(View.GONE);
        }

        if (adapter != null) {
            adapter.setItems(model);
        } else {
            adapter = new TaskWithHeadersAdapter(getActivity(), model);

            recyclerView.setAdapter(adapter);
            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
            recyclerView.addItemDecoration(headersDecor);
        }
    }

    @Override
    public void showBucketEmpty() {
        prepareViewStub();

        final TextView textviewPlaceholder = (TextView) containerPlaceholder.findViewById(R.id.textview_placeholder);
        final MaterialDesignFlatButton ctaPlaceholder = (MaterialDesignFlatButton) containerPlaceholder.findViewById(R.id.cta_placeholder);
        final ImageView imageviewPlaceholder = (ImageView) containerPlaceholder.findViewById(R.id.imageview_placeholder);

        textviewPlaceholder.setText(getString(R.string.bucket_placeholder_empty_title));
        ctaPlaceholder.setText(getString(R.string.bucket_activity_placeholder_button_text));
        ctaPlaceholder.setOnClickListener(this);
        ctaPlaceholder.setVisibility(View.GONE);
        imageviewPlaceholder.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_bucket_empty));
    }

    @Override
    public void showBucketEmptyFirstTime() {
        prepareViewStub();

        final TextView textviewPlaceholder = (TextView) containerPlaceholder.findViewById(R.id.textview_placeholder);
        final MaterialDesignFlatButton ctaPlaceholder = (MaterialDesignFlatButton) containerPlaceholder.findViewById(R.id.cta_placeholder);
        final ImageView imageviewPlaceholder = (ImageView) containerPlaceholder.findViewById(R.id.imageview_placeholder);

        textviewPlaceholder.setText(getString(R.string.bucket_placeholder_empty_first_time_title));
        ctaPlaceholder.setText(getString(R.string.bucket_activity_placeholder_button_text));
        ctaPlaceholder.setVisibility(View.GONE);
        imageviewPlaceholder.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_logo_bucket));
    }

    @Override
    public void showBucketError() {
        prepareViewStub();

        final TextView textviewPlaceholder = (TextView) containerPlaceholder.findViewById(R.id.textview_placeholder);
        final MaterialDesignFlatButton ctaPlaceholder = (MaterialDesignFlatButton) containerPlaceholder.findViewById(R.id.cta_placeholder);
        final ImageView imageviewPlaceholder = (ImageView) containerPlaceholder.findViewById(R.id.imageview_placeholder);

        textviewPlaceholder.setText(getString(R.string.bucket_placeholder_error_title));
        ctaPlaceholder.setText(getString(R.string.bucket_activity_placeholder_button_text));
        ctaPlaceholder.setOnClickListener(this);
        ctaPlaceholder.setVisibility(View.VISIBLE);
        imageviewPlaceholder.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_bucket_error));
    }

    private void prepareViewStub() {
        recyclerView.setVisibility(View.GONE);

        if (containerPlaceholder == null) {
            containerPlaceholder = (FrameLayout) ((ViewStub) getView().findViewById(R.id.viewstub_bucket)).inflate();
        }
        containerPlaceholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTaskDeleted() {
        // Nothing to do here as the task has been removed from the adapter already.
    }

    @Override
    public void showTaskDeletedError(String message) {
        showMessage(message);
        presenter.getBucket();
    }

    @Override
    public void onItemSwiped(int position) {
        if (adapter != null) {
            presenter.deleteTask(adapter.getItem(position).getId());
            adapter.deleteItem(position);
            if (adapter.getItemCount() == 0) {
                showBucketEmpty();
            }
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Remove swiped item from list and notify the adapter
                onItemSwiped(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c,
                                    RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX,
                                    float dY,
                                    int actionState,
                                    boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (adapter != null) {
                        // Get RecyclerView item from the ViewHolder
                        View itemView = viewHolder.itemView;

                        // 1. We draw a rect with a filled color
                        RecyclerViewUtils.drawRectOnSwipe(itemView,
                                c,
                                ContextCompat.getColor(getContext(), R.color.green_400),
                                dX);

                        if (dX > 0) // If RIGHT swiped TODO
                        {
/*                                // 2. And and this color rectangle, draw a resource bitmap
                            RecyclerViewUtils.drawBitmap(itemView,
                                    c,
                                    getResources(),
                                    R.drawable.icon_seen_white,
                                    p);*/
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
            }
        };

        RecyclerViewUtils.setItemTouchCallback(this.recyclerView, simpleItemTouchCallback);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cta_placeholder) {
            presenter.getBucket();
        }
    }
}