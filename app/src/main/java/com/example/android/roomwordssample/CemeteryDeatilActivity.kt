package com.example.android.roomwordssample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.roomwordssample.adapters.GraveListAdapter
import com.example.android.roomwordssample.adapters.GraveListListener
import com.example.android.roomwordssample.databinding.ActivityCemeteryDeatilBinding
import com.example.android.roomwordssample.viewmodel.CemeteryViewModel

class CemeteryDeatilActivity : AppCompatActivity() {

    private lateinit var viewModel: CemeteryViewModel
    private lateinit var binding: ActivityCemeteryDeatilBinding
    private var cemeteryId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cemetery_deatil)
        binding.lifecycleOwner = this
         cemeteryId = intent.getIntExtra("cemetery_id", 0)
        Log.i("CemeteryDetailActivity", "cem id is $cemeteryId")

        viewModel = ViewModelProvider(this).get(CemeteryViewModel::class.java)
        binding.cemeteryViewModel = viewModel
        viewModel.getCemetery(cemeteryId!!)


        val adapter = GraveListAdapter(GraveListListener {
            val intent = Intent(this, CemeteryDeatilActivity::class.java)
            intent.putExtra("grave_id", it)
            startActivity(intent)
        })

        //observe the grave list if it is not null
        viewModel.gravesWithId.observe(this, Observer {
            adapter.submitList(it)
        })

        binding.graveRecyclerView.adapter = adapter

        binding.addGraveFab.setOnClickListener{
            val intent = Intent(this, CreateGraveActivity::class.java)
            intent.putExtra("cemetery_id", cemeteryId!!)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cemeteryId?.let { viewModel.getGraveList(it) }


    }

}