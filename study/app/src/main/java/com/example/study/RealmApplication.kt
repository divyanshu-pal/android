package com.example.study

import android.app.Application
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import io.realm.exceptions.RealmMigrationNeededException
import android.util.Log

class RealmApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .name("demo.realm")
            .schemaVersion(3) // Ensure this matches the latest version
            .migration(MyRealmMigration()) // Attach the migration logic
            .build()

        try {
            Realm.setDefaultConfiguration(config)
        } catch (e: RealmMigrationNeededException) {
            Log.e("RealmMigration", "Migration needed: ${e.message}")
        }
    }
}


class MyRealmMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        var version = oldVersion


        if (version == 0L) {
            if (!schema.contains("Register")) {
                schema.create("Register")
                    .addField("name", String::class.java)
                    .addField("email", String::class.java)
                    .addField("password", String::class.java)
                    .addField("confirmPassword", String::class.java)
            }
            version++
        }


        if (version == 1L) {
            if (!schema.contains("Login")) {
                schema.create("Login")
                    .addField("email", String::class.java)
                    .addField("password", String::class.java)
            }
            version++
        }


        if (version == 2L) {
            schema.get("Register")?.apply {
                setRequired("name", true)
                setRequired("email", true)
                setRequired("password", true)
                setRequired("confirmPassword", true)
            }

            schema.get("Login")?.apply {
                setRequired("email", true)
                setRequired("password", true)
            }

            version++
        }
    }
}
