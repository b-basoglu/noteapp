package com.task.noteapp.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

object EditNoteVMFactoryProvider {
    fun provideFactory(factory:EditNoteVMFactory,
                       noteId: Int
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(noteId) as T
            }
        }
    }
    @AssistedFactory
    interface EditNoteVMFactory{
        fun create(@Assisted("noteId") noteId : Int) : EditNoteViewModel
    }
}