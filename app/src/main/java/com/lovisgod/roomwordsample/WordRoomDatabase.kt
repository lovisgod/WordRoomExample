package com.lovisgod.roomwordsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    // the purpose of this class is to populate the database on creation of the database instance
    // it is optional.
    private class WordDatabaseCallback(
        // use CoroutineScope to perform the operation in the background thread
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            // you must implement the super method
            super.onOpen(db)
            // check if there is istance of the database if so
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao() // here we use the wordDao

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)

                    word = Word("World!")
                    wordDao.insert(word)

                    word = Word("Damilola")
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
                        ): WordRoomDatabase {
            val tempInstance = INSTANCE

            // if the instance is not null use the already created instance
            // else if the instance is null, build an instance of WordDatabase
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)) // add the created callback and pass in the scope as the params
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}