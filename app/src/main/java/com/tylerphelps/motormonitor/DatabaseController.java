package com.tylerphelps.motormonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TylerPhelps on 3/27/17.
 */

public class DatabaseController {
    private DaoMaster.DevOpenHelper dbHelper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SensorModuleDao sensorModuleDao;
    private SensorDataEntryDao sensorDataEntryDao;
    private Context context;

    public DatabaseController(Context context) {
        this.context = context;
        initDatabase();
    }

    private void initDatabase() {
        dbHelper = new DaoMaster.DevOpenHelper(this.context, "ORM.sqlite", null);
        db = dbHelper.getWritableDatabase();

        //Get DaoMaster
        daoMaster = new DaoMaster(db);

        //Create database and tables
        daoMaster.createAllTables(db, true);

        daoSession = daoMaster.newSession();

        sensorModuleDao = daoSession.getSensorModuleDao();
        sensorDataEntryDao = daoSession.getSensorDataEntryDao();
    }

    public boolean addSensorModule(SensorModule sm) {
        Log.d("DB", ("adding new sensor module " + sm.getId()));

        sensorModuleDao.insert(sm);
        Log.d("DB", "added successfully");

        closeReopenDatabase();
        return true;
    }

    public boolean addDataEntry(SensorDataEntry entry) {
        Log.d("DB", ("adding new data entry " + entry.getId()));

        sensorDataEntryDao.insert(entry);

        Log.d("DB", "added successfully");

        closeReopenDatabase();
        return true;
    }

    public List<SensorModule> getSensorModules() { return this.sensorModuleDao.loadAll(); }

    public List<SensorDataEntry> getDataFromModule(SensorModule module) {
        List<SensorDataEntry> returnList = new ArrayList<SensorDataEntry>();

        for (SensorDataEntry entry : this.sensorDataEntryDao.loadAll()) {
            if (entry.getModule_access_name().equals(module.getAccess_name())) {
                returnList.add(entry);
            }
        }

        return returnList;
    }

    public void closeDatabase()
    {
        Log.d("DB", "close");
        daoSession.clear();
        db.close();
        dbHelper.close();
    }

    private void closeReopenDatabase()
    {
        Log.d("DB", "close reopen");
        closeDatabase();

        dbHelper = new DaoMaster.DevOpenHelper(this.context, "ORM.sqlite", null);
        db = dbHelper.getWritableDatabase();

        //Get DaoMaster
        daoMaster = new DaoMaster(db);

        //Create database and tables
        daoMaster.createAllTables(db, true);

        //Create DaoSession
        daoSession = daoMaster.newSession();

        sensorModuleDao = daoSession.getSensorModuleDao();
        sensorDataEntryDao = daoSession.getSensorDataEntryDao();
    }
}
