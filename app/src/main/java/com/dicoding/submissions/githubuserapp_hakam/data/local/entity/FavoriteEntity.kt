package com.dicoding.submissions.githubuserapp_hakam.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favorite")
@Parcelize
class FavoriteEntity(
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int,

    @field:ColumnInfo(name = "username")
    val username: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = null,

    ): Parcelable