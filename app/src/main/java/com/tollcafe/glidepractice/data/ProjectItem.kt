package com.tollcafe.glidepractice.data

import android.os.Parcel
import android.os.Parcelable

data class ProjectItem(
    val id: Int,
    val title: String,
    val envelopePic: String,
    val link: String,
    var isRead: Boolean = false // 默认值为 false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(envelopePic)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectItem> {
        override fun createFromParcel(parcel: Parcel): ProjectItem {
            return ProjectItem(parcel)
        }

        override fun newArray(size: Int): Array<ProjectItem?> {
            return arrayOfNulls(size)
        }
    }
}

