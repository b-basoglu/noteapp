package com.task.noteapp.di.module

import android.content.Context
import androidx.room.Room
import com.task.noteapp.dao.NoteDao
import com.task.noteapp.db.NotesDatabase
import com.task.noteapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): NotesDatabase {
        return Room.databaseBuilder(
            appContext
            , NotesDatabase::class.java
            , Constants.NOTE_DB_NAME
            ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePeopleDao(database: NotesDatabase): NoteDao {
        return database.noteDao()
    }
}
