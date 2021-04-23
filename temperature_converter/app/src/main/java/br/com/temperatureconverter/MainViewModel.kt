package br.com.temperatureconverter

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel

/**
 * Temperature converter project
 * * Last modification: April 23th 2021.
 * @author Josélio de S. C. Júnior
 */
class MainViewModel: ViewModel() {

    companion object {
        const val ENABLED = "#FFFFFF"
        const val DISABLED = "#555555"
    }

    private var bFromC = false
    private var bFromF = false
    private var bFromK = false
    private var bToCS = false
    private var bToFS = false
    private var bToKS = false
    private var bToCE = false
    private var bToFE = false
    private var bToKE = false
    private var btnTCColor = DISABLED
    private var btnTFColor = DISABLED
    private var btnTKColor = DISABLED
    private var tempFrom = ""
    private var tempConverted = ""

    private fun textColorBtnFromState() {
        when {
            bFromC -> {
                btnTCColor = DISABLED
                btnTFColor = ENABLED
                btnTKColor = ENABLED
            }
            bFromF -> {
                btnTFColor = DISABLED
                btnTCColor = ENABLED
                btnTKColor = ENABLED
            }
            bFromK -> {
                btnTKColor = DISABLED
                btnTFColor = ENABLED
                btnTCColor = ENABLED
            }
        }
    }

    private fun textColorBtnToState() {
        when {
            bToCS -> {
                btnTFColor = DISABLED
                btnTKColor = DISABLED
            }
            bToFS -> {
                btnTCColor = DISABLED
                btnTKColor = DISABLED
            }
            bToKS -> {
                btnTFColor = DISABLED
                btnTCColor = DISABLED
            }
        }
    }

    private fun fromStates(fromC: Boolean, fromF: Boolean, fromK: Boolean) {
        bFromC = fromC
        bFromF = fromF
        bFromK = fromK

        when {
            fromC -> {
                bToCE = false
                bToFE = true
                bToKE = true
                textColorBtnFromState()
            }
            fromF -> {
                bToCE = true
                bToFE = false
                bToKE = true
                textColorBtnFromState()
            }
            fromK -> {
                bToCE = true
                bToFE = true
                bToKE = false
                textColorBtnFromState()
            }
        }
        bToCS = false
        bToFS = false
        bToKS = false
    }

    private fun toSelectionStates(toC: Boolean, toF: Boolean, toK: Boolean) {
        bToCS = toC
        bToFS = toF
        bToKS = toK

        when {
            toC -> {
                bToFE = false
                bToKE = false
                textColorBtnToState()
            }
            toF -> {
                bToCE = false
                bToKE = false
                textColorBtnToState()
            }
            toK -> {
                bToCE = false
                bToFE = false
                textColorBtnToState()
            }
        }
    }

    fun buttonFromState(id: Int) {

        when (id) {
            R.id.buttonfc -> {
                fromStates(
                        fromC = true,
                        fromF = false,
                        fromK = false
                )
            }
            R.id.buttonff -> {
                fromStates(
                        fromC = false,
                        fromF = true,
                        fromK = false
                )
            }
            R.id.buttonfk -> {
                fromStates(
                        fromC = false,
                        fromF = false,
                        fromK = true
                )
            }
        }
    }

    fun buttonToState(id: Int) {

        when (id) {
            R.id.buttontc -> {
                toSelectionStates(
                        toC = true,
                        toF = false,
                        toK = false
                )
            }
            R.id.buttontf -> {
                toSelectionStates(
                        toC = false,
                        toF = true,
                        toK = false
                )
            }
            R.id.buttontk -> {
                toSelectionStates(
                        toC = false,
                        toF = false,
                        toK = true
                )
            }
        }
    }

    fun conversionValues(input: String?, msg: Messages) {
        val converter = Converter()
        val from: Int
        val to: Int

        tempConverted = when {
            bFromC -> {
                from = R.id.buttonfc
                tempFrom = converter.tempFromMirror(input, from)

                when {
                    bToFS -> {
                        to = R.id.buttontf
                        converter.conversion(input, from, to, msg)
                    }
                    bToKS -> {
                        to = R.id.buttontk
                        converter.conversion(input, from, to, msg)
                    }
                    else -> {
                        converter.conversion(input, from,null, msg)
                    }
                }
            }
            bFromF -> {
                from = R.id.buttonff
                tempFrom = converter.tempFromMirror(input, from)

                when {
                    bToCS -> {
                        to = R.id.buttontc
                        converter.conversion(input, from, to, msg)
                    }
                    bToKS -> {
                        to = R.id.buttontk
                        converter.conversion(input, from, to, msg)
                    }
                    else -> {
                        converter.conversion(input, from,null, msg)
                    }
                }
            }
            bFromK -> {
                from = R.id.buttonfk
                tempFrom = converter.tempFromMirror(input, from)

                when {
                    bToFS -> {
                        to = R.id.buttontf
                        converter.conversion(input, from, to, msg)
                    }
                    bToCS -> {
                        to = R.id.buttontc
                        converter.conversion(input, from, to, msg)
                    }
                    else -> {
                        converter.conversion(input, from,null, msg)
                    }
                }
            }
            else -> {
                tempFrom = ""
                converter.conversion(input, null, null, msg)
            }
        }
    }

    fun currentState(
            btnfc: Button, btnff: Button, btnfk: Button,
            btntc: Button, btntf: Button, btntk: Button,
            mirrorTemp: TextView, convertTemp: TextView
    ) {
        btnfc.isSelected = bFromC
        btnff.isSelected = bFromF
        btnfk.isSelected = bFromK

        btntc.run {
            isSelected = bToCS
            isEnabled = bToCE
            setTextColor(Color.parseColor(btnTCColor))
        }
        btntf.run {
            isSelected = bToFS
            isEnabled = bToFE
            setTextColor(Color.parseColor(btnTFColor))
        }
        btntk.run {
            isSelected = bToKS
            isEnabled = bToKE
            setTextColor(Color.parseColor(btnTKColor))
        }

        mirrorTemp.text = tempFrom
        convertTemp.text = tempConverted
    }
}