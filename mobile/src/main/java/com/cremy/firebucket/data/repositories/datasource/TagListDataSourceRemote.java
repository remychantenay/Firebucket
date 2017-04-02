package com.cremy.firebucket.data.repositories.datasource;

import com.cremy.firebucket.data.entities.TagListEntity;
import com.cremy.firebucket.firebase.RxFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by remychantenay on 24/02/2017.
 */

public class TagListDataSourceRemote extends BaseFirebaseDataSource {

    /**
     * The target node for a given service
     */
    private DatabaseReference childReference = null;

    private FirebaseDatabase firebaseDatabase;

    @Inject
    public TagListDataSourceRemote(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public DatabaseReference getChildReference() {
        if (childReference == null) {
            childReference = this.firebaseDatabase.
                    getReference()
                    .child(FIREBASE_CHILD_KEY_TAG_LIST);
        }

        return childReference;
    }

    /**
     * Allows to get the list of {@link TagListEntity}
     * @return
     */
    public Observable<TagListEntity> getTagList() {
        return RxFirebase.getObservableForSingleEvent(getChildReference(), TagListEntity.class);
    }
}
