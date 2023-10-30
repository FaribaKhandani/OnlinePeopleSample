package com.example.myapplication.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.DetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint



/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


@AndroidEntryPoint
class DetailFragment : Fragment() ,OnMapReadyCallback{

    private lateinit var detailBinding: FragmentDetailBinding
    private val detailViewModel:DetailViewModel by viewModels()
    private var userId :Int = 0
    private lateinit var googleMap: GoogleMap

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        return detailBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgumentID()
        backButtonToHomeFragment()

        detailBinding.mapsview.onCreate(savedInstanceState)
        detailBinding.mapsview.onResume()
        detailBinding.mapsview.getMapAsync(this)
    }

    private fun getArgumentID(){
        userId =requireArguments().getInt("id")

        // Fetch user details using the ViewModel by UserId
        detailViewModel.fetchUsersDetails(userId)



        detailViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            // Update the UI with the user details
            user = it
            updateUserDetails(it)

        })
    }





    //Back to HomeFragment

    private fun backButtonToHomeFragment(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_fragmentdetail_to_fragmenthome)
        }
    }

    //Set values for views

    private fun updateUserDetails(user: User) {

        detailBinding.fragmentdetailFirstNameTextView.text = user.firstName
        detailBinding.fragmentdetailLastNameTextView.text = user.lastName
        detailBinding.fragmentdetailGendertxtview.text = user.gender
        detailBinding.fragmentdetailPhonetxtview.text = user.phoneNumber
        detailBinding.fragmentdetailAddresstxtview.text = user.address.street+user.address.city +"\n"+user.address.state+ user.address.country + user.address.zip

        if(user.stickers.contains("Ban")){
            detailBinding.fragmentdetailFrmlayoutBan.visibility = View.VISIBLE
        }
        else if(user.stickers.contains("Fam")){
            detailBinding.fragmentdetailFrmlayoutfam.visibility = View.VISIBLE
        }

        Picasso.with(context).load(user.imageUrl)
            .placeholder(R.drawable.ic_launcher_background).into(detailBinding.fragmentdetailImageView)


    }



    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0

        user?.let { user ->
            val currentLocation = LatLng(user.currentLatitude, user.currentLongitude)

            Log.d("currentst",user.currentLongitude.toString())
            val markerOptions = MarkerOptions()
                .position(currentLocation)
                .title("Current Address: ${user.address}")
            googleMap.addMarker(markerOptions)

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
        }
    }

    }


