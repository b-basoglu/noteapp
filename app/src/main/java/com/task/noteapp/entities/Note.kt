package com.task.noteapp.entities

import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.task.noteapp.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.TABLE_NOTE)
class Note(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "date_time") var dateTime: String?,
    @ColumnInfo(name = "desc") var desc: String?,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?,
    @ColumnInfo(name = "edited") var edited: Boolean = false
) : Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int? = null

    override fun toString(): String {
        return "$title : $dateTime"
    }

    fun isImageUrlEmpty():Boolean{
        return TextUtils.isEmpty(imageUrl)
    }
}