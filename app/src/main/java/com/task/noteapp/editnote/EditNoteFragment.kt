package com.task.noteapp.editnote


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentEditNoteBinding
import com.task.noteapp.main.MainBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/* @AndroidEntryPoint annotation needed. Hilt @AndroidEntryPoint's directly inject with this annotation
* */
@AndroidEntryPoint
class EditNoteFragment : MainBaseFragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private lateinit var viewModel: EditNoteViewModel
    private lateinit var btSave: AppCompatButton
    private var noteId = -1

    @Inject
    lateinit var assistedFactory: EditNoteVMFactoryProvider.EditNoteVMFactory

    companion object {
        const val NOTE_ID = "NOTE_ID"
        const val CREATE_NOTE = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        noteId = args.getInt(NOTE_ID)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_note, container, false)
        this.binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = EditNoteVMFactoryProvider.provideFactory(assistedFactory,noteId)
        viewModel = ViewModelProvider(this,factory).get(EditNoteViewModel::class.java)
        binding.viewModel = viewModel
        btSave=  binding.btSave
        setOnClickListeners()
    }
    private fun setOnClickListeners(){
        btSave.setOnClickListener {
            viewModel.addNote()
            requireActivity().onBackPressed()
        }
    }
    override fun getTitle(): String {
        return if(noteId == CREATE_NOTE){
            resources.getString(R.string.CREATE_TEXT)
        }else{
            resources.getString(R.string.EDIT_TEXT)
        }
    }

    override fun hideBackButton(): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
            R.id.delete_note ->{
                viewModel.deleteNote()
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}