package br.com.temperatureconverter

import kotlin.math.round

/**
 * Temperature converter project
 * * Last modification: April 23th 2021.
 * @author Josélio de S. C. Júnior
 */
class Converter {

    companion object {
        const val K = 273.15f
    }

    fun tempFromMirror(value: String?, id: Int): String {
        val v = value?.toFloatOrNull()

        return if (v != null) {
            if (v in -999999.99f..999999.99f) {
                when (id) {
                    R.id.buttonfc -> { "${v}°C" }
                    R.id.buttonff -> { "${v}°F" }
                    R.id.buttonfk -> { "${v}K" }
                    else -> { "" }
                }
            } else { "" }
        } else { "" }
    }

     fun conversion(value: String?, from: Int?, to: Int?, msg: Messages): String {
        val v = value?.toFloatOrNull()

        fun temperatureUnit(value: Float, unit: Int): String {
            val round = round(value * 100) / 100
            return when (unit) {
                R.id.buttontc -> { "${round}°C" }
                R.id.buttontf -> { "${round}°F" }
                else -> { "${round}K" }
            }
        }

        return if (v != null) {
            if (v in -999999.99f..999999.99f) {
                when (from) {
                    R.id.buttonfc -> {
                        when (to) {
                            R.id.buttontf -> {temperatureUnit(((v * 9f) / 5f) + 32f, to)}
                            R.id.buttontk -> {temperatureUnit(v + K, to)}
                            else -> { msg.toQuestion }
                        }
                    }
                    R.id.buttonff -> {
                        when (to) {
                            R.id.buttontc -> {temperatureUnit(((v - 32f) * 5f) / 9f, to)}
                            R.id.buttontk -> {temperatureUnit((((v - 32f) * 5f) / 9f) + K, to)}
                            else -> { msg.toQuestion }
                        }
                    }
                    R.id.buttonfk -> {
                        when (to) {
                            R.id.buttontc -> {temperatureUnit(v - K, to)}
                            R.id.buttontf -> {temperatureUnit((((v - K) * 9f) / 5) + 32, to)}
                            else -> { msg.toQuestion }
                        }
                    }
                    else -> { msg.fromQuestion }
                }
            } else { msg.maxValue }
        } else { "" }
    }

}