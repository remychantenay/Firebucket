package com.cremy.firebucket.presentation.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cremy.firebucket.App;
import com.cremy.firebucket.R;
import com.cremy.firebucket.di.task.DaggerTaskComponent;
import com.cremy.firebucket.di.task.TaskComponent;
import com.cremy.firebucket.di.task.TaskModule;
import com.cremy.firebucket.domain.models.TaskPriorityModel;
import com.cremy.firebucket.presentation.presenters.CreateTaskMVP;
import com.cremy.firebucket.presentation.presenters.impl.CreateTaskPresenter;
import com.cremy.firebucket.presentation.ui.base.BaseFragment;
import com.cremy.firebucket.utils.CustomDateUtils;
import com.cremy.greenrobotutils.library.permission.PermissionHelper;
import com.cremy.greenrobotutils.library.ui.SnackBarUtils;
import com.cremy.greenrobotutils.library.util.NetworkUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTaskFragment#newInstance()} factory method to
 * create an instance of this fragment.
 */
public class CreateTaskFragment extends BaseFragment
        implements CreateTaskMVP.View, CompoundButton.OnCheckedChangeListener, TimePickerDialog.OnTimeSetListener {

    public static final String TAG = CreateTaskFragment.class.getName();

    private SpeechRecognizer speechRecognizer;

    private Calendar calendar = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;
    private int idPriority = TaskPriorityModel.PRIORITY_NORMAL_ID;

    private int reminderHourOfDay;
    private int reminderMinute;

    @Inject
    CreateTaskPresenter presenter;
    TaskComponent component;

    @BindView(R.id.root_view)
    CoordinatorLayout rootView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.appbar)
    AppBarLayout appBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_input_layout_title)
    TextInputLayout textInputLayoutTitle;
    @BindView(R.id.cta_speech)
    ImageView ctaSpeech;
    @BindView(R.id.cta_create)
    FloatingActionButton ctaCreate;
    @BindView(R.id.container_item_deadline)
    FrameLayout containerItemDeadline;
    @BindView(R.id.container_item_priority)
    FrameLayout containerItemPriority;
    @BindView(R.id.container_item_tags)
    FrameLayout containerItemTags;
    @BindView(R.id.textview_item_deadline_subtitle)
    TextView textViewItemDeadlineSubtitle;
    @BindView(R.id.textview_item_priority_subtitle)
    TextView textViewItemPrioritySubtitle;
    @BindView(R.id.textview_item_tags_subtitle)
    TextView textViewItemTagsSubtitle;

    @BindView(R.id.imageview_item_reminder_icon)
    ImageView imageviewItemReminderIcon;
    @BindView(R.id.textview_item_reminder_title)
    TextView textviewItemReminderTitle;
    @BindView(R.id.textview_item_reminder_subtitle)
    TextView textviewItemReminderSubtitle;
    @BindView(R.id.switch_item_reminder)
    SwitchCompat switchItemReminder;

    @OnClick(R.id.cta_speech)
    public void clickCtaSpeech() {
        if (PermissionHelper.isRecordAudioPermissionGranted(getContext())) {
            startVoiceRecognition();
        }
        else {
            PermissionHelper.requestRecordAudioPermission(getActivity());
        }
    }

    @OnClick(R.id.cta_create)
    public void clickCtaCreate() {
        this.presenter.createTask(textInputLayoutTitle.getEditText().getText().toString().trim(),
                calendar,
                textViewItemTagsSubtitle.getText().toString().trim(),
                idPriority);
    }

    @OnClick(R.id.container_item_deadline)
    public void clickContainerItemDeadline() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), this, startYear, startMonth, startDay);
        dialog.show();
    }

    @OnClick(R.id.container_item_reminder)
    public void clickContainerItemReminder() {
        if (switchItemReminder.isChecked()) {
            TimePickerDialog dialog = new TimePickerDialog(getContext(), this, reminderHourOfDay, reminderMinute, true);
            dialog.show();
        }
    }

    @SuppressWarnings("ResourceType")
    @OnClick(R.id.container_item_priority)
    public void clickContainerItemPriority() {
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext(), 5);
        ad.setTitle(getResources().getString(R.string.create_task_option_item_priority_title));
        ad.setCancelable(true);
        ad.setItems(R.array.task_priority_labels, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                idPriority = index;
                textViewItemPrioritySubtitle.setText(TaskPriorityModel.getResourceLabel(getContext(), idPriority));
            }
        });
        ad.show();
    }
    @OnClick(R.id.container_item_tags)
    public void clickContainerItemTags() {
        if (NetworkUtils.isNetworkEnabled(getContext())) {
            this.showLoading();
            this.presenter.getTagList();
        } else {
            this.showNoNetwork();
        }
    }


    @Override
    public void injectDependencies() {
        if (component == null) {
            component = DaggerTaskComponent.builder()
                    .appComponent(App.get(getContext()).getComponent())
                    .taskModule(new TaskModule(this))
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

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TransactionsFragment.
     */
    public static CreateTaskFragment newInstance() {
        CreateTaskFragment fragment = new CreateTaskFragment();
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
        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDeadlineDatePicker();
        initReminderTimePicker();
        textViewItemPrioritySubtitle.setText(TaskPriorityModel.getResourceLabel(getContext(), idPriority));

        switchItemReminder.setOnCheckedChangeListener(this);
        setReminderDisabled();
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
        SnackBarUtils.showSimpleSnackbar(this.rootView,
                getResources().getString(R.string.no_network));
    }

    @Override
    public void onSuccess() {
        getActivity().finish();
    }

    @Override
    public void onFailure() {
        showMessage(getResources().getString(R.string.error_create_task));
    }

    @Override
    public void showMessageInvalidTaskTitle() {
        showMessage(getResources().getString(R.string.error_create_task_invalid_title));
    }

    @SuppressWarnings("ResourceType")
    @Override
    public void showTagList(final String[] tags) {
        AlertDialog.Builder ad = new AlertDialog.Builder(getContext(), 5);
        ad.setTitle(getResources().getString(R.string.create_task_option_item_tags_title));
        ad.setCancelable(true);
        ad.setItems(tags, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                textViewItemTagsSubtitle.setText(tags[index]);
            }
        });
        ad.show();
    }

    @Override
    public void showTagListError() {
        showMessage(getString(R.string.error_create_task_get_tags));
    }

    @Override
    public void initDeadlineDatePicker() {
        this.startYear = calendar.get(Calendar.YEAR);
        this.startMonth = calendar.get(Calendar.MONTH);
        this.startDay = calendar.get(Calendar.DAY_OF_MONTH);

        final String displayedDeadline = CustomDateUtils.getDisplayDate(getContext(), calendar);
        this.textViewItemDeadlineSubtitle.setText(displayedDeadline);
    }

    @Override
    public void initReminderTimePicker() {
        this.reminderHourOfDay = 8;
        this.reminderMinute = 0;

        this.textviewItemReminderSubtitle.setText(CustomDateUtils.getDisplayTime(reminderHourOfDay,
                reminderMinute));
    }

    @Override
    public void startVoiceRecognition() {

        // We start the speech recognizer intent
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizer.setRecognitionListener(this);

        String deviceLocale = getResources().getConfiguration().locale.toString();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, deviceLocale);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        speechRecognizer.startListening(intent);
    }

    @Override
    public boolean isReminderSet() {
        return switchItemReminder.isChecked();
    }

    @Override
    public Date getReminderDate() {
        return calendar.getTime();
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // We start to animate the button
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5F, 1.2F, 0.5F, 1.2F, 1, 0.5F, 1, 0.5F);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
        scaleAnimation.setRepeatCount(10);
        ctaSpeech.startAnimation(scaleAnimation);
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        ctaSpeech.clearAnimation();
    }

    @Override
    public void onError(int error) {
        if (error != SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
            showMessage(getResources().getString(R.string.error_create_task_voice_recognition_error_code));
        }
    }

    @Override
    public void onResults(Bundle results) {
        Log.d(TAG, "onResults " + results);
        ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (data != null && data.size() > 0) {
            Log.e(TAG, "Result: "+data.get(0));
            textInputLayoutTitle.getEditText().setText(data.get(0));
        }
        else {
            showMessage(getResources().getString(R.string.error_create_task_voice_recognition));
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        if (!CustomDateUtils.isBeforeToday(calendar)) {
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;

            calendar.set(startYear, startMonth, startDay);

            final String displayedDeadline = CustomDateUtils.getDisplayDate(getContext(), calendar);
            textViewItemDeadlineSubtitle.setText(displayedDeadline);
        } else {
            this.showMessage(getResources().getString(R.string.error_create_task_date_picking_too_early));
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        reminderHourOfDay = hour;
        reminderMinute = minute;

        calendar.set(startYear, startMonth, startDay);
        calendar.set(Calendar.HOUR_OF_DAY, reminderHourOfDay);
        calendar.set(Calendar.MINUTE, reminderMinute);
        textviewItemReminderSubtitle.setText(CustomDateUtils.getDisplayTime(hour, minute));
    }

    private void setReminderEnabled() {
        imageviewItemReminderIcon.setAlpha(1f);
        textviewItemReminderTitle.setAlpha(1f);
        textviewItemReminderSubtitle.setAlpha(1f);
    }

    private void setReminderDisabled() {
        imageviewItemReminderIcon.setAlpha(0.2f);
        textviewItemReminderTitle.setAlpha(0.2f);
        textviewItemReminderSubtitle.setAlpha(0.2f);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            setReminderEnabled();
        } else {
            setReminderDisabled();
        }
    }
}