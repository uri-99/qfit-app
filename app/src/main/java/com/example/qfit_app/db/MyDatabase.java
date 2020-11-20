package com.example.qfit_app.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.qfit_app.db.dao.SportDao;
import com.example.qfit_app.db.entity.SportEntity;


@Database(entities = {SportEntity.class }, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    abstract public SportDao sportDao();
}