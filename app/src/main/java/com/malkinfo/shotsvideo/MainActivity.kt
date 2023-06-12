package com.malkinfo.shotsvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.malkinfo.shotsvideo.adapter.VideoAdapter
import com.malkinfo.shotsvideo.model.VideoModel
import com.amplitude.api.Amplitude;

class MainActivity : AppCompatActivity() {

    lateinit var viewPager2:ViewPager2
    lateinit var adapter:VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**set fullscreen*/
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
        Amplitude.getInstance().initialize(this, "3b8a1bc36e2209be432dc402c7ef9ed5").enableForegroundTracking(getApplication());
        /**set find id*/
        viewPager2 = findViewById(R.id.vpager)

        /**set database*/
        val mDataBase = Firebase.database.getReference("videos")

        val options = FirebaseRecyclerOptions.Builder<VideoModel>()
            .setQuery(mDataBase,VideoModel::class.java)
            .build()
        /**set adapter*/
        adapter = VideoAdapter(options)
        viewPager2.adapter = adapter
        Amplitude.getInstance().logEvent("Amplitude Tagging Page Entered");
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()

        Amplitude.getInstance().logEvent("Amplitude Tagging Page On started");
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()

        Amplitude.getInstance().logEvent("Amplitude Tagging Page On Stoped");
    }
   
    
}