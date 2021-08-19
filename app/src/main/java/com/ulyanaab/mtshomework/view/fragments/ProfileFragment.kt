package com.ulyanaab.mtshomework.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ulyanaab.mtshomework.R
import com.ulyanaab.mtshomework.model.dto.GenreDto
import com.ulyanaab.mtshomework.model.dto.UserDto
import com.ulyanaab.mtshomework.utilities.loadImageAsync
import com.ulyanaab.mtshomework.view.recyclerView.GenreAdapter
import com.ulyanaab.mtshomework.viewModel.ProfileViewModel


class ProfileFragment : Fragment() {

    private lateinit var recyclerViewInterests: RecyclerView
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var currentUser: UserDto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<View>(R.id.active_home).visibility = View.INVISIBLE
        requireActivity().findViewById<View>(R.id.active_profile).visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        with(requireView()) {
            currentUser.name = findViewById<EditText>(R.id.edittext_name).text.toString()
            currentUser.password = findViewById<EditText>(R.id.edittext_password).text.toString()
            currentUser.email = findViewById<EditText>(R.id.edittext_mail).text.toString()
            currentUser.phone = findViewById<EditText>(R.id.edittext_phone).text.toString()
        }
        profileViewModel.updateUserData(currentUser)
    }

    private fun initViews() {
        recyclerViewInterests = requireView().findViewById(R.id.recycler_view_interests)
        val adapter = GenreAdapter(listOf(), this::adapterGenreListener)
        recyclerViewInterests.adapter = adapter

        profileViewModel.profileLiveData.observe(this) {
            with(requireView()) {
                findViewById<EditText>(R.id.edittext_name).setText(it.name)
                findViewById<EditText>(R.id.edittext_password).setText(it.password)
                findViewById<EditText>(R.id.edittext_mail).setText(it.email)
                findViewById<EditText>(R.id.edittext_phone).setText(it.phone)

                adapter.setData(it.genres)
                adapter.notifyDataSetChanged()

                with(findViewById<TextView>(R.id.profile_name)) {
                    if (it.name != "") {
                       this.text = it.name
                    } else this.text = getString(R.string.default_profile_name)
                }

                with(findViewById<TextView>(R.id.profile_mail)) {
                    if (it.email != "") {
                        this.text = it.email
                    } else this.text = getString(R.string.default_profile_mail)
                }

                if (it.photoUrl != "") {
                    findViewById<ImageView>(R.id.profile_photo).loadImageAsync(it.photoUrl)
                }
            }

            currentUser = it
        }

    }

    private fun adapterGenreListener(item: GenreDto) {
        // do nothing
    }

}