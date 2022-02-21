package phi.saac.comunicame.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import phi.saac.comunicame.R
import phi.saac.comunicame.databinding.FragmentHomeBinding
import phi.saac.comunicame.ui.home.HomeViewModel
import phi.saac.comunicame.ui.scenes.TinderContactModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

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

        homeViewModel
            .modelStream
            .observe(this, Observer {
                bindCard(it)
            })

        _binding?.motionLayout?.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (currentId) {
                    R.id.offScreenUnlike,
                    R.id.offScreenLike -> {
                        motionLayout.progress = 0f
                        motionLayout.setTransition(R.id.start, R.id.detail)
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindCard(model: TinderContactModel) {
        //_binding?.containerCardOne?.setBackgroundColor(model.cardTop.backgroundColor)
        _binding?.name?.text = "${model.cardTop.name}"
        //_binding?.containerCardTwo?.setBackgroundColor(model.cardBottom.backgroundColor)
    }
}