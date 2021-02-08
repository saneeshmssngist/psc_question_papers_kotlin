package com.astalavista.pscquestionpapers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.Session
import com.astalavista.pscquestionpapers.activities.QuestionViewActivity
import com.astalavista.pscquestionpapers.adapter.FavouriteAdapter
import com.astalavista.pscquestionpapers.models.QuestionPaper
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_favourite.view.*
import kotlinx.android.synthetic.main.tool_bar_layout.view.*

class FavouriteFragment : Fragment()
{

    lateinit var viewss : View
    lateinit var favouriteAdapter : FavouriteAdapter
    lateinit var favouriteArray : ArrayList<QuestionPaper>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewss = inflater.inflate(R.layout.fragment_favourite,null)
        initViews()

        return viewss
    }

    private fun initViews() {

        viewss.layoutBack.visibility = View.GONE
        viewss.txtHead.text = "Favourites"
        viewss.recyclerViewFav.layoutManager = GridLayoutManager(activity,2)

    }

    override fun onResume() {
        super.onResume()

        favouriteArray = Session.getArrayData()

        if(favouriteArray.size == 0)
            txtNoData.visibility = View.VISIBLE
        else
            txtNoData.visibility = View.GONE

        favouriteAdapter = FavouriteAdapter(favouriteArray,object :
            FavouriteAdapter.OnFavouriteListener{
            override fun onFavouriteTapped(position: Int) {

                startActivity(Intent(activity, QuestionViewActivity::class.java).putExtra("question_data",
                    favouriteArray[position]
                ))
            }
        })
        viewss.recyclerViewFav.adapter = favouriteAdapter

    }


}