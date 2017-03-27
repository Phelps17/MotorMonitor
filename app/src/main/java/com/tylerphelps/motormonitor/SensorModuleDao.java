package com.tylerphelps.motormonitor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.tylerphelps.motormonitor.SensorModule;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SENSOR_MODULE".
*/
public class SensorModuleDao extends AbstractDao<SensorModule, Long> {

    public static final String TABLENAME = "SENSOR_MODULE";

    /**
     * Properties of entity SensorModule.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Access_name = new Property(1, String.class, "access_name", false, "ACCESS_NAME");
        public final static Property Access_passcode = new Property(2, String.class, "access_passcode", false, "ACCESS_PASSCODE");
        public final static Property Viewable_name = new Property(3, String.class, "viewable_name", false, "VIEWABLE_NAME");
        public final static Property Details = new Property(4, String.class, "details", false, "DETAILS");
        public final static Property SensorModuleId = new Property(5, long.class, "sensorModuleId", false, "SENSOR_MODULE_ID");
    };

    private DaoSession daoSession;


    public SensorModuleDao(DaoConfig config) {
        super(config);
    }
    
    public SensorModuleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SENSOR_MODULE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ACCESS_NAME\" TEXT NOT NULL ," + // 1: access_name
                "\"ACCESS_PASSCODE\" TEXT NOT NULL ," + // 2: access_passcode
                "\"VIEWABLE_NAME\" TEXT NOT NULL ," + // 3: viewable_name
                "\"DETAILS\" TEXT," + // 4: details
                "\"SENSOR_MODULE_ID\" INTEGER NOT NULL );"); // 5: sensorModuleId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SENSOR_MODULE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, SensorModule entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getAccess_name());
        stmt.bindString(3, entity.getAccess_passcode());
        stmt.bindString(4, entity.getViewable_name());
 
        String details = entity.getDetails();
        if (details != null) {
            stmt.bindString(5, details);
        }
        stmt.bindLong(6, entity.getSensorModuleId());
    }

    @Override
    protected void attachEntity(SensorModule entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public SensorModule readEntity(Cursor cursor, int offset) {
        SensorModule entity = new SensorModule( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // access_name
            cursor.getString(offset + 2), // access_passcode
            cursor.getString(offset + 3), // viewable_name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // details
            cursor.getLong(offset + 5) // sensorModuleId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, SensorModule entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAccess_name(cursor.getString(offset + 1));
        entity.setAccess_passcode(cursor.getString(offset + 2));
        entity.setViewable_name(cursor.getString(offset + 3));
        entity.setDetails(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSensorModuleId(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(SensorModule entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(SensorModule entity) {
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
