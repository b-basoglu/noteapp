package com.task.noteapp.main

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.customui.CircleProgressBar
import com.task.noteapp.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint


/* @AndroidEntryPoint annotation needed. Hilt @AndroidEntryPoint's directly inject with this annotation
* */
@AndroidEntryPoint
class MainFragment : MainBaseFragment(){
    private val TOTAL_SECONDS = 20  //seconds for count down
    private val COUNT_DOWN_INTERVAL = 1000L //interval for count down DO NOT CHANGE
    private val TOTAL_TIME = TOTAL_SECONDS * COUNT_DOWN_INTERVAL // total time that counted down

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NoteListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvNoData: TextView
    private lateinit var ivAdd: ImageView
    private lateinit var errorView: ConstraintLayout
    private lateinit var circleProgressBar: CircleProgressBar
    private var navigateToAddNoteTimer : CountDownTimer? = null
    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false)
        this.binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        this.binding.viewModel = viewModel
        initViews()
    }

    private fun initViews() {
        initRecyclerview()
        initOtherViews()
        setupDataObservers()
        setupLayoutListeners()
        startCountDownTimer()
    }
    private fun initRecyclerview(){
        recyclerView = binding.rvRecycler
        recyclerView.setHasFixedSize(true)
        adapter = viewModel.adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
    }

    private fun initOtherViews(){
        ivAdd=  binding.ivAdd
        tvNoData = binding.tvNoData
        errorView = binding.errorView
        circleProgressBar = binding.circleProgress
    }
    private fun setupDataObservers(){
        viewModel.mainFragmentEvents.observe(viewLifecycleOwner,{
            when (it) {
                MainFragmentEvents.NAVIGATE_TO_EDIT_NOTE -> navigateToEditNote(viewModel.editTextID)
                MainFragmentEvents.NAVIGATE_TO_CREATE_NOTE -> navigateToCreateNote()
                else -> {
                    Log.d(MainFragment.toString() ," EVENT ERROR")
                }
            }
        })
        viewModel.rowCountLD.observe(viewLifecycleOwner,{
            showIfNoDataStartTimer(it)
        })
        viewModel.adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })
    }
    private fun setupLayoutListeners(){
        ivAdd.setOnClickListener {
            navigateToCreateNote()
        }
    }
    private fun showIfNoDataStartTimer(rowCount : Int){
        if (rowCount!=0){
            cancelCountDownTimer()
            tvNoData.visibility = View.GONE
            errorView.visibility = View.GONE
        }else{
            startCountDownTimer()
            errorView.visibility = View.VISIBLE
            tvNoData.visibility = View.VISIBLE
        }
    }

    //To set toolbar title
    override fun getTitle(): String {
        return resources.getString(R.string.NOTE_LIST)
    }

    override fun hideBackButton(): Boolean {
        return true
    }

    /*
     * CountDownTimer takes two arguments TOTAL_TIME is seconds long and COUNT_DOWN_INTERVAL one is interval
     * in milliseconds long. When error occurred if user do nothing it will navigateToCreateNote
     */
    private fun startCountDownTimer(){
        cancelCountDownTimer()
        navigateToAddNoteTimer = null
        navigateToAddNoteTimer = object : CountDownTimer(TOTAL_TIME, COUNT_DOWN_INTERVAL) {
            override fun onFinish() {
                circleProgressBar.progress = 0f
                navigateToCreateNote()
            }

            override fun onTick(millisUntilFinished: Long) {
                val resultantFloat: Float = (TOTAL_TIME - millisUntilFinished).toFloat() / TOTAL_TIME
                circleProgressBar.progress = resultantFloat
            }
        }.start()
    }
    fun cancelCountDownTimer(){
        navigateToAddNoteTimer?.cancel()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
    private fun navigateToEditNote(id: Int) {
        controller?.navigateToEditNote(id)
    }
    private fun navigateToCreateNote() {
        cancelCountDownTimer()
        controller?.navigateToEditNote()
    }
}