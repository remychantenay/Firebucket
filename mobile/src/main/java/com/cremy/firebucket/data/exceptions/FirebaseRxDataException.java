package com.cremy.firebucket.data.exceptions;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseError;

public class FirebaseRxDataException extends Exception {

    protected DatabaseError error;

    public FirebaseRxDataException(@NonNull DatabaseError error) {
        this.error = error;
    }

    public DatabaseError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "RxFirebaseDataException{" +
                "error=" + error +
                '}';
    }
}