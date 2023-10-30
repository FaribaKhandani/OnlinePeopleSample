package com.example.myapplication

import android.os.Bundle
import android.widget.TextView

import androidx.test.runner.*
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.model.Address
import com.example.myapplication.model.User
import com.example.myapplication.ui.fragment.DetailFragment
import com.example.myapplication.viewmodel.DetailViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @get:Rule
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setup() {
        @get:Rule
        detailViewModel = Mockito.mock(DetailViewModel::class.java)
    }

    @Test
    fun testFragmentInitialization() {

        val testUser = User(
            id = 123,
            firstName = "John",
            lastName = "Doe",
            gender = "Male",
            phoneNumber = "1234567890",
            address = Address(
                street = "123 Main St",
                city = "City",
                state = "State",
                country = "Country",
                zip = "12345"
            ),
            stickers = listOf("Ban"),
            imageUrl = "https://fastly.picsum.photos/id/473/200/300.jpg?hmac=WYG6etF60iOJeGoFVY1hVDMakbBRS32ZDGNkVZhF6-8",
            currentLatitude = 37.7749,
            currentLongitude = -122.4194
        )


        `when`(detailViewModel.userDetails).thenReturn(MutableLiveData(testUser))


        val scenario: FragmentScenario<DetailFragment> = launchFragmentInContainer(
            fragmentArgs = Bundle().apply { putInt("id", 123) }
        ) {
            DetailFragment().apply {
                detailViewModel = this@DetailFragmentTest.detailViewModel
            }
        }

       Assert.assertEquals(testUser.firstName , "John")
        scenario.onFragment { fragment ->
            assert(fragment.view != null)
            assert(fragment.view?.findViewById<TextView>(R.id.fragmentdetail_firstNameTextView) != null)
        }
    }

}
