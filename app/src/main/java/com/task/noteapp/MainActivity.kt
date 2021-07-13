package com.task.noteapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.task.noteapp.databinding.MainActivityBinding
import com.task.noteapp.editnote.EditNoteFragment
import com.task.noteapp.main.MainBaseFragment
import com.task.noteapp.main.MainFragment
import com.task.noteapp.utils.FragmentUtils
import dagger.hilt.android.AndroidEntryPoint

/* @AndroidEntryPoint annotation needed. Otherwise Fragment @AndroidEntryPoint will give error
* */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainBaseFragment.MainBaseControllerInterface {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolbar)
        openMainFragment()

    }

    private fun openMainFragment() {
        Navigation.findNavController(this,R.id.container).navigate(R.id.note_list_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_note,menu)
        return true
    }

    /** Called from MainBaseFragment instance via Controller Interface to add toolbar title
     * @param title title that wanted to show
     * */
    override fun updateToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun navigateToEditNote(id: Int) {
        val args = Bundle()
        args.putInt(EditNoteFragment.NOTE_ID,id)
        Navigation.findNavController(this,R.id.container).navigate(R.id.edit_note_fragment,args)
    }

    override fun navigateToEditNote() {
        Navigation.findNavController(this,R.id.container).navigate(R.id.edit_note_fragment)
    }

    override fun hideBackButton(isHidden: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(!isHidden)
    }
}