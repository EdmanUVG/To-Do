package com.example.walletsaver.ui.help

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.walletsaver.R
import com.example.walletsaver.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private lateinit var viewModel: HelpViewModel
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HelpViewModel::class.java)

        binding.layoutFaq.setOnClickListener { Log.i("@Edman", "Text") }

        binding.layoutPolicy.setOnClickListener { Log.i("@Edman", "Text") }

        binding.layoutSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.setData(Uri.parse("mailto:edmancota@gmail.com"))
            startActivity(emailIntent)
        }

        binding.layoutAppInfo.setOnClickListener {
            findNavController().navigate(R.id.action_help_fragment_to_app_info_fragment)
        }

    }

}