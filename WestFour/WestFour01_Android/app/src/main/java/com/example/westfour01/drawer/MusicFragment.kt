package com.example.westfour01.drawer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.westfour01.Music
import com.example.westfour01.R
import com.example.westfour01.databinding.FragmentMusicBinding
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.other.Utils
import kotlinx.coroutines.launch
import java.io.IOException


class MusicFragment : DialogFragment(), View.OnClickListener, View.OnLongClickListener {
    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!
    lateinit var mediaPlayer: MediaPlayer
    lateinit var anim: Animation
    private var isListening = false



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMusicBinding.inflate(inflater, container, false)

        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.music_pic)
        lifecycleScope.launch {
            getRandMusic()
        }
        SystemClock.sleep(1000)
        //

        binding.idBackBtn.setOnClickListener(this)
        binding.idMusicPicIv.setOnClickListener(this)
        binding.idMusicPicIv.setOnLongClickListener(this)
        binding.idSearchBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.idBackBtn -> requireActivity().onBackPressed()
            R.id.idMusicPicIv -> {
                if (mediaPlayer.isPlaying) {
                    isListening = false
                    mediaPlayer.pause()
                    binding.idMusicPicIv.clearAnimation()
                } else {
                    isListening = true
                    mediaPlayer.start()
                    binding.idMusicPicIv.startAnimation(anim)
                }
            }
            R.id.idSearchBtn -> {
                Utils.showToast(requireContext(), "资源有限 暂不支持")
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        mediaPlayer.stop()
        mediaPlayer.release()
        lifecycleScope.launch {
            getRandMusic()
        }
        return true
    }

    private suspend fun getRandMusic() {
        val result = ConnectNet.getRandMusic("热歌榜")["data"]
        val music = JSON.parseObject(result.toString(), Music::class.java)
        playOnlineSound(music.url)
//        val typeface = Typeface.createFromAsset(requireActivity().assets, "font/TengXiangBoDangXingKaiJian.ttf")
        binding.idMusicName.text = music.name
//        binding.idMusicName.typeface = typeface
        binding.idMusicArtist.text = music.artistsname
//        binding.idMusicArtist.typeface = typeface
// 字体包过大
        Glide.with(requireContext())
            .load(music.picurl)
            .priority(Priority.HIGH)
            .error(R.drawable.cat)
            .into(binding.idMusicPicIv)
    }
    private fun playOnlineSound(url: String) {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                if (isListening) {
                    it.start()
                    binding.idMusicPicIv.startAnimation(anim)
                }
            }
            mediaPlayer.setOnCompletionListener {
                it.release()
                lifecycleScope.launch {
                    getRandMusic()
                }
            }
            mediaPlayer.setOnErrorListener { _, _, _ -> false }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}