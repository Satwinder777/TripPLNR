package com.example.tripplnr.navigationscreens.DataCls

import android.os.Parcel
import android.os.Parcelable
import android.widget.DatePicker

data class Massage(
    val msg :String
)
data class daterangedataCls(
    var startday : String,
    var endday :String,
)
data class guestdatacls(
    var guest:Int,
    var rooms:Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(guest)
        parcel.writeInt(rooms)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<guestdatacls> {
        override fun createFromParcel(parcel: Parcel): guestdatacls {
            return guestdatacls(parcel)
        }

        override fun newArray(size: Int): Array<guestdatacls?> {
            return arrayOfNulls(size)
        }
    }
}