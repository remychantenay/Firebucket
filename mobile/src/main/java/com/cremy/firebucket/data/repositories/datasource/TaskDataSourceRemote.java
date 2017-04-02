package com.cremy.firebucket.data.repositories.datasource;

import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.firebase.RxFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by remychantenay on 24/02/2017.
 */

public class TaskDataSourceRemote extends BaseFirebaseDataSource {

    /**
     * The target node for a given service
     */
    private DatabaseReference childReference = null;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;

    @Inject
    public TaskDataSourceRemote(FirebaseAuth firebaseAuth, FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
    }

    public DatabaseReference getChildReference() {
        if (this.childReference==null) {
            this.childReference = this.firebaseDatabase.
                    getReference()
                    .child(FIREBASE_CHILD_KEY_USERS)
                    .child(this.firebaseAuth.getCurrentUser().getUid())
                    .child(FIREBASE_CHILD_KEY_TASKS);
        }

        return childReference;
    }

    /**
     * Allows to create a given {@link TaskEntity}
     * @return
     */
    public Observable createTask(final TaskEntity taskEntity) {

        // 1. We push and get the child key
        final String key = this.getChildReference()
                .push().getKey();

        // 2. We add the key as id within the model as well
        taskEntity.setId(key);

        // 3. We now set the new task
        return RxFirebase.getObservable(getChildReference().child(key).setValue(taskEntity), taskEntity);
    }

    /**
     * Allows to remove a given {@link TaskEntity}
     * @return
     */
    public Observable<TaskEntity> deleteTask(final TaskEntity taskEntity) {
        this.getChildReference()
                .child(taskEntity.getId())
                .removeValue();

        return Observable.defer(new Callable<ObservableSource<TaskEntity>>() {
            @Override
            public ObservableSource<TaskEntity> call() throws Exception {
                return Observable.just(taskEntity);
            };
        });
    }
}
