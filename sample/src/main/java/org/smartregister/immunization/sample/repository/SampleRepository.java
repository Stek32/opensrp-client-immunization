package org.smartregister.immunization.sample.repository;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import org.smartregister.AllConstants;
import org.smartregister.immunization.ImmunizationLibrary;
import org.smartregister.immunization.repository.RecurringServiceRecordRepository;
import org.smartregister.immunization.repository.RecurringServiceTypeRepository;
import org.smartregister.immunization.repository.VaccineNameRepository;
import org.smartregister.immunization.repository.VaccineRepository;
import org.smartregister.immunization.repository.VaccineTypeRepository;
import org.smartregister.immunization.util.IMDatabaseUtils;
import org.smartregister.repository.AlertRepository;
import org.smartregister.repository.EventClientRepository;
import org.smartregister.repository.Repository;

/**
 * Created by keyman on 01/08/2017.
 */
public class SampleRepository extends Repository {

    private static final String TAG = SampleRepository.class.getCanonicalName();
    protected SQLiteDatabase readableDatabase;
    protected SQLiteDatabase writableDatabase;
    private Context context;

    private String password = "Sample_PASS";

    public SampleRepository(Context context, org.smartregister.Context openSRPContext) {
        super(context, AllConstants.DATABASE_NAME, AllConstants.DATABASE_VERSION, openSRPContext.getInstance().session(), null, openSRPContext.getInstance().sharedRepositoriesArray());
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        super.onCreate(database);
        EventClientRepository.createTable(database, EventClientRepository.Table.client, EventClientRepository.client_column.values());
        EventClientRepository.createTable(database, EventClientRepository.Table.address, EventClientRepository.address_column.values());
        EventClientRepository.createTable(database, EventClientRepository.Table.event, EventClientRepository.event_column.values());
        EventClientRepository.createTable(database, EventClientRepository.Table.obs, EventClientRepository.obs_column.values());

        VaccineRepository.createTable(database);
        VaccineNameRepository.createTable(database);
        VaccineTypeRepository.createTable(database);

        database.execSQL(AlertRepository.ALTER_ADD_OFFLINE_COLUMN);
        database.execSQL(AlertRepository.OFFLINE_INDEX);

        RecurringServiceTypeRepository.createTable(database);
        RecurringServiceRecordRepository.createTable(database);

        // Recurring service json changed. update
        RecurringServiceTypeRepository recurringServiceTypeRepository = ImmunizationLibrary.getInstance().recurringServiceTypeRepository();
        IMDatabaseUtils.populateRecurringServices(context, database, recurringServiceTypeRepository);

        onUpgrade(database, 1, 2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Repository.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion) {
            switch (upgradeTo) {
                case 2:
                    upgradeToVersion2(db);
                    break;
                default:
                    break;
            }
            upgradeTo++;
        }
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return getReadableDatabase(password);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return getWritableDatabase(password);
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase(String password) {
        try {
            if (readableDatabase == null || !readableDatabase.isOpen()) {
                if (readableDatabase != null) {
                    readableDatabase.close();
                }
                readableDatabase = super.getReadableDatabase(password);
            }
            return readableDatabase;
        } catch (Exception e) {
            Log.e(TAG, "Database Error. " + e.getMessage());
            return null;
        }

    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase(String password) {
        if (writableDatabase == null || !writableDatabase.isOpen()) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            writableDatabase = super.getWritableDatabase(password);
        }
        return writableDatabase;
    }

    @Override
    public synchronized void close() {
        if (readableDatabase != null) {
            readableDatabase.close();
        }

        if (writableDatabase != null) {
            writableDatabase.close();
        }
        super.close();
    }


    private void upgradeToVersion2(SQLiteDatabase db) {
        try {
            db.execSQL(VaccineRepository.UPDATE_TABLE_ADD_EVENT_ID_COL);
            db.execSQL(VaccineRepository.EVENT_ID_INDEX);
            db.execSQL(VaccineRepository.UPDATE_TABLE_ADD_FORMSUBMISSION_ID_COL);
            db.execSQL(VaccineRepository.FORMSUBMISSION_INDEX);

            db.execSQL(VaccineRepository.UPDATE_TABLE_ADD_OUT_OF_AREA_COL);
            db.execSQL(VaccineRepository.UPDATE_TABLE_ADD_OUT_OF_AREA_COL_INDEX);

            EventClientRepository.createTable(db, EventClientRepository.Table.path_reports, EventClientRepository.report_column.values());
            db.execSQL(VaccineRepository.UPDATE_TABLE_ADD_HIA2_STATUS_COL);

            IMDatabaseUtils.accessAssetsAndFillDataBaseForVaccineTypes(context, db);

        } catch (Exception e) {
            Log.e(TAG, "upgradeToVersion2 " + Log.getStackTraceString(e));
        }
    }

}
