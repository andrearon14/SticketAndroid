package com.reinventiva.sticket.ui.myinformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.MyInformationData
import com.reinventiva.sticket.model.MyInformationViewModel
import kotlinx.android.synthetic.main.my_information_fragment.*
import kotlinx.coroutines.launch

class MyInformationFragment: Fragment() {

    private lateinit var viewModel: MyInformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.my_information_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyInformationViewModel::class.java)

        swipeRefresh.isRefreshing = true
        viewModel.information.observe(viewLifecycleOwner, Observer {
            editTextName.setText(it.Name)
            editTextLastName.setText(it.LastName)
            editTextEMail.setText(it.Email)
            swipeRefresh.isRefreshing = false
        })

        buttonSave.setOnClickListener {
            val information = MyInformationData(
                editTextName.text.toString(),
                editTextLastName.text.toString(),
                editTextEMail.text.toString()
            )
            swipeRefresh.isRefreshing = true
            viewModel.viewModelScope.launch {
                viewModel.save(information)
                activity?.finish()
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

}
