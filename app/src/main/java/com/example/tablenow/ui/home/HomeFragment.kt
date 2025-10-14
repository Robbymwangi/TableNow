package com.example.tablenow.ui.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tablenow.R
import com.example.tablenow.adapter.CategoryAdapter
import com.example.tablenow.adapter.InfoCardAdapter
import com.example.tablenow.adapter.SkeletonAdapter
import com.example.tablenow.databinding.FragmentHomeBinding
import com.example.tablenow.model.Category
import com.example.tablenow.model.InfoCard
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // For profile avatar
    private var profileAvatarImageView: ShapeableImageView? = null

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

        // 1. RecyclerViews + skeleton loaders
        setupRecyclerViews()

        // 2. Status bar insets: ensure expanded title is pushed below the status bar
        val headerContainer = view.findViewById<View>(R.id.headerContainer)
        headerContainer?.let { hc ->
            ViewCompat.setOnApplyWindowInsetsListener(hc) { v, insets ->
                val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                val extraTop = dpToPx(8) // a little breathing room
                v.updatePadding(top = statusBarHeight + extraTop)
                insets
            }
            hc.requestApplyInsets()
        }

        // 3. AppBar scroll behavior: cross-fade between expandedTitle (center) and CollapsingToolbarLayout title (collapsed / left)
        val appBar = view.findViewById<AppBarLayout>(R.id.appBarLayout)
        val expandedTitle = view.findViewById<TextView>(R.id.expandedTitle)
        val collapsing = view.findViewById<com.google.android.material.appbar.CollapsingToolbarLayout>(R.id.collapsingToolbarLayout)

        // ensure no collapsed title initially
        collapsing.title = " " // blank while expanded

        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalRange = appBarLayout.totalScrollRange.takeIf { it > 0 } ?: 1
            val collapseFraction = abs(verticalOffset).toFloat() / totalRange.toFloat()

            // When mostly collapsed: show toolbar title and hide expanded title (fade)
            if (collapseFraction >= 0.65f) {
                // Set the collapsing toolbar title (left, collapsed)
                if (collapsing.title == " ") {
                    collapsing.title = "Discover places and restaurants"
                }
                // fade out expanded title
                if (expandedTitle.alpha > 0f) {
                    expandedTitle.animate().alpha(0f).setDuration(150).start()
                }
            } else {
                // Expanded: clear collapsed title and fade in expanded title
                if (collapsing.title != " ") {
                    collapsing.title = " "
                }
                if (expandedTitle.alpha < 1f) {
                    expandedTitle.animate().alpha(1f).setDuration(150).start()
                }
            }
        })

        // 4. Bottom navigation setup
        setupBottomNavigation()
    }

    private fun setupRecyclerViews() {
        val categories = listOf(
            Category("Pizza", R.drawable.ic_pizza_slice),
            Category("Burger", R.drawable.ic_burger_outline),
            Category("Vegan", R.drawable.ic_leaf_outline),
            Category("Greek", R.drawable.ic_greek_columns_outline)
        )
        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = CategoryAdapter(categories)
        }

        binding.featuredRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = SkeletonAdapter(5)
        }
        binding.newOnBookableRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = SkeletonAdapter(5)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val onCardClick = { card: InfoCard, imageView: ImageView ->
                val extras = FragmentNavigatorExtras(imageView to imageView.transitionName)
                findNavController().navigate(
                    R.id.action_nav_home_to_restaurantDetailFragment,
                    null,
                    null,
                    extras
                )
            }

            val featuredCards = listOf(
                InfoCard("The Talisman Restaurant", "Fine Dining · Karen", "4.8", "https://picsum.photos/400/300?random=1", true),
                InfoCard("About Thyme", "Continental · Westlands", "4.7", "https://picsum.photos/400/300?random=2", true),
                InfoCard("Tamarind Tree Hotel", "Modern African · Lang'ata", "4.9", "https://picsum.photos/400/300?random=3", true),
                InfoCard("The Carnivore", "Nyama Choma · Lang'ata", "4.5", "https://picsum.photos/400/300?random=4", true),
                InfoCard("Fogo Gaucho", "Brazilian Steakhouse · Kilimani", "4.6", "https://picsum.photos/400/300?random=5", true)
            )
            binding.featuredRecyclerView.adapter = InfoCardAdapter(featuredCards, onCardClick)

            val newOnBookableCards = listOf(
                InfoCard("CJ's Restaurant", "Cafe · Koinange St", "4.6", "https://picsum.photos/400/300?random=6"),
                InfoCard("Artcaffe", "Coffee & Bakery · The Hub", "4.5", "https://picsum.photos/400/300?random=7"),
                InfoCard("Java House", "Coffee House · Mama Ngina St", "4.4", "https://picsum.photos/400/300?random=8"),
                InfoCard("Big Square", "Fast Food · Westlands", "4.2", "https://picsum.photos/400/300?random=9"),
                InfoCard("Mama Oliech's", "Local Cuisine · Hurlingham", "4.7", "https://picsum.photos/400/300?random=10")
            )
            binding.newOnBookableRecyclerView.adapter = InfoCardAdapter(newOnBookableCards, onCardClick)
        }, 2000)
    }

    private fun setupBottomNavigation() {
        val navController = findNavController()
        customizeProfileTab(hasAvatar = false, initials = "JH")

        binding.bottomNavView.setOnItemSelectedListener { item ->
            profileAvatarImageView?.let { imageView ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        updateProfileTabState(imageView, false)
                        true
                    }
                    R.id.nav_search, R.id.nav_bookings -> {
                        updateProfileTabState(imageView, false)
                        navController.navigate(item.itemId)
                        true
                    }
                    R.id.nav_profile -> {
                        updateProfileTabState(imageView, true)
                        navController.navigate(item.itemId)
                        true
                    }
                    else -> false
                }
            } ?: false
        }
    }

    private fun customizeProfileTab(hasAvatar: Boolean, initials: String) {
        val menuView = binding.bottomNavView.getChildAt(0) as BottomNavigationMenuView
        val profileItem = menuView.getChildAt(menuView.childCount - 1) as BottomNavigationItemView
        val customLayout = LayoutInflater.from(context).inflate(R.layout.item_profile_nav_tab, profileItem, false)

        val profileAvatar: ShapeableImageView = customLayout.findViewById(R.id.profile_avatar_image)
        val profileLabel: TextView = customLayout.findViewById(R.id.profile_label)
        profileLabel.text = "Profile"

        if (hasAvatar) {
            profileAvatar.setImageResource(R.drawable.ic_user_profile)
        } else {
            val drawableSize = dpToPx(24)
            profileAvatar.setImageDrawable(createInitialsDrawable(initials, drawableSize))
        }

        profileItem.removeAllViews()
        profileItem.addView(customLayout)

        profileAvatarImageView = profileAvatar

        val isProfileTabSelected = binding.bottomNavView.selectedItemId == R.id.nav_profile
        updateProfileTabState(profileAvatar, isProfileTabSelected)
    }

    private fun updateProfileTabState(profileImageView: ShapeableImageView, isSelected: Boolean) {
        if (isSelected) {
            profileImageView.strokeWidth = dpToPx(2).toFloat()
            profileImageView.strokeColor = ContextCompat.getColorStateList(requireContext(), R.color.navy_blue)
        } else {
            profileImageView.strokeWidth = 0f
        }
    }

    private fun createInitialsDrawable(initials: String, sizePx: Int): BitmapDrawable {
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        paint.color = ContextCompat.getColor(requireContext(), R.color.grey_300)
        canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f, paint)

        paint.color = ContextCompat.getColor(requireContext(), R.color.almost_black)
        paint.textSize = (sizePx / 2.5f)
        paint.textAlign = Paint.Align.CENTER

        val textBounds = Rect()
        paint.getTextBounds(initials, 0, initials.length, textBounds)
        val textHeight = textBounds.height()
        val textY = canvas.height / 2f + textHeight / 2f - textBounds.bottom
        canvas.drawText(initials, sizePx / 2f, textY, paint)

        return BitmapDrawable(resources, bitmap)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        profileAvatarImageView = null
    }
}
