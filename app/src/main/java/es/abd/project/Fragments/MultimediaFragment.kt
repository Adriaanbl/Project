package es.abd.project.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import es.abd.project.R
import es.abd.project.databinding.MultimediaFragmentBinding

class MultimediaFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: MultimediaFragmentBinding
    private var listener : fragmentMultimediaListener? = null
    val packageName = "es.abd.project"

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is fragmentMultimediaListener){
            listener = context
        }else {
            throw Exception("EXCEPPCION")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MultimediaFragmentBinding.inflate(inflater, container, false)

        binding.playBtn.setOnClickListener(this)
        binding.pauseBtn.setOnClickListener(this)
        binding.stopBtn.setOnClickListener(this)

        binding.loadBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View) {
        when(v.id){

            R.id.playBtn -> { listener?.onPlayAudioBtnClicked() }
            R.id.pauseBtn -> { listener?.onPauseAudioBtnClicked() }
            R.id.stopBtn -> { listener?.onStopAudioBtnClicked() }

            R.id.loadBtn -> {

                var video = getVideo(binding.videoInput.text.toString())

                binding.videoView.setVideoURI(
                    Uri.parse("android.resource://$packageName/" + video)
                )

                val mediaController = MediaController(requireContext())
                mediaController.setAnchorView(binding.videoView)
                mediaController.setMediaPlayer(binding.videoView)
                binding.videoView.setMediaController(mediaController)

                playVideo()

            }
        }

    }

    fun getVideo(video: String): Int{
        return when(video){
            "video1" -> R.raw.video1
            "video2" -> R.raw.video2
            else -> 0
        }
    }

    private fun playVideo() {
        binding.videoView.start()
    }

    interface fragmentMultimediaListener{
        fun onPlayAudioBtnClicked()
        fun onPauseAudioBtnClicked()
        fun onStopAudioBtnClicked()
    }

}