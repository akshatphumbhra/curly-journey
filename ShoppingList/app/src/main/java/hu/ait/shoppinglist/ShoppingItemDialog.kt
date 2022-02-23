package hu.ait.shoppinglist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.ait.shoppinglist.data.ShopItem
import hu.ait.shoppinglist.databinding.ShopItemDialogBinding
import java.lang.RuntimeException

class ShopItemDialog : DialogFragment() {

    interface ShopItemHandler {
        fun shopItemCreated(shopItem: ShopItem)
        fun shopItemUpdated(shopItem: ShopItem)
    }

    lateinit var shopItemHandler: ShopItemHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShopItemHandler) {
            shopItemHandler = context
        } else {
            throw RuntimeException(
                getString(R.string.runtime_exception)
            )
        }
    }

    lateinit var shopItemDialogBinding: ShopItemDialogBinding

    var isEditMode = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        if (arguments != null && requireArguments().containsKey(ScrollingActivity.KEY_SHOP_ITEM_EDIT)) {
            isEditMode = true
            dialogBuilder.setTitle(getString(R.string.edit_item))

        } else {
            isEditMode = false
            dialogBuilder.setTitle(getString(R.string.new_item))
        }
        shopItemDialogBinding = ShopItemDialogBinding.inflate(layoutInflater)
        val categoriesAdapter = ArrayAdapter.createFromResource(
            activity as Context,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        )
        shopItemDialogBinding.spinnerCategories.adapter = categoriesAdapter
        dialogBuilder.setView(shopItemDialogBinding.root)

        if (isEditMode) {

            val itemEdit =
                requireArguments().getSerializable(ScrollingActivity.KEY_SHOP_ITEM_EDIT) as ShopItem
            shopItemDialogBinding.cbShopItemPurchased.isChecked = itemEdit.shopItemPurchased
            shopItemDialogBinding.etShopItemDescription.setText(itemEdit.shopItemDescription)
            shopItemDialogBinding.etShopItemEstimatedPrice.setText(itemEdit.shopItemEstimatedPrice)
            shopItemDialogBinding.etShopItemName.setText(itemEdit.shopItemName)
            val categoryPicked = itemEdit.shopItemCategory
            val categoryPickedIndex = categoriesAdapter.getPosition(categoryPicked)
            shopItemDialogBinding.spinnerCategories.setSelection(categoryPickedIndex)
        }

        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, which -> }
        dialogBuilder.setNegativeButton(getString(R.string.cancel)) { dialog, which -> }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {

            if (shopItemDialogBinding.etShopItemName.text.isNotEmpty()) {
                if (isEditMode) {
                    handleItemEdit()
                } else {
                    handleItemCreate()
                }

                dialog.dismiss()

            } else {
                shopItemDialogBinding.etShopItemName.error = getString(R.string.empty_error)
            }
        }
    }


    private fun handleItemCreate() {
        shopItemHandler.shopItemCreated(
            ShopItem(
                null,
                shopItemDialogBinding.cbShopItemPurchased.isChecked,
                shopItemDialogBinding.etShopItemName.text.toString(),
                shopItemDialogBinding.etShopItemDescription.text.toString(),
                shopItemDialogBinding.etShopItemEstimatedPrice.text.toString(),
                shopItemDialogBinding.spinnerCategories.selectedItem.toString()
            )
        )
    }

    private fun handleItemEdit() {
        val itemEdit = (arguments?.getSerializable(
            ScrollingActivity.KEY_SHOP_ITEM_EDIT
        ) as ShopItem).copy(
            shopItemName = shopItemDialogBinding.etShopItemName.text.toString(),
            shopItemPurchased = shopItemDialogBinding.cbShopItemPurchased.isChecked,
            shopItemDescription = shopItemDialogBinding.etShopItemDescription.text.toString(),
            shopItemCategory = shopItemDialogBinding.spinnerCategories.selectedItem.toString(),
            shopItemEstimatedPrice = shopItemDialogBinding.etShopItemEstimatedPrice.text.toString()
        )
        shopItemHandler.shopItemUpdated(itemEdit)
    }
}
