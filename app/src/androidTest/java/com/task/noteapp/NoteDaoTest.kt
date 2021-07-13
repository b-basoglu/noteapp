package com.task.noteapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.task.noteapp.dao.NoteDao
import com.task.noteapp.db.NotesDatabase
import com.task.noteapp.entities.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    private lateinit var database: NotesDatabase
    private lateinit var noteDao : NoteDao
    private val note1 = Note("1", "A", "", "",)
    private val note2 = Note("2", "B", "", "1", true)
    private val note3 = Note("3", "C", "", "2")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java).build()
        noteDao = database.noteDao()

        // Insert notes
        noteDao.insertNote(note1)
        noteDao.insertNote(note2)
        noteDao.insertNote(note3)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetNotes() = runBlocking {
        val noteList = noteDao.getNotesForTest().first()
        Assert.assertThat(noteList.size, Matchers.equalTo(3))

        // Ensure list is sorted by id desc with auto generated id
        Assert.assertEquals(noteList[0].toString(), note3.toString())
        Assert.assertEquals(noteList[1].toString(), note2.toString())
        Assert.assertEquals(noteList[2].toString(), note1.toString())

    }
    @Test fun testGetRowID() = runBlocking {
        val noteList = noteDao.getNotesForTest().first()
        Assert.assertFalse(noteList[0].id == noteList[2].id)
        Assert.assertFalse(noteList[1].id == noteList[2].id)
    }
    @Test fun testGetRowCount() = runBlocking {
        Assert.assertEquals(noteDao.getRowCount().first(), 3)
    }

    @Test fun testDelete() = runBlocking {
        noteDao.deleteNote(noteDao.getNotesForTest().first()[0])
        Assert.assertEquals(noteDao.getRowCount().first(), 2)
    }

    @Test fun testUpdate() = runBlocking {
        val note = noteDao.getNotesForTest().first()[1]
        note.desc = "changed"
        noteDao.updateNote(note)
        Assert.assertEquals("changed",noteDao.getNotesForTest().first()[1].desc)
    }
}