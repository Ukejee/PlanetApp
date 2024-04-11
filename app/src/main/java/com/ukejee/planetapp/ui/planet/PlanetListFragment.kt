package com.ukejee.planetapp.ui.planet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.ukejee.planetapp.databinding.FragmentPlanetListBinding
import com.ukejee.planetapp.theme.PlanetAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanetListFragment : Fragment() {

    private var _binding: FragmentPlanetListBinding? = null
    private val binding get() = _binding!!

    private lateinit var  viewModel: PlanetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val baseInflater = LayoutInflater.from(requireActivity())
        _binding = FragmentPlanetListBinding.inflate(baseInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PlanetViewModel::class.java]
        binding.composeView.setContent {
            PlanetAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlanetListScreen(viewModel = viewModel)
                }
            }
        }

    }
}
