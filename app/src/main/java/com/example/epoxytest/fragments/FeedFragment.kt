package com.example.epoxytest.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ConcatAdapter
import com.example.epoxytest.R
import com.example.epoxytest.epoxy.RecyclerViewController
import com.example.epoxytest.models.Categories
import com.example.epoxytest.models.Category
import com.example.epoxytest.models.NewsItem
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.feed_fragment.view.*

@AndroidEntryPoint
class FeedFragment : Fragment(), View.OnLayoutChangeListener, OnNewsItemClickListener {

    private val viewModel : FeedViewModel by viewModels()
    private val controller = RecyclerViewController(this,this)
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.feed_fragment, container, false)

        view.main_rv.setHasFixedSize(true)
        view.main_rv.setItemViewCacheSize(20)
        view.main_rv.setControllerAndBuildModels(controller)


        view.swipe_refresh_layout.setOnRefreshListener {
            viewModel.update()
        }

        viewModel.state.observe(viewLifecycleOwner,{
            when(it){
                Loading -> view.swipe_refresh_layout.isRefreshing = true
                Success -> view.swipe_refresh_layout.isRefreshing = false
            }
        })


        viewModel.ratesItem.observe(viewLifecycleOwner,{ controller.currencyItems = it })
        viewModel.newsItem.observe(viewLifecycleOwner, {
        it.forEach{ i->
            Log.d("Test", i.toString())
        }
            controller.newsItems = it.toList() })
        viewModel.categories.observe(viewLifecycleOwner,{ controller.categories = Categories(it) })
        viewModel.toastMsg.observe(viewLifecycleOwner, {
            Toast.makeText( requireContext() ,it, Toast.LENGTH_LONG).show()
        })
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onLayoutChange(
        view: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
        if ( view is ChipGroup){
            val categoryList = view.children.toList().map { it as Chip }.map {
                Category( category = it.text as String, checked =  it.isChecked )
            }
            viewModel.saveCategories( categoryList)
        }
    }

    override fun onNewsClick(newsItem: NewsItem) {
        val action = FeedFragmentDirections.actionFeedFragmentToNewsFragment(newsItem)
        navController.navigate(action)
    }
}