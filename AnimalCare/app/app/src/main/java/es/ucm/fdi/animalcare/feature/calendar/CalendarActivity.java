package es.ucm.fdi.animalcare.feature.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.ucm.fdi.animalcare.R;
import es.ucm.fdi.animalcare.base.BaseActivity;
import es.ucm.fdi.animalcare.data.Task;
import es.ucm.fdi.animalcare.data.App;
import es.ucm.fdi.animalcare.data.User;
import es.ucm.fdi.animalcare.feature.pets.PetsActivity;
import es.ucm.fdi.animalcare.feature.settings.SettingsActivity;
import es.ucm.fdi.animalcare.feature.toolbar.ToolBarManagement;
import es.ucm.fdi.animalcare.feature.upcoming.UpcomingActivity;
import es.ucm.fdi.animalcare.feature.user.UserActivity;

public class CalendarActivity extends BaseActivity implements CalendarView, ToolBarManagement {
    private int TASK_LOADER_ID=50;
    private User user;
    private CalendarPresenter mCalendarPresenter;
    private List<EventDay> events = new ArrayList<>();
    private Calendar mCalendar;
    private com.applandeo.materialcalendarview.CalendarView mCalendarView;
    private RecyclerView mRecyclerView;
    private TaskLoaderCallbacks mTaskLoaderCallbacks = new TaskLoaderCallbacks(this, this);
    private TasksListAdapter mAdapter;
    private List<Task> mTasks= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        bindViews();
        setUpToolbar();

        user = (User) getIntent().getSerializableExtra("user");

        mCalendarPresenter = new CalendarPresenter(this);

        mCalendar = Calendar.getInstance();

        mCalendarView = findViewById(R.id.calendarView);
        mCalendarPresenter.prepareEvents(user.getmId());

        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new TasksListAdapter(this, mTasks);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if(loaderManager.getLoader(TASK_LOADER_ID) != null){
            loaderManager.initLoader(TASK_LOADER_ID,null, mTaskLoaderCallbacks);
        }

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar calendar = eventDay.getCalendar();
                searchEventsOfDay(calendar);
            }
        });
    }

    @Override
    public void setEvents(List<EventDay> events){
        mCalendarView.setEvents(events);
    }

    public void searchEventsOfDay(Calendar calendar){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Bundle queryBundle = new Bundle();
        queryBundle.putString(TaskLoaderCallbacks.EXTRA_DATETIMESTRING, dateFormat.format(calendar.getTime()));
        queryBundle.putInt(TaskLoaderCallbacks.EXTRA_USERID, user.getmId());
        LoaderManager.getInstance(this)
                .restartLoader(TASK_LOADER_ID, queryBundle, mTaskLoaderCallbacks);
    }

    public void updateTasksList(List<Task> data){
        mAdapter.setTasksData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void launchFromToolbar(View view) {
        if(view.getId() != R.id.button_toolbar_calendar){
            Intent intent;
            switch (view.getId()){
                case R.id.button_toolbar_upcoming:
                    intent = new Intent(this, UpcomingActivity.class);
                    break;
                case R.id.button_toolbar_user:
                    intent = new Intent(this, UserActivity.class);
                    break;
                case R.id.button_toolbar_pets:
                    intent = new Intent(this, PetsActivity.class);
                    break;
                default:
                    intent = new Intent(this, SettingsActivity.class);
                    break;

            }
            intent.putExtra("user", user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void setUpToolbar() {
        findViewById(R.id.button_toolbar_pets).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_upcoming).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_settings).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
        findViewById(R.id.button_toolbar_calendar).getBackground().setTint(App.getApp().getResources().getColor(R.color.white));
        findViewById(R.id.button_toolbar_user).getBackground().setTint(App.getApp().getResources().getColor(R.color.iconColor));
    }
}