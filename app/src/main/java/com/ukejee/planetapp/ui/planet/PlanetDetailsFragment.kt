package com.ukejee.planetapp.ui.planet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.ukejee.planetapp.databinding.FragmentPlanetDetailsBinding
import com.ukejee.planetapp.theme.PlanetAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlanetDetailsFragment : Fragment() {

    private var _binding: FragmentPlanetDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var  viewModel: PlanetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseInflater = LayoutInflater.from(requireActivity())
        _binding = FragmentPlanetDetailsBinding.inflate(baseInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.composeView.setContent {
            PlanetAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PlanetDetailsScreen(viewModel = viewModel)
                }
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[PlanetViewModel::class.java]
        viewModel.onBackPressed = { requireActivity().onBackPressedDispatcher.onBackPressed() }
    }

}