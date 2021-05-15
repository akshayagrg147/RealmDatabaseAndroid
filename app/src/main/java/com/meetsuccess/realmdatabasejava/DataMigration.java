package com.meetsuccess.realmdatabasejava;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class DataMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        Log.d("daatamigration",oldVersion+"--"+newVersion);
        RealmSchema schema = realm.getSchema();
        if (oldVersion <= 1) {
            RealmObjectSchema roomSchema = schema.get("DataModal");
            if (roomSchema != null) {
                roomSchema.addField("keepRoom", Boolean.class);


            }

//            schema.create("SubClaimObject")
//                    .addField("claimCodeC", String.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("claimNameC", String.class)
//                    .addField("claimSubGroupCodeC", String.class);
//
//            schema.create("ClaimDraftObject")
//                    .addField("templateGroupIdN", int.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("idn", int.class)
//                    .addField("claimTemplateName", String.class)
//                    .addField("templateReferenceNoC", String.class)
//                    .addField("empCodeC", String.class)
//                    .addField("claimableAmount", float.class);
            oldVersion++;
        }
        if (oldVersion <= 2) {
            RealmObjectSchema roomSchema = schema.get("DataModal1");
            if (roomSchema != null) {

                roomSchema.addField("id", Long.class);
                roomSchema.addField("courseName", String.class);
                roomSchema.addField("courseDescription", String.class);
                roomSchema.addField("courseTracks", String.class);
                roomSchema.addField("keepRoom", Boolean.class);
                roomSchema.addField("courseDuration", String.class);


            }


//            schema.create("SubClaimObject")
//                    .addField("claimCodeC", String.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("claimNameC", String.class)
//                    .addField("claimSubGroupCodeC", String.class);
//
//            schema.create("ClaimDraftObject")
//                    .addField("templateGroupIdN", int.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("idn", int.class)
//                    .addField("claimTemplateName", String.class)
//                    .addField("templateReferenceNoC", String.class)
//                    .addField("empCodeC", String.class)
//                    .addField("claimableAmount", float.class);
            oldVersion++;
        }
    }
}
