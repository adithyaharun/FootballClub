package com.adithyaharun.footballclub.Misc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FootballDatabase.db", null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.applicationContext)
            }

            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable("FAVORITE_EVENTS", true,
                "ID_" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "EVENT_ID" to TEXT + UNIQUE,
                "EVENT_DATE" to TEXT,
                "EVENT_HOME_TEAM_ID" to TEXT,
                "EVENT_AWAY_TEAM_ID" to TEXT,
                "EVENT_HOME_TEAM" to TEXT,
                "EVENT_AWAY_TEAM" to TEXT,
                "EVENT_HOME_SCORE" to INTEGER,
                "EVENT_AWAY_SCORE" to INTEGER,
                "EVENT_HOME_GOALS" to TEXT,
                "EVENT_AWAY_GOALS" to TEXT,
                "EVENT_HOME_YELLOW_CARDS" to TEXT,
                "EVENT_AWAY_YELLOW_CARDS" to TEXT,
                "EVENT_HOME_RED_CARDS" to TEXT,
                "EVENT_AWAY_RED_CARDS" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("FAVORITE_EVENTS", true)
    }
}

// Access property for Context
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)