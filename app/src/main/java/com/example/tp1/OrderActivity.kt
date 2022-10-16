package com.example.tp1

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class OrderActivity : AppCompatActivity() {
    lateinit var txtFirstname : EditText
    lateinit var txtLastname : EditText
    lateinit var txtAddress : EditText
    lateinit var btnConfirm : Button
    lateinit var btnCancel : Button
    lateinit var radioSmall : RadioButton
    lateinit var radioMedium : RadioButton
    lateinit var radioLarge : RadioButton
    lateinit var chipPepperoni : Chip
    lateinit var chipPeppers : Chip
    lateinit var chipCheese : Chip
    lateinit var chipOnions : Chip
    lateinit var chipMushrooms : Chip
    lateinit var chipSausage : Chip
    lateinit var chipChicken : Chip
    lateinit var chips1 : ChipGroup
    lateinit var chips2 : ChipGroup
    lateinit var chosenSize : String
    lateinit var ingredients : MutableSet<String?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        radioLarge = findViewById(R.id.radioLarge)
        radioMedium = findViewById(R.id.radioMedium)
        radioSmall = findViewById(R.id.radioSmall)
        radioLarge.setOnClickListener{ view ->
            chosenSize = "Large"
        }
        radioMedium.setOnClickListener{ view ->
            chosenSize = "Medium"
        }
        radioSmall.setOnClickListener{ view ->
            chosenSize = "Small"
        }
        chosenSize = "Medium"
        ingredients = mutableSetOf()
        btnCancel = findViewById(R.id.btnCancel)
        btnCancel.setOnClickListener { view ->
            val intent = Intent(view.context,MainActivity::class.java)
            startActivity(intent)
        }
        chipChicken = findViewById(R.id.chipChicken)
        chipPepperoni = findViewById(R.id.chipPepperoni)
        chipPeppers = findViewById(R.id.chipPeppers)
        chipCheese = findViewById(R.id.chipCheese)
        chipOnions = findViewById(R.id.chipOnions)
        chipMushrooms = findViewById(R.id.chipMushrooms)
        chipSausage = findViewById(R.id.chipSausage)
        configure(chipChicken)
        configure(chipOnions)
        configure(chipCheese)
        configure(chipPepperoni)
        configure(chipPeppers)
        configure(chipMushrooms)
        configure(chipSausage)

        btnConfirm = findViewById(R.id.btnConfirm)
        btnConfirm.setOnClickListener { view ->

            chips1 = findViewById(R.id.chips1)
            chips2 = findViewById(R.id.chips2)
            txtFirstname = findViewById(R.id.txtFirstname)
            txtLastname = findViewById(R.id.txtLastname)
            txtAddress = findViewById(R.id.txtAddress)
            val firstname = txtFirstname.text.toString()
            val lastname = txtLastname.text.toString()
            val address = txtAddress.text.toString()

            //Maybe you'll have to check 2 chip groups
            val ids: List<Int> = chips1.checkedChipIds
            for (id in ids) {
                val chip: Chip = chips1.findViewById(id)
                ingredients.add(chip.text.toString())
            }

            val mIntent =  Intent(Intent.ACTION_SEND)
            mIntent.data = Uri.parse("mailto:")
            mIntent.type = "text/plain"
            mIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "52103306")
            mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("fatmachaouech02@gmail.com"))
            mIntent.putExtra(Intent.EXTRA_SUBJECT, "NEW ORDER : ${address}")
            var type = "Hello!\n\n${firstname} ${lastname} just ordered a ${chosenSize} Pizza "
            if(ingredients.size == 0) type += "with no ingredients"
            else {
                type += "with these ingredients : "
                for (ingredient in ingredients) {
                    type += "${ingredient} "
                }
                type += ".\n\nDon't be late!"
            }
            mIntent.putExtra(Intent.EXTRA_TEXT, type)
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))


        }

    }
    private fun configure(chip: Chip, ) {
        chip.setOnClickListener {
            if(ingredients.contains(chip.text.toString()))
                ingredients.remove(chip.text.toString())
            else ingredients.add(chip.text.toString())
        }
    }

    override fun onRestart() {
        super.onRestart()
        val intent = Intent(this@OrderActivity,MainActivity::class.java)
        startActivity(intent)
    }

}