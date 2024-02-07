package es.abd.project.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.abd.project.R
import es.abd.project.databinding.LoginFragmentBinding
import es.abd.project.databinding.RetrofitFragmentBinding

class RetrofitFragment : Fragment() {

    private lateinit var binding: RetrofitFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RetrofitFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

}