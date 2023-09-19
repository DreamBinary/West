package com.example.westfour01.main.recommend

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.example.westfour01.Collection
import com.example.westfour01.NovelEntity
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentDetailBinding
import com.example.westfour01.main.recommend.Novel.getChapters
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.network.ResponseMes
import com.example.westfour01.reading.ChapterFragment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody


class NovelDetailFragment(private val novelEntity: NovelEntity) : DialogFragment(), View.OnClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var request: RequestBody
    override fun onStart() {
        super.onStart()
        //设置动画、位置、宽度等属性（注意一：必须放在onStart和onResume()中设置才有效）
        val window = dialog!!.window
        if (window != null) {
            window.setDimAmount(0.6f)
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            window.setBackgroundDrawableResource(R.drawable.corner_novel_detail)
            val layoutParams = window.attributes
            layoutParams.windowAnimations = R.style.MyDialog
            //动画---------------------
            layoutParams.height = 1250
            window.attributes = layoutParams
        }
//        if (window != null) {
//            window.setBackgroundDrawableResource(R.drawable.corner_novel_detail)
//            window.attributes?.windowAnimations = R.style.MyDialog //动画
//            window.attributes?.height = 1500
//            Log.d("========================", "========================")
//        }
        //错??0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(
            inflater,
            container, false
        )
        val sharedPreferences = requireContext().getSharedPreferences("Username", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "").toString()
        val strJson = JSON.toJSON(Collection(username, novelEntity.title)).toString()
        request = RequestBody.create(MediaType.parse("application/json"), strJson)

        binding.idNovelDetailName.text = novelEntity.title
        val novelIntroduction =
            "作者 ： ${novelEntity.author}\n类型 ：${novelEntity.fictionType}\n简介 ： ${novelEntity.descs}"
        binding.idNovelIntroduction.text = novelIntroduction


        binding.idNovelNegative.setOnClickListener(this)
        binding.idNovelPositive.setOnClickListener(this)
        lifecycleScope.launch {
            coroutineScope {
                if (existCollection()) {
                    binding.idNovelCollect.isChecked = true
                }
            }
            binding.idNovelCollect.setOnCheckedChangeListener { checkBox, _ ->
                lifecycleScope.launch {
                    if (checkBox.isChecked) {
                        insertCollection()
                    } else {
                        deleteCollection()
                    }
                }
            }
        }
        SystemClock.sleep(300)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idNovelNegative ->  dismiss()
            R.id.idNovelPositive -> {
                lifecycleScope.launch {
                    getChapters(novelEntity.fictionId)?.let { chapterList ->
                        activity?.supportFragmentManager?.let {
                            ChapterFragment(chapterList).show(it, "小说章节")
                        }
                        requireContext().getSharedPreferences("Reading", 0).edit()
                            .putString("fictionId", novelEntity.fictionId)
                            .putString("novelTitle", novelEntity.title)
                            .apply()
                        dismiss()
                    }
                }
            }
        }
    }

    private suspend fun deleteCollection() {
        val result = ConnectNet.getCollectionDelete(request)
        ResponseMes.showCollectionResponse(requireContext(), result)
    }

    private suspend fun insertCollection() {
        val result = ConnectNet.getCollectResult(request)
        ResponseMes.showCollectionResponse(requireContext(), result)
    }

    private suspend fun existCollection() : Boolean{
        return ConnectNet.getCollectionExist(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}