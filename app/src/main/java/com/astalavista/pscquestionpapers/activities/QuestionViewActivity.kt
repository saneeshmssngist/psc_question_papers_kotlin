package com.astalavista.pscquestionpapers.activities

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.Session
import com.astalavista.pscquestionpapers.adapter.QuestionAnswerKeyAdapter
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.gms.ads.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.android.synthetic.main.activity_question_view.*
import kotlinx.android.synthetic.main.tool_bar_layout.*
import java.io.File

class QuestionViewActivity : AppCompatActivity() {

    lateinit var pdfQuestions: PDFView
    lateinit var progressBar: ProgressBar
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var bottomSheet: BottomSheetBehavior<LinearLayout>
    lateinit var questionData: QuestionPaper
    var isFavourite: Boolean = false
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_view)

        initViews()
        intiControls()
        checkFavourite()
        setUpAdmob()
        setUpAdmobs()
        setAnimation()

        Session.setCounter()
        Log.d("COUNTER", Session.getCounter().toString())

    }

    private fun setAnimation() {

        val inA = AlphaAnimation(0.0f, 1.0f)
        inA.duration = 700

        val out = AlphaAnimation(1.0f, 0.0f)
        out.duration = 700

        txtLoading.startAnimation(out)
        out.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                txtLoading.startAnimation(inA)

            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })

        inA.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                txtLoading.startAnimation(out)

            }

            override fun onAnimationStart(animation: Animation?) {

            }

        })


    }

    fun setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, resources.getString(R.string.APPID))

        var adMobView = findViewById(R.id.adMobView) as AdView
        adMobView.loadAd(AdRequest.Builder().build())

    }

    private fun setUpAdmobs() {

        //admob sync..
        MobileAds.initialize(this, resources.getString(R.string.APPID))

        //        adMobView = (AdView) findViewById(R.id.adMobView);
        //        adMobView.loadAd(new AdRequest.Builder().build());

        interstitialAd = InterstitialAd(this)
        interstitialAd!!.adUnitId = resources.getString(R.string.INTERTITIAID)
        interstitialAd!!.loadAd(AdRequest.Builder().build())
    }

    override fun onBackPressed() {

        if (Session.getCounter() % 5 == 0)
            setInterstitialAd()
        else {
            finish()
        }

    }

    private fun setInterstitialAd() {

        interstitialAd!!.setAdListener(object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                finish()

                interstitialAd!!.loadAd(AdRequest.Builder().build())
            }
        })

        if (interstitialAd!!.isLoaded()) {
            interstitialAd!!.show()
        } else {
            finish()
        }

    }

    private fun checkFavourite() {

        for (question in Session.getArrayData()) {
            if (question.id.contentEquals(questionData.id)) {
                isFavourite = true
                imgFavourite.setBackgroundResource(R.drawable.ic_favorite_orange)
            }
        }
    }

    private fun initViews() {

        pdfQuestions = findViewById(R.id.pdfQuestions)
        coordinatorLayout = findViewById(R.id.rootLayout)

        bottomSheet = BottomSheetBehavior.from<LinearLayout>(linearLayout)
        questionData = intent.getSerializableExtra("question_data") as QuestionPaper

        layoutBack.visibility = View.VISIBLE
        imgFavourite.visibility = View.VISIBLE
        txtHead.text = questionData.name

        recyclerViewKey.layoutManager = FlexboxLayoutManager(this, FlexDirection.COLUMN)
        recyclerViewKey.adapter = QuestionAnswerKeyAdapter(questionData.answerArray)

        if (questionData.answerArray.size == 0) {
            recyclerViewKey.visibility = View.GONE
            txtNoAnswer.visibility = View.VISIBLE
        } else {
            recyclerViewKey.visibility = View.VISIBLE
            txtNoAnswer.visibility = View.GONE
        }


    }

    private fun intiControls() {

        bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
        fabKey.setOnClickListener {
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_EXPANDED)
                    fabKey.hide()
                else if (p1 == BottomSheetBehavior.STATE_COLLAPSED || p1 == BottomSheetBehavior.STATE_HIDDEN)
                    fabKey.show()


            }
        })

        if (questionData != null) {

            setPDFView(pdfQuestions, "https://docs.google.com/uc?id=" + questionData.questionUrl)
//            Glide
//                .with(this)
//                .load("https://docs.google.com/uc?id=" + questionData.answerUrl)
//                .into(imgView)
        }

        layoutBack.setOnClickListener {
            finish()
        }

        imgFavourite.setOnClickListener {
            if (isFavourite) {
                imgFavourite.setBackgroundResource(R.drawable.ic_favorite)
                Session.removeQuestion(questionData)
                isFavourite = false
            } else {
                imgFavourite.setBackgroundResource(R.drawable.ic_favorite_orange)
                Session.addQuestion(questionData)
                isFavourite = true
            }
        }

    }


    private fun setPDFView(
        pdfQuestions: PDFView,
        question: String
    ) {

        layoutLoading.visibility = View.VISIBLE

        FileLoader.with(this)
            .load(question)
//            .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(
                    fileLoadRequest: FileLoadRequest,
                    fileResponse: FileResponse<File>
                ) {

                    layoutLoading.visibility = View.GONE

                    val pdfFile = fileResponse.getBody()
                    pdfQuestions.fromFile(pdfFile)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onDrawAll(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onPageError(object : OnPageErrorListener {
                            override fun onPageError(page: Int, t: Throwable) {
                                Toast.makeText(
                                    this@QuestionViewActivity,
                                    "Your Internet connection is slow",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                        .onPageChange(object : OnPageChangeListener {
                            override fun onPageChanged(page: Int, pageCount: Int) {
                            }
                        })
                        .onTap(object : OnTapListener {
                            override fun onTap(e: MotionEvent): Boolean {
                                return true
                            }
                        })
                        .onRender(object : OnRenderListener {
                            override fun onInitiallyRendered(
                                nbPages: Int,
                                pageWidth: Float,
                                pageHeight: Float
                            ) {
                                pdfQuestions.fitToWidth()
                            }
                        })
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load()
                }

                override fun onError(fileLoadRequest: FileLoadRequest, throwable: Throwable) {
                    Toast.makeText(this@QuestionViewActivity, "Your Internet connection is slow", Toast.LENGTH_LONG).show()
                    layoutLoading.visibility = View.GONE
                }
            })

    }

}
