package com.example.adam.allinthefamily

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.widget.Button
import com.example.adam.allinthefamily.NamesAdapter.NameItem

class GameActivity : AppCompatActivity() {

    companion object {
        const val NAMES_INTENT_EXTRA_KEY = "names"
    }

    private lateinit var namesAdapter: NamesAdapter
    private lateinit var namesList: RecyclerView

    private lateinit var newGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        namesAdapter = NamesAdapter()
        namesList = findViewById<RecyclerView>(R.id.names_list).apply {
            adapter = namesAdapter
            layoutManager = LinearLayoutManager(context, VERTICAL, false)

            val itemSpacing = resources.getDimensionPixelOffset(R.dimen.game_name_item_spacing)
            addItemDecoration(NamesItemDecoration(itemSpacing))
        }

        newGameButton = findViewById<Button>(R.id.new_game_button).apply {
            setOnClickListener { finish() }
        }

        setupNames()
    }

    private fun setupNames() {
        val names = intent.extras?.getStringArrayList(NAMES_INTENT_EXTRA_KEY) ?: emptyList<String>()
        val nameCountMap: Map<String, Int> = HashMap<String, Int>().apply {
            names.forEach { name ->
                val normalized = name.toLowerCase().trim()
                val updatedCount = (this[normalized] ?: 0) + 1
                this[normalized] = updatedCount
            }
        }

        val nameItems = nameCountMap.entries.map { (name, count) -> NameItem(name, count) }
        namesAdapter.setNames(nameItems)
    }
}
