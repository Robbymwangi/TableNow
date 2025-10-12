package com.example.tablenow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.databinding.FragmentHomeBinding
import com.example.tablenow.model.InfoCard

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFeaturedRecyclerView()
        setupNewOnBookableRecyclerView()
    }

    private fun setupFeaturedRecyclerView() {
        val featuredCards = listOf(
            InfoCard("The Talisman Restaurant", "Fine Dining · Karen", "4.8", "https://picsum.photos/400/300?random=1", true),
            InfoCard("About Thyme", "Continental · Westlands", "4.7", "https://picsum.photos/400/300?random=2", true),
            InfoCard("Tamarind Tree Hotel", "Modern African · Lang'ata", "4.9", "https://picsum.photos/400/300?random=3", true),
            InfoCard("The Carnivore", "Nyama Choma · Lang'ata", "4.5", "https://picsum.photos/400/300?random=4", true),
            InfoCard("Fogo Gaucho", "Brazilian Steakhouse · Kilimani", "4.6", "https://picsum.photos/400/300?random=5", true)
        )

        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = InfoCardAdapter(featuredCards)
        }
    }

    private fun setupNewOnBookableRecyclerView() {
        val newOnBookableCards = listOf(
            InfoCard("CJ's Restaurant", "Cafe · Koinange St", "4.6", "https://picsum.photos/400/300?random=6"),
            InfoCard("Artcaffe", "Coffee & Bakery · The Hub", "4.5", "https://picsum.photos/400/300?random=7"),
            InfoCard("Java House", "Coffee House · Mama Ngina St", "4.4", "https://picsum.photos/400/300?random=8"),
            InfoCard("Big Square", "Fast Food · Westlands", "4.2", "https://picsum.photos/400/300?random=9"),
            InfoCard("Mama Oliech's", "Local Cuisine · Hurlingham", "4.7", "https://picsum.photos/400/300?random=10")
        )

        binding.newOnBookableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = InfoCardAdapter(newOnBookableCards)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}