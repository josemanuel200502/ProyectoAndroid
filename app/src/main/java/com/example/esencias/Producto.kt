package com.example.esencias
import android.os.Parcel
import android.os.Parcelable

class Producto (
    val id:Int,
    val nombre:String,
    val descripcion:String,
    val precio:Double,
    val imagen: String
    )

    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeString(imagen)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}