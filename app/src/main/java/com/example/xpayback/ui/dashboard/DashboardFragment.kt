package com.example.xpayback.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xpayback.databinding.FragmentDashboardBinding
import com.example.xpayback.utils.Extensions

class DashboardFragment : Fragment() {

  private var _binding: FragmentDashboardBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
      ViewModelProvider(this)[DashboardViewModel::class.java]
    val userId= arguments?.getInt("Id")
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root

    dashboardViewModel.getUserData(userId!!)
    dashboardViewModel.loading.observe(viewLifecycleOwner){
//            binding.progressBar.visibility = View.GONE
      if(it){binding.progressBar.visibility = View.VISIBLE}else{
        binding.progressBar.visibility = View.GONE
        binding.layout.visibility = View.VISIBLE
      }
    }
    dashboardViewModel.userData.observe(viewLifecycleOwner) {
      binding.apply {
        tvUserName.text = it.username
        tvEmail.text = it.email
        tvFirstName.text = it.firstName
        tvLastName.text = it.lastName
        tvAge.text = it.age.toString()
        tvMaiden.text = it.maidenName
        tvPhone.text = it.phone
        tvBirthday.text = it.birthDate
        tvBlood.text = it.bloodGroup
        tvGender.text = it.gender

      }
      Extensions.loadImagefromUrl(binding.ivUser.context, binding.ivUser, it.image)
    }
   
    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
