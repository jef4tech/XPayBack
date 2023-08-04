package com.example.xpayback.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xpayback.R
import com.example.xpayback.adapters.UserAdapter
import com.example.xpayback.adapters.UsersLoadStateAdapter
import com.example.xpayback.databinding.FragmentHomeBinding
import com.example.xpayback.network.RetrofitClientFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private lateinit var userAdapter: UserAdapter
  lateinit var homeViewModel:HomeViewModel

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    homeViewModel =
      ViewModelProvider(this,MainViewModelFactory(RetrofitClientFactory))[HomeViewModel::class.java]

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root
    setupRecyclerView()
    binding.userRecyclerView.adapter = userAdapter.withLoadStateHeaderAndFooter(
      header = UsersLoadStateAdapter { userAdapter.retry() },
      footer = UsersLoadStateAdapter { userAdapter.retry() }
    )
    lifecycleScope.launch {
      homeViewModel.listData.observe(viewLifecycleOwner){ pagedData ->
        userAdapter.submitData(lifecycle,pagedData)
      }
    }


    return root
  }

  private fun setupRecyclerView()=binding.userRecyclerView.apply {
    userAdapter= UserAdapter{position -> onClick(position)}
    adapter=userAdapter
    layoutManager= LinearLayoutManager(context)
  }


  private fun onClick(position: Int) {

    val bundle= Bundle()
    bundle.putInt("Id", position)
    view?.let { Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_navigation_dashboard,bundle) }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
