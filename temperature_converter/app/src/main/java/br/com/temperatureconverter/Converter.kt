package br.com.temperatureconverter

import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.round

/**
 * * Last modification: April 14th 2021.
 *
 * This class sets interactions changes and converts of a temperature value.
 * @author Josélio de S. C. Júnior
 * @param cTemp (TextView) Where converted text or alert messages will appear.
 * @param iTemp (EditText) Temperature input value.
 * @param mTemp (TextView) Shows temperature input value.
 * @param fromC (Button) Gets input temperature value as Celsius.
 * @param fromF (Button) Gets input temperature value as Fahrenheit.
 * @param fromK (Button) Gets input temperature value as Kelvin.
 * @param toC (Button) Sets conversion value to Celsius temperature.
 * @param toF (Button) Sets conversion value to Fahrenheit temperature.
 * @param toK (Button) Sets conversion value to Kelvin temperature.
 * @property convertFromQuestion (String) Alert message of missing measurement unit for temperature value to be converted.
 * @property convertToQuestion (String) Alert message of missing measurement unit for temperature conversion value.
 * @property maxValue (String) Alert message of reached max input value.
 */
class Converter(
        val cTemp: TextView,
        val iTemp: EditText,
        val mTemp: TextView,
        val fromC: Button,
        val fromF: Button,
        val fromK: Button,
        val toC: Button,
        val toF: Button,
        val toK: Button
) {

    var convertFromQuestion = ""
    var convertToQuestion = ""
    var maxValue = ""

    companion object {
        /** Color grey: #555555 */
        const val GREY = "#555555"

        /** Color white: #FFFFFF */
        const val WHITE = "#FFFFFF"

        /** Kelvin constant: 273.15 */
        const val K = 273.15f
    }

    /**
     * Sets buttons [fromC], [fromF], [fromK], [toC], [toF] and [toK] state and clear text of [cTemp].
     * @param selectedFrom Turns button selected state active.
     * @param unselected1 Turns button selected state inactive.
     * @param unselected2 Turns button selected state inactive.
     * @param impossibleTo Turns button selected state inactive and disables it.
     * @param possibleTo1 Turns button selected state inactive and enables it.
     * @param possibleTo2 Turns button selected state inactive and enables it.
     */
    fun setButtonStateFrom(selectedFrom: Button, unselected1: Button, unselected2: Button,
        impossibleTo: Button, possibleTo1: Button, possibleTo2: Button) {
        val grey = Color.parseColor(GREY)
        val white = Color.parseColor(WHITE)

        selectedFrom.isSelected = true
        unselected1.isSelected = false
        unselected2.isSelected = false

        impossibleTo.isEnabled = false
        possibleTo1.isEnabled = true
        possibleTo2.isEnabled = true

        impossibleTo.isSelected = false
        possibleTo1.isSelected = false
        possibleTo2.isSelected = false

        impossibleTo.setTextColor(grey)
        possibleTo1.setTextColor(white)
        possibleTo2.setTextColor(white)

        cTemp.text = ""
    }

    /**
     * Sets buttons [toC], [toF] and [toK] state and clear text of [cTemp].
     * @param selectedTo Turns button selected state active.
     * @param unselected1 Turns button selected state inactive and disables it.
     * @param unselected2 Turns button selected state inactive and disables it.
     */
    fun setButtonStateTo(selectedTo: Button, unselected1: Button, unselected2: Button) {
        val grey = Color.parseColor(GREY)

        selectedTo.isSelected = true
        unselected1.isEnabled = false
        unselected2.isEnabled = false
        unselected1.isSelected = false
        unselected2.isSelected = false

        unselected1.setTextColor(grey)
        unselected2.setTextColor(grey)

        mTemp.text = ""
    }

    /**
     * Sets buttons [fromC], [fromF], [fromK], [toC], [toF] and [toK] to they original state.
     * @param bFrom1 Turns button selected state inactive.
     * @param bFrom2 Turns button selected state inactive.
     * @param bFrom3 Turns button selected state inactive.
     * @param bTo1 Turns button selected state inactive and disables it.
     * @param bTo2 Turns button selected state inactive and disables it.
     * @param bTo3 Turns button selected state inactive and disables it.
     */
    private fun refreshToState(bFrom1: Button, bFrom2: Button, bFrom3: Button, bTo1: Button, bTo2: Button, bTo3: Button) {
        val grey = Color.parseColor(GREY)

        bFrom1.isSelected = false
        bFrom2.isSelected = false
        bFrom3.isSelected = false

        bTo1.setTextColor(grey)
        bTo2.setTextColor(grey)
        bTo3.setTextColor(grey)
        bTo1.isSelected = false
        bTo2.isSelected = false
        bTo3.isSelected = false
        bTo1.isEnabled = false
        bTo2.isEnabled = false
        bTo3.isEnabled = false
    }

    /**
     * Sets text to [mTemp].
     * @param from Sets temperature measurement unit.
     */
    private fun mirror(from: Int?) {
        val v = iTemp.text.toString().toFloatOrNull()

        mTemp.text = if (v != null && v in -999999.99f..999999.99f) {
            when (from) {
                1 -> {
                    "${iTemp.text}°C"
                }
                2 -> {
                    "${iTemp.text}°F"
                }
                3 -> {
                    "${iTemp.text}K"
                }
                else -> {
                    "${iTemp.text}?"
                }
            }
        } else { "" }
    }

    /**
     * Converts temperature value.
     * @param value Value to be converted.
     * @param from Temperature measurement unit from value to be converted or alert message selection.
     * @param to Temperature measurement unit to converted value or alert message selection.
     */
    fun conversion(value: String, from: Int, to: Int) {
        /**
         * Temperature value or null.
         */
        val v = value.toFloatOrNull()

        /**
         * Rounds the conversion value and sets temperature measurement unit.
         * @param value Converted value.
         * @param unit Temperature measurement unit.
         */
        fun result(value: Float, unit: Int): String {
            val round = round(value * 100) / 100
            return when (unit) {
                1 -> {
                    "${round}°C"
                }
                2 -> {
                    "${round}°F"
                }
                else -> {
                    "${round}K"
                }
            }
        }

        /**
         * Sets the alert message.
         * @param msg Alert message. ("from", "to", "max" or "any")
         */
        fun msg(msg: String): String {
            return when (msg) {
                "from" -> {
                    convertFromQuestion
                }
                "to" -> {
                    convertToQuestion
                }
                "max" -> {
                    maxValue
                }
                else -> { "" }
            }
        }

        /**
         * Selects when it is an alert message or not.
         * @param isAlert Type of message that will be returned.
         * @param m Message.
         * @param size Text size of [cTemp].
         */
        fun message(isAlert: Int?, m: String, size: Float) {
            cTemp.textSize = size

           when (isAlert) {
               0 -> {
                   cTemp.text = m
                   mirror(from)
                   iTemp.editableText.clear()
               }
               1 -> {
                   cTemp.text = msg(m)
                   mirror(from)
                   iTemp.editableText.clear()
               }
               else -> {
                   cTemp.text = msg(m)
                   mirror(from)
               }
           }
        }

        /**
         * Temperature value with measurement unit. e.g. "21°C"
         */
        val r: String

        if (v != null) {
            if (v in -999999.99f..999999.99f) {
                when (from) {
                    1 -> {
                        when (to) {
                            2 -> {
                                r = result(((v * 9f) / 5f) + 32f, to)
                                message(0, r,50f)
                            }
                            3 -> {
                                r = result(v + K, to)
                                message(0, r,50f)
                            }
                            else -> {
                                message(1, "to", 24f)
                            }
                        }
                    }
                    2 -> {
                        when (to) {
                            1 -> {
                                r = result(((v - 32f) * 5f) / 9f, to)
                                message(0, r,50f)
                            }
                            3 -> {
                                r = result((((v - 32f) * 5f) / 9f) + K, to)
                                message(0, r,50f)
                            }
                            else -> {
                                message(1, "to", 24f)
                            }
                        }
                    }
                    3 -> {
                        when (to) {
                            1 -> {
                                r = result(v - K, to)
                                message(0, r,50f)
                            }
                            2 -> {
                                r = result((((v - K) * 9f) / 5) + 32, to)
                                message(0, r,50f)
                            }
                            else -> {
                                message(1, "to", 24f)
                            }
                        }
                    }
                    else -> {
                        message(null, "from", 45f)
                        mirror(null)
                    }
                }
            } else {
                message(1, "max", 35f)
            }
        } else {
            message(1, "no input", 0f)
        }

        refreshToState(fromC, fromF, fromK, toC, toF, toK)
    }
}