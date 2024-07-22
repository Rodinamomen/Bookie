package com.example.bookie.auth.signup.view

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.bookie.R
import com.example.bookie.auth.signup.model.User
import com.example.bookie.auth.signup.repo.SignUpRepoImp
import com.example.bookie.auth.signup.viewModel.SignUpViewModel
import com.example.bookie.auth.signup.viewModel.SignUpViewModelFactory
import com.example.bookie.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    private val TAG = "SignUpFragment"
    private lateinit var binding : FragmentSignUpBinding

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var loadingDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewModelReady()
        loadingDialog = Dialog(requireContext())

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

                    val userData = User(name, email, password)

                    signUpViewModel.signUpByEmailAndPassword(userData)

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
        val factory = SignUpViewModelFactory(SignUpRepoImp())
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