package phi.saac.comunicame.ui.home

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech.Engine
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import phi.saac.comunicame.R
import phi.saac.comunicame.databinding.FragmentHomeBinding
import phi.saac.comunicame.data.DeckContactModel
import phi.saac.comunicame.ui.speech.TextToSpeechService

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var textService :TextToSpeechService? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.modelStream
            .observe(this, Observer {
                bindCard(it)
            })

        _binding?.motionLayout?.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenUnlike,
                    R.id.offScreenLike -> {
                        motionLayout.progress = 0.0f
                        motionLayout.setTransition(R.id.start, R.id.start)
                        homeViewModel.swipe()
                    }
                }
            }
        })

        _binding?.likeFloating?.setOnClickListener {
            _binding?.motionLayout?.transitionToState(R.id.like)
        }

        _binding?.unlikeFloating?.setOnClickListener {
            _binding?.motionLayout?.transitionToState(R.id.unlike)
        }
        checkTextToSpeechStatus()
        _binding?.superLikeFloating?.setOnClickListener {
            if(textService?.isTtsInitialized == true) {
                textService?.speak("Hola, buenos días")
            }

        }

        return root
    }

    private fun checkTextToSpeechStatus() {
        registerForActivityResult(StartActivityForResult())
        { result: ActivityResult ->
            if (Engine.CHECK_VOICE_DATA_PASS == result.resultCode) {
                // success, create the TTS instance
                initializeTextToSpeech()
            } else {
                // missing data, install it
                installTextToSpeechLibrary()
            }


        }.launch(Intent(Engine.ACTION_CHECK_TTS_DATA))
    }
    private fun installTextToSpeechLibrary() {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Engine.CHECK_VOICE_DATA_PASS) {
                initializeTextToSpeech()
            }

        }.launch(Intent(Engine.ACTION_INSTALL_TTS_DATA))
    }

    private fun initializeTextToSpeech() {
        textService = TextToSpeechService(requireContext())
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onPause() {
        super.onPause()
        if(textService!=null) {
            textService?.onPause()
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if(textService!=null) {
            textService?.onDetach()
        }
    }

    private fun bindCard(model: DeckContactModel) {
        _binding?.iconCardOne?.setImageResource(model.cardTop.icon)
        _binding?.iconCardOne?.background= resources.getDrawable(model.cardTop.backgroundColor, null)
        _binding?.nameOne?.text = "${model.cardTop.name}"

        //_binding?.iconCardTwo?.setImageResource(model.cardBottom.icon)
        //_binding?.iconCardTwo?.background= resources.getDrawable(model.cardBottom.backgroundColor, null)
        //_binding?.nameTwo?.text = "${model.cardBottom.name}"

    }
}