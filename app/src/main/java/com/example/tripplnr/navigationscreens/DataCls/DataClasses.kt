package com.example.tripplnr.navigationscreens.DataCls

import android.os.Parcel
import android.os.Parcelable
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng

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
data class userData(var first :String,var second:String)
data class User1(val username: String? = null, val email: String? = null)
data class sortData(val text:TextView,val image:ImageView)
data class locationItem(val name: String?, val latitude: Double?, val longitude: Double?)