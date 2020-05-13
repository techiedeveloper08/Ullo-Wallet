package com.ullo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ullo.R
import com.ullo.api.response.notification.Notification
import com.ullo.databinding.NotificationListItemBinding

class NotificationAdapter(var context: Context) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    var contact: ArrayList<Notification> = ArrayList()

    fun setNotificationListData(contact: ArrayList<Notification>) {
        this.contact = contact
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val notificationListItemBinding = DataBindingUtil.inflate<NotificationListItemBinding>(LayoutInflater.from(context), R.layout.notification_list_item, parent, false)
        return ViewHolder(notificationListItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = contact[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return contact.size
    }

    inner class ViewHolder internal constructor(private val notificationListItemBinding: NotificationListItemBinding) : RecyclerView.ViewHolder(notificationListItemBinding.root) {

        fun bind(notification: Notification) {
            notificationListItemBinding.notification = notification
            notificationListItemBinding.executePendingBindings()
        }
    }
}