package com.ullo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ullo.R
import com.ullo.api.response.balance_history.History
import com.ullo.api.response.contact.Contact
import com.ullo.databinding.BalanceListItemBinding
import com.ullo.databinding.ContactListItemBinding

class BalanceHistoryAdapter(var context: Context) : RecyclerView.Adapter<BalanceHistoryAdapter.ViewHolder>() {

    var contactList: ArrayList<History> = ArrayList()

    fun setBalanceHistoryListData(contactList: ArrayList<History>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val balanceListItemBinding = DataBindingUtil.inflate<BalanceListItemBinding>(LayoutInflater.from(context), R.layout.balance_list_item, parent, false)
        return ViewHolder(balanceListItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = contactList[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    inner class ViewHolder internal constructor(private val balanceListItemBinding: BalanceListItemBinding) : RecyclerView.ViewHolder(balanceListItemBinding.root) {

        fun bind(history: History) {
            balanceListItemBinding.history = history
            balanceListItemBinding.executePendingBindings()
        }
    }
}