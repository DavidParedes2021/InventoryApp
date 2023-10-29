package com.example.secondproject

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        val updateButton: Button = itemView.findViewById(R.id.updateButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.productName.text = currentItem.name
        holder.productPrice.text = currentItem.price.toString()
        holder.productQuantity.text = currentItem.quantity.toString()

        // Set click listeners for update and delete buttons
        holder.updateButton.setOnClickListener {
            val product = productList[position]

            // Create an Intent to go to the EditProductActivity
            val intent = Intent(holder.itemView.context, EditActivity::class.java).apply {
                putExtra("productId", product.id) // Pass the product ID
                putExtra("productName", product.name) // Pass the product name
                putExtra("productPrice", product.price) // Pass the product price
                putExtra("productQuantity", product.quantity) // Pass the product quantity
            }

            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val productName = productList[position].name // Get the name of the product to be deleted

            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Do you want to permanently delete $productName?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val productId = productList[position].id // Get the ID of the product
                    val sqLiteHelper = SQLiteHelper(holder.itemView.context)
                    sqLiteHelper.delete(productId)

                    // Create a mutable copy and update the adapter
                    val mutableList = productList.toMutableList()
                    mutableList.removeAt(position)
                    productList = mutableList.toList()
                    notifyDataSetChanged()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }

            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
