package com.reinventiva.sticket.ui.mynumbers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reinventiva.sticket.R
import kotlinx.android.synthetic.main.my_numbers_fragment.*

class MyNumbersFragment : Fragment() {

    private lateinit var viewModel: MyNumbersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.my_numbers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyNumbersViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.list.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = MyNumbersRecyclerAdapter(context!!, it)
        })
    }

}
