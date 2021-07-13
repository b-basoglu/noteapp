package com.task.noteapp.di.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.task.noteapp.db.NotesDatabase
import com.task.noteapp.entities.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class MainRepository @Inject constructor(private val database: NotesDatabase) {
    @ExperimentalCoroutinesApi
    fun getNotes(): Flow<PagingData<Note>> {
        val pagingSourceFactory =
            { database.noteDao().getNotes() }
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun getNoteWithId(id: Int): Note {
        return database.noteDao().getNotesWithID(id)
    }

    fun getRowCount() = database.noteDao().getRowCount()

    suspend fun deleteNote(note: Note) {
        database.noteDao().deleteNote(note)
    }

    suspend fun insertNote(note: Note) {
        database.noteDao().insertNote(note)
    }

    suspend fun update(note: Note) {
        database.noteDao().updateNote(note)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}