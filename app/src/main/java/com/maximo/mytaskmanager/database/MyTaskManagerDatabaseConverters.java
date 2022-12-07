package com.maximo.mytaskmanager.database;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

public class MyTaskManagerDatabaseConverters {

    @TypeConverter
    public static Date fromTimeStamp(Long dateValue){
        return dateValue == null ? null : new Date(dateValue);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {return date == null ? null : date.getTime();}
}
