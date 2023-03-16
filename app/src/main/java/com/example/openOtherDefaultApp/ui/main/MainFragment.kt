package com.example.openOtherDefaultApp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.openOtherDefaultApp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val requestImageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("VLADISLAV", "ok")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            urlOpenButton.setOnClickListener {
                val webpage = Uri.parse(MainViewModel.DEFAULT_URL)
                val intent = Intent(Intent.ACTION_VIEW, webpage)

                val chooser = Intent.createChooser(intent, null)
                startActivity(chooser)

//              Или можно так, в данном случае, насколько я понимаю, откроет первое попавшееся
//              if (intent.resolveActivity(requireContext().packageManager) != null) {
//                  startActivity(intent)
//              }
            }
            textSendButton.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

//              Или можно так, в данном случае использует какой-то дефолтный Chooser
//              if (sendIntent.resolveActivity(requireContext().packageManager) != null) {
//                    startActivity(sendIntent)
//              }
            }
            callButton.setOnClickListener {
                val callIntent: Intent =
                    Intent(Intent.ACTION_DIAL, Uri.parse("tel:5551234"))
                val shareIntent = Intent.createChooser(callIntent, null)
                startActivity(shareIntent)
            }
            requestImageButton.setOnClickListener {
                val requestFileIntent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/jpg"
                }
                requestImageResultLauncher.launch(requestFileIntent)
            }
        }
    }
}
