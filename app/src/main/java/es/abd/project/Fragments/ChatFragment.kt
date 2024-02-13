package es.abd.project.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.abd.project.R
import es.abd.project.databinding.ChatFragmentBinding
import es.abd.project.databinding.RetrofitFragmentBinding

class ChatFragment : Fragment() {

    private lateinit var binding: ChatFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

}