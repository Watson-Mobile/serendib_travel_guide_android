package com.watson.serendibtravelguide;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.watson.serendibtravelguide.models.SampleModel;
import com.watson.serendibtravelguide.models.SampleModelDao;

@Database(entities={SampleModel.class}, version=1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract SampleModelDao sampleModelDao();

    // Database name to be used
    public static final String NAME = "TravelDataBase";
}
