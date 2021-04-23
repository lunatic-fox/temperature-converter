package br.com.temperatureconverter


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.temperatureconverter.databinding.ActivityMainBinding

/**
 * Temperature converter project
 * * Last modification: April 23th 2021.
 * @author Josélio de S. C. Júnior
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initButtons()
    }

    private fun initButtons() {
        val messages = Messages(
                fromQuestion = getString(R.string.convert_from_question),
                toQuestion = getString(R.string.convert_to_question),
                maxValue = getString(R.string.max_value)
        )

        fun state () {
            binding.run {
                viewModel.currentState(
                        buttonfc, buttonff, buttonfk,
                        buttontc, buttontf, buttontk,
                        mirrorTemp, convertTemp
                )
            }
        }
        state()


        viewModel.run {
            binding.run {
                buttonfc.setOnClickListener {
                    buttonFromState(R.id.buttonfc)
                    state()
                }
                buttonff.setOnClickListener {
                    buttonFromState(R.id.buttonff)
                    state()
                }
                buttonfk.setOnClickListener {
                    buttonFromState(R.id.buttonfk)
                    state()
                }
                buttontc.setOnClickListener {
                    buttonToState(R.id.buttontc)
                    state()
                }
                buttontf.setOnClickListener {
                    buttonToState(R.id.buttontf)
                    state()
                }
                buttontk.setOnClickListener {
                    buttonToState(R.id.buttontk)
                    state()
                }
                calculate.setOnClickListener {
                    if (inputTemperature.editableText.isNotEmpty()) {
                        conversionValues(inputTemperature.editableText.toString(), messages)
                    } else {
                        conversionValues(null, messages)
                        Toast.makeText(
                                this@MainActivity,
                                getString(R.string.no_input),
                                Toast.LENGTH_SHORT).show()
                    }
                    state()
                }
            }
        }
    }
}