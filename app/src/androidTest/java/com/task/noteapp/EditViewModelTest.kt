package com.task.noteapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.task.noteapp.db.NotesDatabase
import com.task.noteapp.di.repository.MainRepository
import com.task.noteapp.editnote.EditNoteViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import org.junit.rules.RuleChain
import kotlin.jvm.Throws

@HiltAndroidTest
class EditViewModelTest {

    private lateinit var database: NotesDatabase
    private lateinit var viewModel: EditNoteViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Before
    fun setUp() {
        hiltRule.inject()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, NotesDatabase::class.java).build()
        viewModel = EditNoteViewModel(-1,MainRepository(database))
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() = coroutineRule.runBlockingTest {
        Assert.assertTrue(viewModel.currentDate!="")
    }
}