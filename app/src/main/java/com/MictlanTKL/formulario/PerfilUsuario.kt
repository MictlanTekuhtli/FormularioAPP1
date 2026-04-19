package com.MictlanTKL.formulario

import android.os.Parcel
import android.os.Parcelable

data class PerfilUsuario(
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: String,
    val genero: String,
    val telefono: String,
    val email: String,
    val intereses: List<String>,
    val fotoUri: String? = null
) : Parcelable {

    // Constructor que reconstruye el objeto desde un Parcel
    private constructor(parcel: Parcel) : this(
        nombre = parcel.readString() ?: "",
        apellidos = parcel.readString() ?: "",
        fechaNacimiento = parcel.readString() ?: "",
        genero = parcel.readString() ?: "",
        telefono = parcel.readString() ?: "",
        email = parcel.readString() ?: "",
        intereses = parcel.createStringArrayList() ?: emptyList()
    )

    // Escribe los datos del objeto en un Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(apellidos)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(genero)
        parcel.writeString(telefono)
        parcel.writeString(email)
        parcel.writeStringList(intereses)
    }

    // Indica que no hay contenido especial (archivos, etc.)
    override fun describeContents(): Int = 0

    // Objeto companion requerido por la interfaz Parcelable
    companion object CREATOR : Parcelable.Creator<PerfilUsuario> {
        override fun createFromParcel(parcel: Parcel): PerfilUsuario {
            return PerfilUsuario(parcel)
        }

        override fun newArray(size: Int): Array<PerfilUsuario?> {
            return arrayOfNulls(size)
        }
    }
}