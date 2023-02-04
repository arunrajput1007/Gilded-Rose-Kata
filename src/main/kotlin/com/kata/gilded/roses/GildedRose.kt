package com.kata.gilded.roses

import com.kata.gilded.roses.model.AbstractRose
import com.kata.gilded.roses.model.VaryingRose

class GildedRose(var roses: Array<AbstractRose>) {

    fun update(){
        roses.filterIsInstance(VaryingRose::class.java).forEach { it.vary() }
    }

}