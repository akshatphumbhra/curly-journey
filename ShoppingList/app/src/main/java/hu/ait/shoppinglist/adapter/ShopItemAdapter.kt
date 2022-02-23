package hu.ait.shoppinglist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.ait.shoppinglist.R
import hu.ait.shoppinglist.ScrollingActivity
import hu.ait.shoppinglist.data.AppDatabase
import hu.ait.shoppinglist.data.ShopItem
import hu.ait.shoppinglist.databinding.ShopItemRowBinding

class ShopItemAdapter : ListAdapter<ShopItem, ShopItemAdapter.ViewHolder> {

    var shopItems = mutableListOf<ShopItem>()

    val context : Context
    constructor(context: Context, shopList: List<ShopItem>) : super(ShopItemDiffCallBack()){
        this.context = context
        shopItems.addAll(shopList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val shopItemRowBinding = ShopItemRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(shopItemRowBinding)
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

//    fun getItemsList(): List<ShopItem> {
//        return shopItems
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var shopItem = shopItems.get(holder.adapterPosition)
        holder.bind(shopItem)


        holder.shopItemRowBinding.btnDeleteShopItem.setOnClickListener{
            removeShopItem(holder.adapterPosition)
        }
        holder.shopItemRowBinding.btnEditShopItem.setOnClickListener{
            (context as ScrollingActivity).showEditShopItemDialog(
                shopItem, holder.adapterPosition)
        }
        holder.shopItemRowBinding.cbShopItemPurchased.setOnClickListener{
            shopItem.shopItemPurchased = holder.shopItemRowBinding.cbShopItemPurchased.isChecked

            Thread{
                AppDatabase.getInstance(context).shopItemDao().updateShopItem(shopItem)
            }.start()
        }
    }

    fun removeShopItem(index: Int) {
        Thread {
            AppDatabase.getInstance(context).shopItemDao().deleteShopItem(shopItems.get(index))
            (context as ScrollingActivity).runOnUiThread {
                shopItems.removeAt(index)
                notifyItemRemoved(index)
            }
        }.start()
    }

    fun removeAll() {
        shopItems.clear()
        notifyDataSetChanged()
    }

    fun addShopItem(shopItem: ShopItem){
        shopItems.add(shopItem)
        notifyItemInserted(shopItems.lastIndex)
    }

    fun updateShopItem(shopItem: ShopItem, index : Int) {
        shopItems.set(index, shopItem)
        notifyItemChanged(index)
    }

    fun setHolderCategoryImage(category : String, holder: ShopItemRowBinding) {
        when (category) {
            "Fruits" -> holder.ivShopItemCategory.setImageResource(R.drawable.fruit)
            "Vegetables" -> holder.ivShopItemCategory.setImageResource(R.drawable.veggie)
            "Meat and Eggs" -> holder.ivShopItemCategory.setImageResource(R.drawable.meat)
            "Dairy" -> holder.ivShopItemCategory.setImageResource(R.drawable.cheese)
            "Fish" -> holder.ivShopItemCategory.setImageResource(R.drawable.fish)
            "Grains" -> holder.ivShopItemCategory.setImageResource(R.drawable.grain)
            "Beverages" -> holder.ivShopItemCategory.setImageResource(R.drawable.beverages)
            "Alcohol" -> holder.ivShopItemCategory.setImageResource(R.drawable.alcohol)
            "Sweets" -> holder.ivShopItemCategory.setImageResource(R.drawable.sweets)
            "Cleaning Supplies" -> holder.ivShopItemCategory.setImageResource(R.drawable.cleaning)
            else -> holder.ivShopItemCategory.setImageResource(R.drawable.other)
        }
    }

    inner class ViewHolder(val shopItemRowBinding: ShopItemRowBinding) : RecyclerView.ViewHolder(shopItemRowBinding.root) {
        fun bind(shopItem: ShopItem) {
            shopItemRowBinding.cbShopItemPurchased.isChecked = shopItem.shopItemPurchased
            shopItemRowBinding.tvShopItemName.text = shopItem.shopItemName
            shopItemRowBinding.tvShopItemDescription.text = shopItem.shopItemDescription
            shopItemRowBinding.tvShopItemEstimatedPrice.text = shopItem.shopItemEstimatedPrice
            setHolderCategoryImage(shopItem.shopItemCategory, shopItemRowBinding)
        }
    }
}

class ShopItemDiffCallBack : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}