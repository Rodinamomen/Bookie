package com.example.bookie.foryou.model

import android.os.Parcel
import android.os.Parcelable

data class AboutBook(val bookDescription:String? , val bookTitle:String?,val bookImage:String?, val bookAuthor:String?):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookDescription)
        parcel.writeString(bookTitle)
        parcel.writeString(bookImage)
        parcel.writeString(bookAuthor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AboutBook> {
        override fun createFromParcel(parcel: Parcel): AboutBook {
            return AboutBook(parcel)
        }

        override fun newArray(size: Int): Array<AboutBook?> {
            return arrayOfNulls(size)
        }
    }

}
