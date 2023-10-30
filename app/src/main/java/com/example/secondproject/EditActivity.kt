package com.example.secondproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import com.example.secondproject.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.EditButton.setOnClickListener {
            val name = binding.NameInput.editText?.text?.toString() ?: ""
            val price = binding.PriceInput.editText?.text?.toString() ?: ""
            val quantity = binding.QuantityInput.editText?.text?.toString() ?: ""

            if (name.isBlank() || price.isBlank() || quantity.isBlank()){
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_LONG).show()
            } else {



            var db_helper = SQLiteHelper(context = applicationContext)
            val db_write = db_helper.writableDatabase


            val cursor = db_write.query(
                DBContract.FeedEntry.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                "id=?",            // The columns for the WHERE clause
                arrayOf(name),    // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
            )

            var Productid = 0
            with(cursor) {
              moveToNext()
                Productid= getInt(getColumnIndexOrThrow(BaseColumns._ID))

            }
            cursor.close()
            db_helper.update(Productid, name, price.toDouble(), quantity.toInt())
            finish()
            }
        }

    }
}