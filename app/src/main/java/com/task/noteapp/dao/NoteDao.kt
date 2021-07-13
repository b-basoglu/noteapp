package com.task.noteapp.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.task.noteapp.entities.Note
import com.task.noteapp.utils.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NOTE +" ORDER BY id DESC")
    fun getNotes() : PagingSource<Int,Note>

    @Query("SELECT * FROM Notes WHERE id =:id")
    suspend fun getNotesWithID(id:Int) : Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Update
    suspend fun updateNote(note:Note)

    @Query("SELECT COUNT(*) FROM "+ Constants.TABLE_NOTE)
    fun getRowCount(): Flow<Int>

    //just for test
    @Query("SELECT * FROM "+ Constants.TABLE_NOTE +" ORDER BY id DESC")
    fun getNotesForTest(): Flow<List<Note>>
}