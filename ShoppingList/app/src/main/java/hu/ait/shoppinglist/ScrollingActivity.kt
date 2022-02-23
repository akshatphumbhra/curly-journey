package hu.ait.shoppinglist

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.ait.shoppinglist.adapter.ShopItemAdapter
import hu.ait.shoppinglist.data.AppDatabase
import hu.ait.shoppinglist.data.ShopItem
import hu.ait.shoppinglist.databinding.ActivityScrollingBinding
import kotlin.concurrent.thread

class ScrollingActivity : AppCompatActivity(),
    ShopItemDialog.ShopItemHandler {

    lateinit var shopItemAdapter: ShopItemAdapter
    lateinit var binding: ActivityScrollingBinding

    companion object {
        public val KEY_SHOP_ITEM_EDIT = "KEY_SHOP_ITEM_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fabAddShopItem.setOnClickListener {
            ShopItemDialog().show(supportFragmentManager, "Dialog")
        }

        binding.fabDeleteAll.setOnClickListener {
            Thread {
                AppDatabase.getInstance(this@ScrollingActivity).shopItemDao()
                    .deleteAllShopItems()
                runOnUiThread {
                    shopItemAdapter.removeAll()
                }
            }.start()
        }

        binding.fabShowSummary.setOnClickListener {
            var fruits = 0.0
            var vegetables = 0.0
            var meat_and_eggs = 0.0
            var dairy = 0.0
            var alcohol = 0.0
            var beverages = 0.0
            var other = 0.0
            var fish = 0.0
            var grains = 0.0
            var sweets = 0.0
            var cleaning_supplies = 0.0
            var total = 0.0
            for (item in shopItemAdapter.shopItems) {
                total += item.shopItemEstimatedPrice.toFloat()
                if (item.shopItemCategory.equals("Fruits")) {
                    fruits += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Vegetables")) {
                    vegetables += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Meat and Eggs")) {
                    meat_and_eggs += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Dairy")) {
                    dairy += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Alcohol")) {
                    alcohol += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Beverages")) {
                    beverages += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Sweets")) {
                    sweets += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Cleaning Supplies")) {
                    cleaning_supplies += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Fish")) {
                    fish += item.shopItemEstimatedPrice.toFloat()
                } else if (item.shopItemCategory.equals("Grains")) {
                    grains += item.shopItemEstimatedPrice.toFloat()
                } else{
                    other += item.shopItemEstimatedPrice.toFloat()
                }
            }

            val bundle = Bundle()
            bundle.putDouble("Fruits", fruits)
            bundle.putDouble("Vegetables", vegetables)
            bundle.putDouble("Meat and Eggs", meat_and_eggs)
            bundle.putDouble("Sweets", sweets)
            bundle.putDouble("Dairy", dairy)
            bundle.putDouble("Other", other)
            bundle.putDouble("Cleaning Supplies", cleaning_supplies)
            bundle.putDouble("Grains", grains)
            bundle.putDouble("Beverages", beverages)
            bundle.putDouble("Alcohol", alcohol)
            bundle.putDouble("Fish", fish)
            bundle.putDouble("Total", total)

            val filterDialog = SummaryView()
            filterDialog.arguments = bundle
            filterDialog.show(supportFragmentManager, "TAG_SUMMARY")
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        Thread {
            var shopItems = AppDatabase.getInstance(this@ScrollingActivity).
            shopItemDao().getAllShopItems()

            runOnUiThread {
                shopItemAdapter = ShopItemAdapter(this, shopItems)
                binding.recyclerView.adapter = shopItemAdapter
            }
        }.start()
    }

    var editIndex : Int = -1

    public fun showEditShopItemDialog(shopItemToEdit: ShopItem, shopItemIndex : Int) {
        editIndex = shopItemIndex

        val editShopItemDialog = ShopItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_SHOP_ITEM_EDIT, shopItemToEdit)
        editShopItemDialog.arguments = bundle

        editShopItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }

    override fun shopItemCreated(shopItem: ShopItem) {
        Thread{

            var newId : Long = AppDatabase.getInstance(this@ScrollingActivity)
                .shopItemDao().insertShopItem(shopItem)
            shopItem.id = newId

            runOnUiThread {
                shopItemAdapter.addShopItem(shopItem)
            }
        }.start()
    }

    override fun shopItemUpdated(shopItem: ShopItem) {
        Thread {
            AppDatabase.getInstance(this@ScrollingActivity)
                .shopItemDao().updateShopItem(shopItem)

            runOnUiThread{
                shopItemAdapter.updateShopItem(shopItem, editIndex)
            }
        }.start()
    }

}
