package com.example.esencias

import android.os.Parcel
import android.os.Parcelable

data class Vela(
    val nombre: String,
    val precio: String,
    val descripcion: String?,
    val informacion: String?,
    val imagen: String,
    val tamano: String?,
    val aromas: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(precio)
        parcel.writeString(descripcion)
        parcel.writeString(informacion)
        parcel.writeString(imagen)
        parcel.writeString(tamano)
        parcel.writeString(aromas)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Vela> {
        override fun createFromParcel(parcel: Parcel): Vela {
            return Vela(parcel)
        }

        override fun newArray(size: Int): Array<Vela?> {
            return arrayOfNulls(size)
        }
    }
}