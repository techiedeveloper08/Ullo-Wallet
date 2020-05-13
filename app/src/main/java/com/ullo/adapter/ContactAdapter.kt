package com.ullo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ullo.R
import com.ullo.api.response.contact.Contact
import com.ullo.databinding.ContactListItemBinding

class ContactAdapter(var context: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    var contactList: ArrayList<Contact> = ArrayList()

    private lateinit var onContactItemListener: OnContactItemListener

    fun setContactListData(contactList: ArrayList<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    fun setOnContactItemListener(onContactItemListener: OnContactItemListener) {
        this.onContactItemListener = onContactItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val contactListItemBinding = DataBindingUtil.inflate<ContactListItemBinding>(LayoutInflater.from(parent!!.context), R.layout.contact_list_item, parent, false)
        return ViewHolder(contactListItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = contactList[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ViewHolder internal constructor(private val contactListItemBinding: ContactListItemBinding) : RecyclerView.ViewHolder(contactListItemBinding.root) {

        fun bind(contact: Contact) {
            contactListItemBinding.contact = contact
            contactListItemBinding.itemView.setOnClickListener {
                if (onContactItemListener != null) {
                    onContactItemListener.onItemClick(contact)
                }
            }
            contactListItemBinding.executePendingBindings()
        }
    }

    interface OnContactItemListener {
        fun onItemClick(contact: Contact)
    }
}