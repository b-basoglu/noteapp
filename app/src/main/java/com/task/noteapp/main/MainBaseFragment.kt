package com.task.noteapp.main

import android.content.Context
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

/**     This class implemented to communicate between fragment and its activity
 *      There was other ways but this is the most convenient way that I think for this case
 *      The most common way is SharedViewModel. Its lifecycle is activity lifecycle. In a continuous
 *      development with single activity and multiple fragments there could be regrets for using
 *      simple data communication. But its easier than this way
 * */
abstract class MainBaseFragment : Fragment() {
    @JvmField
    protected var controller: MainBaseControllerInterface? = null
    override fun onAttach(context: Context) {
        if (context is MainBaseControllerInterface) {
            controller = context
        } else {
            throw RuntimeException("Activity must implement SupportCenterControllerInterface")
        }
        super.onAttach(context)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        controller!!.updateToolbarTitle(getTitle())
        controller!!.hideBackButton(hideBackButton())
    }

    abstract fun getTitle(): String
    abstract fun hideBackButton():Boolean


    interface MainBaseControllerInterface {
        fun updateToolbarTitle(title: String)
        fun navigateToEditNote()
        fun navigateToEditNote(id: Int)
        fun hideBackButton(isHidden: Boolean)
    }
}