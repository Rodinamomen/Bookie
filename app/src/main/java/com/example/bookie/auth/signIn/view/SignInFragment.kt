package com.example.bookie.auth.signIn.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bookie.R
import com.example.bookie.auth.signIn.repo.SignInRepoImp
import com.example.bookie.auth.signIn.viewModel.SignInViewModel
import com.example.bookie.auth.signIn.viewModel.SignInViewModelFactory
import com.example.bookie.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private val TAG = "SignInFragment"
    private lateinit var signInViewModel:SignInViewModel
    private lateinit var loadingDialog: Dialog
    private lateinit var connectionErrorDialog: Dialog

    private lateinit var binding : FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        dismissLoadingDialog()
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onViewCreated: point 1")
            signInViewModel.handleSignInResult(result.data)
        } else {
            Log.d(TAG, "onViewCreated: point 2")
            Log.w(TAG, "Google sign in failed, result code: ${result.resultCode}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: point 3")

        getViewModelReady()
        loadingDialog = Dialog(requireContext())
        connectionErrorDialog = Dialog(requireContext())

        binding.btnGoogleSignIn.setOnClickListener {
            try {
                Log.d(TAG, "onViewCreated: point 4")
                val signInIntent = signInViewModel.getGoogleSignInIntent()
                googleSignInLauncher.launch(signInIntent)
            }catch (e:Exception){
                Log.d(TAG, "onViewCreated: point 5")

            }

        }
        Log.d(TAG, "onViewCreated: point x")
        signInViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            Log.d(TAG, "onViewCreated: point 6")
            if (user != null) {
                Log.d(TAG, "User Info: Name: ${user.displayName}, Email: ${user.email}, uid: ${user.uid}")
                Toast.makeText(requireContext(), "Signed you in successfully.", Toast.LENGTH_SHORT).show()
                // Navigate to the home screen
                view.findNavController().navigate(R.id.preferencesFragment)

            } else {
                createConnectionErrorDialog()
                showErrorDialog()
                Toast.makeText(requireContext(), "Failed to signed you in.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSignin.setOnClickListener {

            val email = binding.txtEtEmail.editText?.text.toString()
            val password = binding.txtEtPassword.editText?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                signInViewModel.signInWithEmailAndPassword(email, password)

                createLoadingDialog()
                showLoadingDialog()

                signInViewModel.isUserLoggedIn.observe(requireActivity()){isLogged->
                    if(isLogged!= null){
                        dismissLoadingDialog()
                        if (isLogged){
                            // navigate to the home screen
                            view.findNavController().navigate(R.id.preferencesFragment)
                            Toast.makeText(requireContext(), "Logged in.", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "onViewCreated: the user logged in successfully,")
                        }else{
                            Log.d(TAG, "onViewCreated: failed to log in.")
                        }
                    }else{
                        dismissLoadingDialog()
                        Log.d(TAG, "onViewCreated: the user is null.")
                    }
                }
            }
        }

        binding.btnGoToSignUp.setOnClickListener {
            view.findNavController().navigate(R.id.signUpFragment)
        }


    }

    private fun getViewModelReady(){
        val factory = SignInViewModelFactory(SignInRepoImp(requireContext()))
        signInViewModel = ViewModelProvider(requireActivity(), factory)[SignInViewModel::class.java]
    }

    private fun createLoadingDialog(){
        val dialog = layoutInflater.inflate(R.layout.loading_dialog, null)
        loadingDialog.setContentView(dialog)
        loadingDialog.setCancelable(false)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    private fun showLoadingDialog(){
        loadingDialog.show()
    }
    private fun dismissLoadingDialog(){
        loadingDialog.dismiss()
    }

    private fun createConnectionErrorDialog(){
        val dialog = layoutInflater.inflate(R.layout.error_dialog, null)
        connectionErrorDialog.setContentView(dialog)
        connectionErrorDialog.setCancelable(false)
        connectionErrorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun showErrorDialog(){
        loadingDialog.show()
    }
    private fun dismissErrorDialog(){
        loadingDialog.dismiss()
    }





}