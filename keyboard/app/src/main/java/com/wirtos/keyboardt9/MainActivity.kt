package com.wirtos.keyboardt9


import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.springboard_item_preference_switch.view.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val context = this
    val keyMaps = mapOf(

        R.xml.number_pad_en to "English",
        R.xml.number_pad_uk to "Українська",
        R.xml.number_pad_ru to "Русский"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val lngList = findViewById<LinearLayout>(R.id.langslist)
        val keyMapsRev: HashMap<String, Int> = HashMap()
        keyMaps.forEach { keyMapsRev[it.value] = it.key }

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        var prf = preferences.getString("languages", null)
        var setLangs = if (prf != null) JSONObject(prf) else JSONObject(keyMapsRev as Map<*, *>)

        for ((xml_id, lang) in keyMaps) {


            val switch =
                LayoutInflater.from(
                    this
                ).inflate(R.layout.springboard_item_preference_switch, null)

            switch.title.text = lang
            switch.sw.isChecked =
                setLangs.has(lang)

            switch.sw.setOnCheckedChangeListener { button, isChecked ->
                Log.d("LOL", isChecked.toString())
                prf = preferences.getString("languages", null)
                setLangs = if (prf != null) JSONObject(prf) else JSONObject(keyMapsRev as Map<*, *>)

                if (isChecked) {
                    setLangs.put(lang, xml_id)
                    preferences.edit().putString("languages", setLangs.toString()).apply()
                    Log.d("LOL", setLangs.toString())
                } else {
                    if (setLangs.length() < 2) {
                        button.isChecked = true
                        Toast.makeText(
                            this,
                            "At least one language should be enabled",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        setLangs.remove(lang)
                        preferences.edit().putString("languages", setLangs.toString()).apply()
                        Log.d("LOL", setLangs.toString())
                    }
                }
            }

            lngList.addView(switch)
        }
    }

}
