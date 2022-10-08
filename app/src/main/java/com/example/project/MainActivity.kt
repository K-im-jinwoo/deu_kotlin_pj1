package com.example.project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.project.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity) :

        FragmentStateAdapter(activity) {
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }


        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()


        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, postion ->
            tab.text = "Tab"
        }.attach()

        binding.mainDrawerView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu1 -> {
                    AlertDialog.Builder(this).run {
                        setTitle("24살 김진우")
                        setIcon(android.R.drawable.ic_dialog_info)
                        setMessage("21년 7월 제주도 여행 갔다옴")
                        setPositiveButton("확인", null)
                        show()
                    }
                }
                R.id.menu5 -> {
                    Toast.makeText(this@MainActivity, "축구,게임,독서입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            Log.d("kkang", "navigation item click...${it.title}")
            true
        }

        binding.btn1.setOnClickListener {
            val intent = Intent(this, mbti::class.java)
            startActivity(intent)
        }

        binding.notificationButton.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "one-channel"
                val channelName = "My Channel One"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW
                )
                //채널에 다양한 정보 설정
                channel.description = "My Channel One Description"
                channel.setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()

                channel.setSound(uri, audioAttributes)
                channel.enableLights(true)
                channel.lightColor = Color.RED
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(100, 200, 100, 200)

                //채널을 NotificationManager에 등록
                manager.createNotificationChannel(channel)

                //채널을 이용하여 builder 생성
                builder = NotificationCompat.Builder(this, channelId)
            } else {
                builder = NotificationCompat.Builder(this)
            }
            builder.setSmallIcon(R.drawable.small)
            builder.setWhen(System.currentTimeMillis())
            builder.setContentTitle("김진우")
            builder.setContentText("2022-06-11 오후 9시 7분 완성 힘들었습니다. ")
            manager.notify(11, builder.build())
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("kkang", "search text: $query")
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}