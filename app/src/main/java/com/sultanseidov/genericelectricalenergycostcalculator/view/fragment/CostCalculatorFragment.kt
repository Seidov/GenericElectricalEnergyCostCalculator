package com.sultanseidov.genericelectricalenergycostcalculator.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.sultanseidov.genericelectricalenergycostcalculator.R
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.base.Status
import com.sultanseidov.genericelectricalenergycostcalculator.databinding.FragmentCostCalculatorBinding
import com.sultanseidov.genericelectricalenergycostcalculator.view.adapter.PreviousActionAdapter
import com.sultanseidov.genericelectricalenergycostcalculator.view.viewmodel.MainViewModel

class CostCalculatorFragment : Fragment(R.layout.fragment_cost_calculator) {

    private var _binding: FragmentCostCalculatorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCostCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearUI()
        var customerServiceNumber: String
        var currentMeterValue: Int

        binding.buttonSubmit.setOnClickListener {
            if (validationUiComponent()
            ) {
                customerServiceNumber = binding.editTextCustomerServiceNumber.text.toString()
                currentMeterValue = binding.editTextCurrentMeterValue.text.toString().toInt()

                binding.listItems.visibility = View.VISIBLE
                binding.textViewTitle1.visibility=View.VISIBLE


                viewModel.getCustomerBillsListFromDdId(customerServiceNumber)
                    .observe(viewLifecycleOwner) {
                        val adapter = PreviousActionAdapter()
                        adapter.uploadData(it)
                        binding.listItems.adapter = adapter
                    }

                viewModel.getCustomerElectricMetersByCSN(customerServiceNumber)
                    .observe(viewLifecycleOwner) { previousMeterValue ->

                        if (previousMeterValue != null) {

                            if (currentMeterValue >= previousMeterValue) {
                                viewModel.calculateElectricCost(
                                    currentMeterValue,
                                    previousMeterValue
                                )
                            } else {
                                createDialog(
                                    "New Electric Meter Reading\n" +
                                            "Must be always higher or equal"
                                )
                            }

                        } else {
                            viewModel.calculateElectricCost(currentMeterValue, 0)

                        }

                        subscribeToObservers(customerServiceNumber, currentMeterValue)

                    }

            }
        }
    }

    private fun subscribeToObservers(customerServiceNumber: String, currentMeterValue: Int) {
        viewModel.totalBillAmount.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    result.data?.let { customerTotalBillAmount ->
                        showUI()
                        binding.textNewTotalBillAmount.text= "Total Bill Amount = $$customerTotalBillAmount"

                        binding.buttonSave.setOnClickListener {
                            viewModel.insertCustomerBill(
                                CustomerBillModel(
                                    customerServiceNumber,
                                    currentMeterValue,
                                    customerTotalBillAmount,
                                    System.currentTimeMillis()
                                )
                            )

                            clearUI()
                        }

                    }
                }

                Status.ERROR -> {
                    result.message?.let {

                        Log.e("viewModel.totalBillAmount", "ERROR")
                    }
                }

                Status.LOADING -> {
                    Log.e("viewModel.totalBillAmount", "LOADING")
                }
            }
        }
    }

    private fun validationUiComponent(): Boolean {
        var validation = true

        if (binding.editTextCustomerServiceNumber.length() != 10) {
            validation = false
            createDialog(
                "Customer's Service Number\n" +
                        "Must be 10 digits"
            )
        }

        if (binding.editTextCustomerServiceNumber.text.isNullOrBlank()) {
            validation = false
            createDialog(
                "Customer's Service Number\n" +
                        "cannot be empty"
            )
        }
        if (binding.editTextCurrentMeterValue.text.isNullOrBlank()) {
            validation = false
            createDialog("Meter Value\n" + "cannot be empty")
        }
        return validation
    }

    private fun showUI(){
        binding.buttonSave.visibility = View.VISIBLE
        binding.textViewTitle2.visibility=View.VISIBLE
        binding.textNewTotalBillAmount.visibility=View.VISIBLE
    }

    private fun clearUI() {
        binding.editTextCustomerServiceNumber.text.clear()
        binding.editTextCurrentMeterValue.text.clear()
        binding.textViewTitle2.visibility=View.INVISIBLE
        binding.textViewTitle1.visibility=View.INVISIBLE
        binding.listItems.visibility = View.INVISIBLE
        binding.textNewTotalBillAmount.visibility=View.INVISIBLE
        binding.buttonSave.visibility = View.INVISIBLE

    }


    private fun createDialog(s: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Warning!")
        builder.setMessage(s)
        builder.setNegativeButton("Cancel") { dialog, id -> }
        builder.setNeutralButton("OK") {dialog, id-> }
        builder.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}