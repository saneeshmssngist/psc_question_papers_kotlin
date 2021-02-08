package com.astalavista.pscquestionpapers.activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.Session
import com.astalavista.pscquestionpapers.fragments.*
import com.astalavista.pscquestionpapers.retrofit.DataManager
import com.astalavista.pscquestionpapers.retrofit.RetrofitCallBack
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), BottomSheetEx.BottomSheetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        DataManager.getDatamanager().init(this)
        Session.getSession(this)

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, homeFragment, homeFragment.javaClass.simpleName).commit()

        initViews()
        setUpAdmob()

        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("psc_question_papers")

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult: InstanceIdResult ->
            val newToken = instanceIdResult.token
            Log.e("newToken", newToken)
            getPreferences(Context.MODE_PRIVATE).edit()
                .putString("fb", newToken).apply()
        }

        checkVersion()

    }

    private fun checkVersion() {

        DataManager.getDatamanager().checkVersion(object : RetrofitCallBack<String> {
            override fun Success(data: String) {

                try {
                    val pInfo: PackageInfo =
                        getPackageManager().getPackageInfo(packageName, 0)
                    val version = pInfo.versionName

                    if (data.toFloat() > version.toFloat()) {
                        val bottomSheet = BottomSheetEx()
                        bottomSheet.show(supportFragmentManager, "BottomSheetEx")
                    }

                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
            }

            override fun Failure(data: String) {
            }
        })

    }

    fun setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, resources.getString(R.string.APPID))

        var adMobView = findViewById(R.id.adMobView) as AdView
        adMobView.loadAd(AdRequest.Builder().build())

    }

    private fun initViews() {

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment(), "home").commit()
                }
                R.id.syllabus -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SyllabusWiseFragment(), "syallabus").commit()
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.frameLayout,
                        SearchFragment(), "search"
                    ).commit()
                }
                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavouriteFragment(), "Favourite").commit()

                }
            }

            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onOkClicked() {

        val appPackageName =
            packageName // getPackageName() from Context or Activity object

        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    override fun onCancelClicked() {

    }
}
