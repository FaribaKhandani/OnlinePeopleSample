package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.model.User
import com.example.myapplication.ui.adapter.CustomerAdapter
import com.example.myapplication.viewmodel.MainViewModel

import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var bindingFragment: FragmentHomeBinding
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private val customerAdapter = CustomerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        return bindingFragment.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerviw(bindingFragment.homefragmnetRecyclerview)
        getAllUsers()
        setOnItemClicked()


    }


    private fun setUpRecyclerviw(recyclerView: RecyclerView) {

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = customerAdapter
        recyclerView.setHasFixedSize(true)
        dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

    }


    //Fetch all users via LiveData and viewmodel
    private fun getAllUsers() {
        viewModel.getAllUsers()
        viewModel.userList.observe(viewLifecycleOwner, Observer { userResponse ->
            userResponse?.let {
                customerAdapter.users = it.customers
                customerAdapter.notifyDataSetChanged()
            }
        }
        )
    }


    private fun setOnItemClicked() {
        customerAdapter.setItemClickListener(object : CustomerAdapter.OnClickListener {
            override fun onItemClick(user: User) {
                navigateToFragmentDetail(user.id)
            }
        })
    }


    //Navigate to FragmentDetail by userId
    private fun navigateToFragmentDetail(userId: Int) {
        val action = HomeFragmentDirections.actionFragmenthomeToFragmentdetail(userId)
        findNavController().navigate(action)
    }


}

