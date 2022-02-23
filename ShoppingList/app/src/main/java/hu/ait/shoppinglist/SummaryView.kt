package hu.ait.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hu.ait.shoppinglist.databinding.BottomSheetBinding
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import java.lang.RuntimeException

class SummaryView : BottomSheetDialogFragment() {

    private lateinit var tvClothingSum : TextView
    private lateinit var tvElectronicsSum : TextView
    private lateinit var tvEntertainmentSum : TextView
    private lateinit var tvFoodSum : TextView
    private lateinit var tvHomeSum : TextView
    private lateinit var tvTotalSum : TextView

    lateinit var binding: BottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.bottom_sheet, null
        )

        if (arguments == null) {
            throw RuntimeException("No arguments passed to filter activity")
        }

        rootView.fruitSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Fruits")))
        rootView.vegetableSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Vegetables")))
        rootView.meatSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Meat and Eggs")))
        rootView.otherSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Other")))
        rootView.dairySum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Dairy")))
        rootView.alcoholSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Alcohol")))
        rootView.beverageSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Beverages")))
        rootView.cleaningSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Cleaning Supplies")))
        rootView.grainSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Grains")))
        rootView.fishSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Fish")))
        rootView.sweetSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Sweets")))
        rootView.tvTotalSum.text = requireContext().getString(R.string.dollar, "%.2f".format(requireArguments().get("Total")))

        return rootView
    }
}