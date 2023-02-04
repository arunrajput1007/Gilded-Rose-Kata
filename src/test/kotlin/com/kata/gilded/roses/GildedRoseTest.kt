package com.kata.gilded.roses

import com.kata.gilded.roses.model.AbstractRose
import com.kata.gilded.roses.model.AgedBrie
import com.kata.gilded.roses.model.Backstage
import com.kata.gilded.roses.model.Sulfuras
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertNotNull

class GildedRoseTest {

    @Test
    fun `quality of any item cannot be negative`() {
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            { AgedBrie(5, -10) }, "invalid quality"
        )
    }

    @Test
    fun `quality of any item can be positive and zero`() {
        assertNotNull(AgedBrie(5, 5))
        assertNotNull(AgedBrie(4, 0))
    }

    @Test
    fun `the quality of an item is never more than 50`() {
        Assertions.assertThrows(
            IllegalArgumentException::class.java,
            { AgedBrie(5, 55) }, "quality cannot be more than 50"
        )
    }

    @Test
    fun `Aged Brie actually increases in Quality the older it gets`() {
        val items = arrayOf<AbstractRose>(AgedBrie(5, 4), AgedBrie(3, 6))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(AgedBrie(4, 5), AgedBrie(2, 7)),
            gildedRose.roses
        )
    }

    @Test
    fun `Sulfuras, being a legendary item, never has to be sold or decreases in Quality`(){
        val items = arrayOf<AbstractRose>(Sulfuras(5))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Sulfuras(5)),
            gildedRose.roses
        )
    }

    @Test
    fun `Sulfuras, being a legendary item, its quality is 80`(){
        val items = arrayOf<AbstractRose>(Sulfuras(5))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Sulfuras(5)),
            gildedRose.roses
        )
    }

    @Test
    fun `Backstage passes quality increases by 2 when there are 10 days or less`(){
        val items = arrayOf<AbstractRose>(Backstage(10, 4))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Backstage(9, 6)),
            gildedRose.roses
        )
    }

    @Test
    fun `Backstage passes quality increases by 1 when there are more than 10 days`(){
        val items = arrayOf<AbstractRose>(Backstage(12, 4))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Backstage(11, 5)),
            gildedRose.roses
        )
    }

    @Test
    fun `Backstage passes quality increases by 3 when there are there are 5 days or less`(){
        val items = arrayOf<AbstractRose>(Backstage(5, 4))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Backstage(4, 7)),
            gildedRose.roses
        )
    }

    @Test
    fun `Backstage passes quality drops to 0 when there are 0 days or less`(){
        val items = arrayOf<AbstractRose>(Backstage(0, 4))
        val gildedRose = GildedRose(items)
        gildedRose.update()
        assertContentEquals(
            arrayOf(Backstage( -1, 0)),
            gildedRose.roses
        )
    }
}