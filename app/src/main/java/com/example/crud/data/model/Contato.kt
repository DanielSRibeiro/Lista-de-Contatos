package com.example.crud.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Contato(
    var id:Int,
    var nome:String,
    var telefone:String
) : Parcelable