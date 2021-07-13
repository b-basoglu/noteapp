package com.task.noteapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.databinding.NoteListItemBinding
import com.task.noteapp.entities.Note


class NoteListAdapter(private val controller:NoteClickController): PagingDataAdapter<Note, NoteListAdapter.UserViewHolder>( DIFF_CALLBACK ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding: NoteListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.note_list_item,
            parent,
            false
        )
        return UserViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
                note -> holder.bind(note)
                holder.binding.rootListItem.setOnClickListener {
                    controller.navigateToEditNote(note.id!!)
                }
        }
    }

    inner class UserViewHolder(val binding: NoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.noteImage.setImage(note.imageUrl)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(
                    oldItem: Note,
                    newItem: Note
                ): Boolean = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Note,
                    newItem: Note
                ): Boolean = oldItem.dateTime == newItem.dateTime
            }
    }
    interface NoteClickController{
        fun navigateToEditNote(id : Int)
    }
}

