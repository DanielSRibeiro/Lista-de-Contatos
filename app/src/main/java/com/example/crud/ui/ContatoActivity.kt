package com.example.crud.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.crud.data.model.Contato
import com.example.crud.data.dataBase.DataBaseHandler
import com.example.crud.R
import kotlinx.android.synthetic.main.activity_contato.*
import java.lang.Exception

class ContatoActivity : AppCompatActivity() {

    private val TAG = "ContatoActivity"
    private var dataBaseHandler = DataBaseHandler(this)
    private var keyContato: Contato? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        setupView()
    }

    private fun setupView() {
        setSupportActionBar(toolbarContato)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        keyContato = intent.getParcelableExtra("contato")

        if(keyContato != null) {
            buttonExcluir.visibility = View.VISIBLE
            edt_nome.setText(keyContato?.nome)
            edt_telefone.setText(keyContato?.telefone)
        } else buttonExcluir.visibility = View.INVISIBLE
    }

    fun onClickEcluir(view: View) {
        if(keyContato != null){
            try {
                dataBaseHandler.deleteContato(keyContato!!.id)
                finish()
            } catch (ex: Exception){
                Log.d(TAG, ex.toString())
            }
        }
    }

    fun onClickSalvar(view: View) {
        var nome = edt_nome.text.toString()
        var telefone = edt_telefone.text.toString()
        var contato = Contato(0, nome, telefone)

        if(keyContato != null){
            try {
                dataBaseHandler.updateContato(keyContato!!.id, contato)
                finish()
            } catch (ex: Exception){
                ex.toString()
            }
        } else{
            try {
                dataBaseHandler.insertContato(contato)
                finish()
            } catch (ex : Exception){
                Log.d(TAG, ex.toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}