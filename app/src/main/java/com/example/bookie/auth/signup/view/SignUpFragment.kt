package com.example.bookie.auth.signup.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.credentials.GetCredentialRequest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.bookie.R
import com.example.bookie.auth.signup.model.User
import com.example.bookie.auth.signup.repo.SignUpRepoImp
import com.example.bookie.auth.signup.viewModel.SignUpViewModel
import com.example.bookie.auth.signup.viewModel.SignUpViewModelFactory
import com.example.bookie.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.identitycredentials.IdentityCredentialManager
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlin.math.log


class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"
    private lateinit var binding : FragmentSignUpBinding

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var loadingDialog: Dialog

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            signUpViewModel.handleSignInResult(result.data)
        } else {
            Log.w(TAG, "Google sign in failed, result code: ${result.resultCode}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()
        loadingDialog = Dialog(requireContext())

        auth = FirebaseAuth.getInstance()


        binding.btnGoogleSignUp.setOnClickListener {
            val signInIntent = signUpViewModel.getGoogleSignInIntent()
            googleSignInLauncher.launch(signInIntent)
        }
        signUpViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                Log.d(TAG, "User Info: Name: ${user.displayName}, Email: ${user.email}")
                // Navigate to the preferences screen

            } else {
                Toast.makeText(requireContext(), "Failed to signed you up.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnSignup.setOnClickListener {
            val name = binding.txtEtName.editText?.text.toString()
            val email = binding.txtEtEmail.editText?.text.toString()
            val password = binding.txtEtPassword.editText?.text.toString()

            if (name.isEmpty()){
                binding.txtEtName.error="This field is required."
            }else{
                binding.txtEtName.error = null
            }
            if (email.isEmpty()){
                binding.txtEtEmail.error="This field is required."
            }else{
                binding.txtEtEmail.error= null
            }
            if (password.isEmpty()){
                binding.txtEtPassword.error="This field is required."
            }else{
                binding.txtEtPassword.error= null
            }

            if(isValidEmail(email)){
                binding.txtEtEmail.error= null
                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){

                    val userData = User(name, email)

                    signUpViewModel.signUpByEmailAndPassword(userData, password)

                    createDialog()
                    showDialog()

                    signUpViewModel.isUserCreated.observe(requireActivity()){isCreated->
                        dismissDialog()
                        if (isCreated != null){
                            if (isCreated){
                                // navigate to the categories fragment.
                                binding.tvErrorMessage.visibility = View.GONE
                                Log.d(TAG, "onViewCreated: isCreated from the repo is true")
                            }else{
                                binding.tvErrorMessage.visibility = View.VISIBLE
                                Log.d(TAG, "onViewCreated: isCreated from the repo is false")
                            }
                        }else{
                            binding.tvErrorMessage.visibility = View.VISIBLE
                            Log.d(TAG, "onViewCreated: isCreated from the repo is null")
                        }

                    }
                }
                else{
                    binding.txtEtEmail.error="Invalid Email."
                }
            }



        }

        binding.btnGoToSignIn.setOnClickListener {
            //navigate to the sign in fragment
            view.findNavController().navigate(R.id.signInFragment)
//            view.findNavController().popBackStack()
        }

    }

    private fun getViewModelReady(){
        val factory = SignUpViewModelFactory(SignUpRepoImp(requireContext()))
        signUpViewModel = ViewModelProvider(requireActivity(), factory)[SignUpViewModel::class.java]
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

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}