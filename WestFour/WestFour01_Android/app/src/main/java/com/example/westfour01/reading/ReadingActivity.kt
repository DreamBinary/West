package com.example.westfour01.reading

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.example.westfour01.Music
import com.example.westfour01.R
import com.example.westfour01.databinding.ActivityReadingBinding
import com.example.westfour01.databinding.ReadingPopBinding
import com.example.westfour01.main.MainActivity
import com.example.westfour01.main.recommend.Novel
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.other.Utils
import kotlinx.coroutines.launch
import java.io.IOException


class ReadingActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private lateinit var binding: ActivityReadingBinding
    private lateinit var bindingPop: ReadingPopBinding
    private lateinit var mPopWindow: PopupWindow
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var pref: SharedPreferences
    private var lastColorChecked: Int = 0

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadingBinding.inflate(layoutInflater)
        hideSystemUI()
        setContentView(binding.root)
        pref = getSharedPreferences("Reading", MODE_PRIVATE)

        bindingPop = ReadingPopBinding.inflate(layoutInflater)
        mPopWindow = PopupWindow(bindingPop.root)
        mediaPlayer = MediaPlayer()


        val seekBarBright = bindingPop.idBrightnessSeekBar
        seekBarBright.max = 255
        val screenBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        seekBarBright.progress = pref.getInt("brightness", screenBrightness)
        changeBright(seekBarBright.progress)


        val seekBarFont = bindingPop.idFontSeekBar
        seekBarFont.min = 50
        seekBarFont.max = 125
        seekBarFont.progress = pref.getInt("textSize", binding.idContentTv.textSize.toInt())
        changeTextSize(seekBarFont.progress)

        binding.idContentTv.text = pref.getString("content", "")
        lastColorChecked = pref.getInt("lastColorChecked", 0)
        if (lastColorChecked != 4) {
            changeBackground(lastColorChecked)
        }
        binding.idFloatBtn.setOnClickListener(this)
        binding.idContentTv.setOnClickListener(this)
        bindingPop.idPopMusic.setOnClickListener(this)
        bindingPop.idPopContent.setOnClickListener(this)
        seekBarBright.setOnSeekBarChangeListener(this)
        seekBarFont.setOnSeekBarChangeListener(this)

    }

    private fun Activity.hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.getWindowInsetsController(window.decorView)?.let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.idFloatBtn, R.id.idContentTv -> {
                if (mPopWindow.isShowing) {
                    mPopWindow.dismiss()
                } else {
                    popWindow()
                }
            }
            R.id.idPopMusic -> {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                } else {
                    lifecycleScope.launch {
                        getRandMusic()
                    }
                }
            }
            R.id.idPopContent -> {
                val fictionId = pref.getString("fictionId", "")
                lifecycleScope.launch {
                    Novel.getChapters(fictionId!!)?.let { chapterList ->
                        supportFragmentManager.let {
                            ChapterFragment(chapterList).show(it, "小说章节")
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun popWindow() {
//        theme.obtainStyledAttributes(android.R.attr.colorPrimary)
//        resources.getColor(android.R.attr.colorPrimary, theme)
        val radioList = listOf(
            bindingPop.idColorBtn0,
            bindingPop.idColorBtn1,
            bindingPop.idColorBtn2,
            bindingPop.idColorBtn3,
            bindingPop.idNightBtn
        )
        radioList[lastColorChecked].isChecked = true
        mPopWindow.width = ViewGroup.LayoutParams.MATCH_PARENT
        mPopWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mPopWindow.isFocusable = false
        mPopWindow.isOutsideTouchable = false
        mPopWindow.showAtLocation(bindingPop.root, Gravity.BOTTOM, 0, 0)

        bindingPop.idColorGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                radioList[0].id -> {
                    changeBackground(0)
                }
                radioList[1].id -> {
                    changeBackground(1)
                }
                radioList[2].id -> {
                    changeBackground(2)
                }
                radioList[3].id -> {
                    changeBackground(3)
                }
                radioList[4].id -> {
                    changeBackground(4)
                }
            }
        }
    }

    private fun changeBackground(int: Int) {
        when (int) {
            0, 1, 2, 3 -> {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    val edit = getSharedPreferences("Reading", MODE_PRIVATE).edit()
                    edit.putInt("lastColorChecked", int)
                    edit.apply()
                    mPopWindow.dismiss()
                    Utils.switchMode(this)
                } else {
                    val colorList = listOf(
                        Color.rgb(163, 163, 163),
                        Color.rgb(138, 156, 137),
                        Color.rgb(172, 140, 154),
                        Color.rgb(153, 190, 197),
                        Color.rgb(10, 10, 10)
                    )
                    binding.root.setBackgroundColor(colorList[int])
                    bindingPop.root.setBackgroundColor(colorList[int])
                    lastColorChecked = int
                }
            }
            4 -> {
                val edit = getSharedPreferences("Reading", MODE_PRIVATE).edit()
                edit.putInt("lastColorChecked", 4)
                edit.apply()
                mPopWindow.dismiss()
                Utils.switchMode(this)
            }
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            R.id.idBrightnessSeekBar -> {
                changeBright(progress)
            }
            R.id.idFontSeekBar -> {
                changeTextSize(progress)
            }
        }
    }

    private fun changeTextSize(progress: Int) {
        binding.idContentTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, progress.toFloat())
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        when (seekBar?.id) {
            R.id.idBrightnessSeekBar -> {
                pref.edit().putInt("brightness", seekBar.progress).apply()
            }
            R.id.idFontSeekBar -> {
                pref.edit().putInt("textSize", seekBar.progress).apply()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        getSharedPreferences("Reading", 0).edit().putInt("lastColorChecked", lastColorChecked)
            .apply()
        super.onBackPressed()
    }

    private fun changeBright(progress: Int) {
        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.screenBrightness = progress.toFloat() / 255
        window.attributes = layoutParams
        hideSystemUI()
    }

    private suspend fun getRandMusic() {
        val result = ConnectNet.getRandMusic("热歌榜")["data"]
        val music = JSON.parseObject(result.toString(), Music::class.java)
        playOnlineSound(music.url)
    }

    private fun playOnlineSound(url: String) {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { it.start() }
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

    override fun onDestroy() {
        super.onDestroy()
        mPopWindow.dismiss()
        mediaPlayer.release()
    }

}