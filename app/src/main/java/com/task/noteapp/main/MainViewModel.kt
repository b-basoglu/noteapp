package com.task.noteapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.task.noteapp.di.repository.MainRepository
import com.task.noteapp.main.NoteListAdapter.NoteClickController
import com.task.noteapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Constructor injection needed since with @HiltViewModel annotation
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel(), NoteClickController {

    var adapter = NoteListAdapter(this)
    var mainFragmentEvents = SingleLiveEvent<MainFragmentEvents>()
    val rowCountLD = mainRepository.getRowCount().asLiveData()
    var editTextID = -1

    init {
        fetchAll()
    }

    private fun fetchAll() {
        viewModelScope.launch {
            mainRepository.getNotes().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun navigateToEditNote(id: Int) {
        editTextID = id
        mainFragmentEvents.value = MainFragmentEvents.NAVIGATE_TO_EDIT_NOTE
    }
}