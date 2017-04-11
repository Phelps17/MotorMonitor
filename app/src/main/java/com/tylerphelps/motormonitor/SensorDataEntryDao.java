package com.tylerphelps.motormonitor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.tylerphelps.motormonitor.SensorDataEntry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SENSOR_DATA_ENTRY".
*/
public class SensorDataEntryDao extends AbstractDao<SensorDataEntry, Long> {

    public static final String TABLENAME = "SENSOR_DATA_ENTRY";

    /**
     * Properties of entity SensorDataEntry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Module_access_name = new Property(1, String.class, "module_access_name", false, "MODULE_ACCESS_NAME");
        public final static Property Date = new Property(2, java.util.Date.class, "date", false, "DATE");
        public final static Property Time = new Property(3, Double.class, "time", false, "TIME");
        public final static Property Vibration = new Property(4, Double.class, "vibration", false, "VIBRATION");
        public final static Property Temperature = new Property(5, Double.class, "temperature", false, "TEMPERATURE");
    };


    public SensorDataEntryDao(DaoConfig config) {
        super(config);
    }
    
    public SensorDataEntryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SENSOR_DATA_ENTRY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"MODULE_ACCESS_NAME\" TEXT NOT NULL ," + // 1: module_access_name
                "\"DATE\" INTEGER," + // 2: date
                "\"TIME\" REAL," + // 3: time
                "\"VIBRATION\" REAL," + // 4: vibration
                "\"TEMPERATURE\" REAL);"); // 5: temperature
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SENSOR_DATA_ENTRY\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SensorDataEntry entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getModule_access_name());
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(3, date.getTime());
        }
 
        Double time = entity.getTime();
        if (time != null) {
            stmt.bindDouble(4, time);
        }
 
        Double vibration = entity.getVibration();
        if (vibration != null) {
            stmt.bindDouble(5, vibration);
        }
 
        Double temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindDouble(6, temperature);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SensorDataEntry readEntity(Cursor cursor, int offset) {
        SensorDataEntry entity = new SensorDataEntry( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // module_access_name
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // date
            cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3), // time
            cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4), // vibration
            cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5) // temperature
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SensorDataEntry entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setModule_access_name(cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setTime(cursor.isNull(offset + 3) ? null : cursor.getDouble(offset + 3));
        entity.setVibration(cursor.isNull(offset + 4) ? null : cursor.getDouble(offset + 4));
        entity.setTemperature(cursor.isNull(offset + 5) ? null : cursor.getDouble(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SensorDataEntry entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SensorDataEntry entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
