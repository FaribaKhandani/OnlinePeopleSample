package com.example.myapplication.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.CustomeritemBinding
import com.example.myapplication.model.User
import com.squareup.picasso.Picasso


class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {


    var users: List<User> = emptyList()

    private var itemClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CustomeritemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int {
        return users.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class ViewHolder(private val binding: CustomeritemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {

            binding.apply {
                user.also {
                    firstNameTextView.text = it.firstName
                    lastNameTextView.text = it.lastName
                    genderTextView.text = it.gender
                    phoneNumberTextView.text = it.phoneNumber

                    if(user.stickers.contains("Ban")){
                        cutomeritemFrmlayoutban.visibility = View.VISIBLE
                    }
                    else if(user.stickers.contains("Fam")){
                        customeritemFrmlayoutfam.visibility = View.VISIBLE
                    }
                    Picasso.with(imageView.context).load(it.imageUrl)
                        .placeholder(R.drawable.ic_launcher_background).into(imageView)
                }
            }
            itemClickListener?.let { listener ->
                binding.root.setOnClickListener {
                    listener.onItemClick(user)
                }
            }
        }
    }

    interface OnClickListener {
        fun onItemClick(user: User)
    }

    fun setItemClickListener(listener: OnClickListener) {
        itemClickListener = listener
    }


}