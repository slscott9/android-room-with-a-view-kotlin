package com.example.android.roomwordssample.utils

import android.widget.TextView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.android.roomwordssample.database.Cemetery
import com.example.android.roomwordssample.database.Grave

import org.w3c.dom.Text

/*
    Binding adapters are used in cemetery list item and grave list item
 */

@BindingAdapter("setCemeteryName")
fun TextView.setCemeteryName(item: Cemetery?){
    item?.cemeteryName?.let {
        text = item.cemeteryName
    }
}

@BindingAdapter("setCemeteryLocation")
fun TextView.setCemeteryLocation(item: Cemetery?){
    item?.cemeteryLocation?.let {
        text = item.cemeteryLocation
    }
}


@BindingAdapter("setGraveFirstName")
fun TextView.setGraveFirst(item: Grave?){
    item?.firstName?.let {
        text = item.firstName
    }
}

@BindingAdapter("setGraveLast")
fun TextView.setGraveLast(item: Grave?){
    item?.lastName?.let {
        text = item.lastName
    }
}

@BindingAdapter("setGraveBirth")
fun TextView.setGraveBirth(item: Grave?){
    item?.birthDate?.let {
        text = item.birthDate
    }
}

@BindingAdapter("setGraveDeath")
fun TextView.setGraveDeath(item: Grave?){
    item?.deathDate?.let {
        text = item.deathDate
    }
}

@BindingAdapter("setGraveMarried")
fun TextView.setGraveMarried(item: Grave?){
    item?.marriageYear?.let {
        text = item.marriageYear
    }
}

@BindingAdapter("setGraveComment")
fun TextView.setGraveComment(item: Grave?){
    item?.comment?.let {
        text = item.comment
    }
}

@BindingAdapter("setGraveNum")
fun TextView.setGraveNum(item: Grave?){
    item?.graveNumber?.let {
        text = item.graveNumber
    }
}






