package com.example.bookie.auth.signup.view

import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bookie.R
import com.example.bookie.auth.signup.model.User
import com.example.bookie.auth.signup.repo.SignUpRepoImp
import com.example.bookie.auth.signup.viewModel.SignUpViewModel
import com.example.bookie.auth.signup.viewModel.SignUpViewModelFactory
import com.example.bookie.databinding.FragmentSignUpBinding

import com.google.firebase.auth.FirebaseAuth


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
        signUpViewModel.user.observe(requireActivity()) { user ->
            if (user != null) {
                Log.d(TAG, "User Info: Name: ${user.displayName}, Email: ${user.email}")
                Toast.makeText(requireContext(), "Signed you up successfully.", Toast.LENGTH_SHORT).show()
                // Navigate to the preferences screen
                view.findNavController().navigate(R.id.preferencesFragment)

            } else {
                Toast.makeText(requireContext(), "Failed to signed you up.", Toast.LENGTH_SHORT).show()
            }
        }

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
                    showLoadingDialog()

                    signUpViewModel.isUserCreated.observe(requireActivity()){isCreated->
                        dismissLoadingDialog()
                        if (isCreated != null){
                            if (isCreated){
                                // navigate to the preferences fragment.
                                binding.tvErrorMessage.visibility = View.GONE
                                view.findNavController().navigate(R.id.preferencesFragment)
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
        }

    }

    private fun getViewModelReady(){
        val factory = SignUpViewModelFactory(SignUpRepoImp(requireContext()))
        signUpViewModel = ViewModelProvider(requireActivity(), factory)[SignUpViewModel::class.java]
    }

    private fun createDialog(){
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

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }
}