package com.task.noteapp.editnote

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.task.noteapp.di.repository.MainRepository
import com.task.noteapp.entities.Note
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditNoteViewModel @AssistedInject constructor(@Assisted("noteId") noteId : Int,
                                                    private val mainRepository: MainRepository) :
    ViewModel() {

    private var noteLD : MutableLiveData<Note> = MutableLiveData()
    val title : MutableLiveData<String> = MutableLiveData()
    val desc : MutableLiveData<String> = MutableLiveData()
    val imageUrlLD : MutableLiveData<String> = MutableLiveData()
    @SuppressLint("SimpleDateFormat")
    val currentDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
    private var isEditNote = false
    init {
        isEditNote = if ( noteId != EditNoteFragment.CREATE_NOTE){
            getNote(noteId)
            true
        }else{
            false
        }
    }

    private fun getNote(noteId:Int){
        viewModelScope.launch {
            noteLD.value = mainRepository.getNoteWithId(noteId)
            if (noteLD.value!= null){
                title.value = noteLD.value!!.title
                desc.value = noteLD.value!!.desc
                imageUrlLD.value = noteLD.value!!.imageUrl
            }
        }
    }

    fun addNote() {
        viewModelScope.launch {
            if (isEditNote) {
                val note = noteLD.value
                if (note != null) {
                    note.title = title.value
                    note.desc = desc.value
                    note.imageUrl = imageUrlLD.value
                    note.dateTime = currentDate
                    note.edited = true
                    mainRepository.update(note)
                }
            } else {
                val note = Note(title.value,currentDate,desc.value,imageUrlLD.value,false)
                mainRepository.insertNote(note)
            }
        }
    }

    fun deleteNote(){
        if(noteLD.value!=null){
            viewModelScope.launch {
                mainRepository.deleteNote(noteLD.value!!)
            }
        }
    }
}