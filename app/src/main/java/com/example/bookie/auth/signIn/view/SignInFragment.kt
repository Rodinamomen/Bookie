package com.example.bookie.auth.signIn.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.auth.signIn.repo.SignInRepoImp
import com.example.bookie.auth.signIn.viewModel.SignInViewModel
import com.example.bookie.auth.signIn.viewModel.SignInViewModelFactory
import com.example.bookie.auth.signup.repo.SignUpRepoImp
import com.example.bookie.auth.signup.viewModel.SignUpViewModel
import com.example.bookie.auth.signup.viewModel.SignUpViewModelFactory
import com.example.bookie.databinding.FragmentSignInBinding
import kotlin.math.sign


class SignInFragment : Fragment() {

    private val TAG = "SignInFragment"
    private lateinit var signInViewModel:SignInViewModel
    private lateinit var loadingDialog: Dialog

    private lateinit var binding : FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getViewModelReady()
        loadingDialog = Dialog(requireContext())

        binding.btnSignin.setOnClickListener {

            val email = binding.txtEtEmail.editText?.text.toString()
            val password = binding.txtEtPassword.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                signInViewModel.signInWithEmailAndPassword(email, password)

                createDialog()
                showDialog()

                signInViewModel.isUserLoggedIn.observe(requireActivity()){isLogged->
                    if(isLogged!= null){
                        dismissDialog()
                        if (isLogged){
                            // navigate to the home screen
                            Toast.makeText(requireContext(), "Logged in.", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "onViewCreated: the user logged in successfully,")
                        }else{
                            Log.d(TAG, "onViewCreated: failed to log in.")
                        }
                    }else{
                        dismissDialog()
                        Log.d(TAG, "onViewCreated: the user is null.")
                    }
                }
            }


        }

        binding.btnGoToSignUp.setOnClickListener {
            view.findNavController().navigate(R.id.signUpFragment)
//            requireActivity().findNavController(R.id.signInFragment).popBackStack()
        }


    }

    private fun getViewModelReady(){
        val factory = SignInViewModelFactory(SignInRepoImp())
        signInViewModel = ViewModelProvider(requireActivity(), factory)[SignInViewModel::class.java]
    }

    private fun createDialog(){
        val dialog = layoutInflater.inflate(R.layout.loading_dialog, null)
        loadingDialog.setContentView(dialog)
        loadingDialog.setCancelable(true)
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun showDialog(){
        loadingDialog.show()
    }
    private fun dismissDialog(){
        loadingDialog.dismiss()
    }


}