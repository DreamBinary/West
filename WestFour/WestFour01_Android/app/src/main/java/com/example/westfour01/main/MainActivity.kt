package com.example.westfour01.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.westfour01.R
import com.example.westfour01.databinding.ActivityMainBinding
import com.example.westfour01.main.recommend.ConnectDatabase
import com.example.westfour01.network.ConnectNet
import com.example.westfour01.other.Utils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var headerView: View
    private lateinit var headerImage: CircleImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        headerView = binding.idNavDrawerView.inflateHeaderView(R.layout.nav_drawer_header)
        headerImage = headerView.findViewById(R.id.idNavDrawerHeaderImage)
        val sharedPreferences = getSharedPreferences("Username", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username", "").toString()
        lifecycleScope.launch {
            initNovelDatabase()
            getImage()
        }

        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            it?.let {
                Glide.with(this).load(it).error(R.drawable.cat).into(headerImage)
                startAnim()
                lifecycleScope.launch {
                    upImage(it)
                }
            }
        }

        val pickPhoto = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let {
                Glide.with(this).load(it).into(headerImage)
                startAnim()
                lifecycleScope.launch {
                    getBitmapFromUri(it)?.let {bitmap ->
                        upImage(bitmap)
                    }
                }
            }
        }
        headerImage.setOnClickListener {
            takePhoto.launch(null)
        }
        headerImage.setOnLongClickListener(View.OnLongClickListener {
            pickPhoto.launch(arrayOf("image/*"))
            return@OnLongClickListener true
        })
        binding.idFloatBtn.setOnClickListener(this)
        binding.idNavNight.setOnClickListener(this)
        binding.idNavSettingBtn.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val navDrawerController = findNavController(R.id.idMain)
        binding.idNavDrawerView.setupWithNavController(navDrawerController)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.idFloatBtn -> {
                binding.idDrawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.idNavSettingBtn -> {
                val navController = findNavController(R.id.idMain)
                navController.navigate(R.id.idNavSettingFragment)
                binding.idDrawerLayout.closeDrawers()
            }
            R.id.idNavNight -> {
                Utils.switchMode(this)
            }
        }
    }

    private fun startAnim() {
        val anim1 = AnimationUtils.loadAnimation(this, R.anim.drawer_header_circle)
        val anim2 = AnimationUtils.loadAnimation(this, R.anim.drawer_header_circle)
        anim2.startOffset = 500
        val anim3 = AnimationUtils.loadAnimation(this, R.anim.drawer_header_circle)
        anim3.startOffset = 1000
        val anim4 = AnimationUtils.loadAnimation(this, R.anim.drawer_header_circle)
        anim4.startOffset = 1500
        val anim5 = AnimationUtils.loadAnimation(this, R.anim.drawer_header_image)
        anim5.repeatMode = Animation.REVERSE

        headerView.findViewById<ImageView>(R.id.idCircle1).startAnimation(anim1)
        headerView.findViewById<ImageView>(R.id.idCircle2).startAnimation(anim2)
        headerView.findViewById<ImageView>(R.id.idCircle3).startAnimation(anim3)
        headerView.findViewById<ImageView>(R.id.idCircle4).startAnimation(anim4)
        headerView.findViewById<CircleImageView>(R.id.idNavDrawerHeaderImage).startAnimation(anim5)
    }

    private suspend fun initNovelDatabase() {
        val list = listOf(
            "全职高手", "奥术神座", "儒道至圣", "武神", "当个法师闹革命",
            "帝霸诸天", "凡人修仙传", "星球上的完美家园", "圣徒",
            "东京文艺时代", "武神赘婿", "知北游", "踏星", "逆天宰道",
            "地球过河卒", "一剑独尊", "边月满西山", "最强战神奶爸",
            "人间守墓神", "病毒王座", "我是万劫之主", "剑来", "猎天争锋",
            "星际生存从侵略开始", "三国之最风流", "卒舞", "袭爵血路", "仙都", "驭命图"
        )
        for (title in list) {
            ConnectDatabase.insertDatabase(title, this)
        }
    }

    private suspend fun upImage(image: Bitmap) {
        val file = File(externalCacheDir, "$username.jpg")
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            image.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            bos.flush()
            bos.close()
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("uploaded_file", file.name, requestFile)
            ConnectNet.upImage(body)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private suspend fun getImage() {
        try {
            val responseBody = ConnectNet.getImage(username)
            val inputStream = responseBody.byteStream()
            val outputStream = ByteArrayOutputStream()
            val buff = ByteArray(100)
            var rc: Int
            while (inputStream.read(buff, 0, 100).also { rc = it } > 0) {
                outputStream.write(buff, 0, rc)
            }
            outputStream.toByteArray().let {
                BitmapFactory.decodeByteArray(it, 0, it.size).let { bitmap ->
                    Glide.with(this)
                        .apply { DiskCacheStrategy.NONE }
                        .load(bitmap).into(headerImage)
                }
            }
            inputStream.close()
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}