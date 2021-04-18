package com.example.crud.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud.data.model.Contato
import com.example.crud.data.dataBase.DataBaseHandler
import com.example.crud.R
import com.example.crud.ui.adapter.ContatoAdapter
import com.example.crud.ui.adapter.OnClickItemContatoListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer.*

class MainActivity : AppCompatActivity() , OnClickItemContatoListener {
    private val TAG = "MainActivity"
    private var dataBaseHandler = DataBaseHandler(this)
    private var adapter: ContatoAdapter? = null
    private var listContato = ArrayList<Contato>()
    private lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        listContato = dataBaseHandler.selectContato()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        listContato = dataBaseHandler.selectContato()
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.item_menu_1 -> {mensagemToast("Teste do Item menu 1")}
            R.id.item_menu_2 -> {mensagemToast("Teste do Item menu 2")}
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mensagemToast(s: String): Boolean {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        return true
    }

    fun setupRecyclerView(){
        progressBar.visibility = View.VISIBLE

        Thread(Runnable {
            Thread.sleep(1000)

            runOnUiThread {
                adapter = ContatoAdapter(listContato, this)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter
                progressBar.visibility = View.INVISIBLE

            }
        }).start()
    }

    fun onClickFltButton(view: View) {
        var intent = Intent(this, ContatoActivity::class.java)
        startActivity(intent)
    }

    fun onClickBuscar(view: View) {
        var nome =  edt_buscar.text.toString()
        listContato = dataBaseHandler.likeContato(nome)
        setupRecyclerView()
    }

    override fun clickContato(contato: Contato) {
        var intent = Intent(this, ContatoActivity::class.java)
        intent.putExtra("contato",contato)
        intent.putExtra("key", contato.id)
        startActivity(intent)
    }
}