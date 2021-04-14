package br.com.temperatureconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.temperatureconverter.databinding.ActivityMainBinding

/**
 * Temperature converter project
 * * Last modification: April 14th 2021.
 * @author Josélio de S. C. Júnior
 */
class MainActivity : AppCompatActivity() {
    /** viewBinding */
    private lateinit var b: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        /** Selector: From this temperature measurement unit. */
        var from = 0

        /** Selector: To this temperature measurement unit. */
        var to = 0

        /** Creates a object from Converter class.*/
        val c = Converter(
            cTemp = b.convertTemperature,
            iTemp = b.inputTemperature,
            mTemp = b.mirrorTemperature,
            fromC = b.fromC,
            fromF = b.fromF,
            fromK = b.fromK,
            toC = b.toC,
            toF = b.toF,
            toK = b.toK
        )

        c.convertToQuestion = getString(R.string.convert_to_question)
        c.convertFromQuestion = getString(R.string.convert_from_question)
        c.maxValue = getString(R.string.max_value)

        b.fromC.setOnClickListener {
            from = 1
            c.setButtonStateFrom(b.fromC, b.fromF, b.fromK, b.toC, b.toF, b.toK)
        }
        b.fromF.setOnClickListener {
            from = 2
            c.setButtonStateFrom(b.fromF, b.fromC, b.fromK, b.toF, b.toC, b.toK)
        }
        b.fromK.setOnClickListener {
            from = 3
            c.setButtonStateFrom(b.fromK, b.fromF, b.fromC, b.toK, b.toF, b.toC)
        }

        b.toC.setOnClickListener {
            to = 1
            c.setButtonStateTo(b.toC, b.toF, b.toK)
        }
        b.toF.setOnClickListener {
            to = 2
            c.setButtonStateTo(b.toF, b.toK, b.toC)
        }
        b.toK.setOnClickListener {
            to = 3
            c.setButtonStateTo(b.toK, b.toF, b.toC)
        }

        b.calculate.setOnClickListener {
          c.conversion(b.inputTemperature.text.toString(), from, to)
          from = 0
          to = 0
        }
    }
}