package com.kata.gilded.roses.model

sealed class AbstractRose(protected var sellIn: Int, protected var quality: Int) {
    init {
        if (quality < 0) throw IllegalArgumentException("invalid quality")
    }

    override fun toString(): String {
        return "AbstractRose(sellIn=$sellIn, quality=$quality)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractRose) return false

        if (sellIn != other.sellIn) return false
        if (quality != other.quality) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sellIn
        result = 31 * result + quality
        return result
    }
}

sealed interface Expirable {
    fun expiryRate(): Int = -1
}

sealed interface VariableQuality {
    fun changeRate(sellIn: Int): Int
}

sealed interface Appreciable : VariableQuality {
    override fun changeRate(sellIn: Int): Int = 1
}

sealed interface Degradable : VariableQuality {
    override fun changeRate(sellIn: Int): Int = if(sellIn > 0) normalChangeRate() else expiryChangeRate()
    fun normalChangeRate() = -1
    fun expiryChangeRate() = 2 * normalChangeRate()
}

sealed class VaryingRose(sellIn: Int, quality: Int) : AbstractRose(sellIn, quality), Expirable, VariableQuality {
    init {
        if (quality > 50) throw IllegalArgumentException("invalid quality")
    }

    fun vary() {
        sellIn += expiryRate()
        quality += changeRate(sellIn)
    }
}

class AgedBrie(sellIn: Int, quality: Int) : VaryingRose(sellIn, quality), Appreciable

class Sulfuras(sellIn: Int) : AbstractRose(sellIn, quality = 80)

class Backstage(sellIn: Int, quality: Int) : VaryingRose(sellIn, quality), Appreciable {

    override fun changeRate(sellIn: Int): Int =
        when {
            sellIn > 10 -> super.changeRate(sellIn)
            sellIn > 6 -> 2
            sellIn > 0 -> 3
            else -> -quality
        }
}

class CommonRose(sellIn: Int, quality: Int) : VaryingRose(sellIn, quality), Degradable

class Conjured(sellIn: Int, quality: Int) : VaryingRose(sellIn, quality), Degradable {
    override fun normalChangeRate(): Int = 2 * super.normalChangeRate()
}