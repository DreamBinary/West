package com.example.westfour01.main

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.westfour01.NovelEntity
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentMainBinding
import com.example.westfour01.main.recommend.ConnectDatabase
import com.example.westfour01.main.recommend.NovelDatabase
import com.example.westfour01.main.recommend.NovelDetailFragment
import com.example.westfour01.other.Utils
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,
            container, false)
//        initData()
//        initBottomNavigation()

        binding.idSearchBtn.setOnClickListener(this)
        binding.idNavDrawerMenu.setOnClickListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(requireActivity(), R.id.idNavBottomHostFragment)
        binding.idNavBottom.setupWithNavController(navController)
    }

//    private fun initData() {
//        mFragments = ArrayList()
//        mFragments.add(RecommendFragment())
//        mFragments.add(BookstoreFragment())
//        // 初始化展示MessageFragment
//        setFragmentPosition(0)
//    }

//    private fun initBottomNavigation() {
//        binding.idNavBottom.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
//            when(it.itemId) {
//                R.id.idNavBottomRecommend -> setFragmentPosition(0)
//                R.id.idNavBottomBookstore -> setFragmentPosition(1)
//            }
//            return@OnItemSelectedListener true
//        })
//    }

//    private fun setFragmentPosition(position: Int) {
//        val ft = requireActivity().supportFragmentManager.beginTransaction()
//        val currentFragment = mFragments[position]
//        val lastFragment = mFragments[lastIndex]
//        lastIndex = position
//        ft.hide(lastFragment)
//        if (!currentFragment.isAdded) {
//            requireActivity().supportFragmentManager.beginTransaction().remove(currentFragment).commit()
//            ft.add(R.id.idNavBottomHostFragment, currentFragment)
//        }
//        ft.show(currentFragment)
//        ft.commitAllowingStateLoss()
//    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idNavDrawerMenu -> {
                val drawer = requireActivity().findViewById<DrawerLayout>(R.id.id_drawerLayout)
                drawer.openDrawer(GravityCompat.START)
            }
            R.id.idSearchBtn -> lifecycleScope.launch {
                getSearch()
            }
        }
    }

    private suspend fun getSearch() {
        val titleSearch = binding.idSearchEv.text.toString().trim()
        if (titleSearch != "") {
            var novelEntity: NovelEntity?
            context?.let { ConnectDatabase.insertDatabase(titleSearch, it) }
            thread {
                SystemClock.sleep(5000)
                novelEntity = NovelDatabase.getDatabase(requireContext())
                    .novelDao().findByTitle(titleSearch)
                if (novelEntity != null) {
                    requireActivity().supportFragmentManager.let {
                        NovelDetailFragment(novelEntity!!).show(it, "小说细节") }
                } else {
                    requireActivity().runOnUiThread {
                        Utils.showToast(requireContext(), "能力有限，搜索不到此小说")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}