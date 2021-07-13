package com.task.noteapp

import com.task.noteapp.entities.Note
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NoteTest {

    private lateinit var note: Note

    @Before
    fun start() {
        note = Note("ben","13/7/2021 12:51:48","sen","https://cdn.yemek.com/mncrop/940/625/uploads/2015/01/imam-bayildi-yeni-one-cikan.jpg",true)
        note.id = 0;
    }

    @Test
    fun test_default_value() {
        val note2 = Note("ben","13/7/2021 12:51:48","sen","https://cdn.yemek.com/mncrop/940/625/uploads/2015/01/imam-bayildi-yeni-one-cikan.jpg")
        note.id = 1;
        Assert.assertEquals(false, note2.edited)
    }

    @Test
    fun test_toString() {
        Assert.assertEquals("ben : 13/7/2021 12:51:48", note.toString())
    }
}
