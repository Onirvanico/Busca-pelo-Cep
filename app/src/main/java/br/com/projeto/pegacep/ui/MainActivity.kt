package br.com.projeto.pegacep.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import br.com.projeto.pegacep.core.createDialog
import br.com.projeto.pegacep.core.createProgressDialog
import br.com.projeto.pegacep.data.model.Content
import br.com.projeto.pegacep.databinding.ActivityMainBinding
import br.com.projeto.pegacep.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val progressDialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        validateCepField()

        configViewModel()

        configButton()
    }

    private fun configButton() {
        binding.button.setOnClickListener {
            viewModel.retrieveCep(binding.textInputCep.editableText.toString())
        }
    }

    private fun configViewModel() {
        viewModel.state.observe(this) {
            when (it) {
                is MainViewModel.State.Load -> {
                    progressDialog.show()
                }
                is MainViewModel.State.Failure -> {
                    progressDialog.dismiss()
                    createDialog {
                        this.setMessage(it.ex.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    progressDialog.dismiss()
                    binding.textResult.text = formatResult(it.content)
                }
            }
        }
    }

    private fun validateCepField() {
        val fullPattern = Regex("\\d{8}")

        binding.textInputCep.addTextChangedListener { watcher ->
            binding.textResult.text = watcher.toString()
            if (fullPattern.containsMatchIn(watcher.toString())) {
                binding.button.setEnabled(true)
            } else
                binding.button.setEnabled(false)

        }
    }

    private fun formatResult(content: Content): String {

        Log.i("RESULT", "formatResult: " + content.toString())
        return String.format(
            "Endereço: %s \n " +
                    "Bairro: %s \n " +
                    "Cidade: %s \n " +
                    "Latitude: %s \n " +
                    "Longitude: %s \n " +
                    "DDD: %s \n",
            content.address,
            content.distric,
            content.city,
            content.lat,
            content.lng,
            content.ddd
        )
    }
}