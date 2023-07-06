package com.example.tripplnr.navigationscreens.DataCls

import android.location.Address
import android.os.Parcel
import android.os.Parcelable
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.PlacesSearchResult
import kotlinx.android.parcel.Parcelize

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
data class lat_data(val latData: LatLng,var name: String)
data class namePlace(val item :Pair<String,String?>)



object db_firebase{
    data class UserData(val email: String?,val name: String?,val signInWithGoogle:Boolean?=false)
}

@Parcelize
data class MyDataHandle(val data: List<PlacesSearchResult>?) : Parcelable {
}
@Parcelize
data class my_latlng(val data: LatLng?) : Parcelable {
}

