package com.geekbrains.androidstart.notes.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//3. Класс данных, созданный на шестом уроке, используйте для заполнения карточки списка.

@Parcelize
data class Note(
    var name: String,
    var description: String,
    var date: String
) : Parcelable
