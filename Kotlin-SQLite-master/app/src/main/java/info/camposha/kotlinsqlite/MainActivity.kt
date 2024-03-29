package info.camposha.kotlinsqlite

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    internal var dbHelper = DatabaseHelper(this)


    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }


    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }


    fun clearEditTexts(){
        nameTxt.setText("")
        classTxt.setText("")
        schoolTxt.setText("")
        idTxt.setText("")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }


    fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(nameTxt.text.toString(),classTxt.text.toString(),
                        schoolTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


    fun handleUpdates() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(idTxt.text.toString(),
                        nameTxt.text.toString(),
                        classTxt.text.toString(), schoolTxt.text.toString())
                if (isUpdate == true)
                    showToast("Data Updated Successfully")
                else
                    showToast("Data Not Updated")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


    fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }


    fun handleViewing() {
        viewBtn.setOnClickListener(
                View.OnClickListener {
                    val res = dbHelper.allData
                    if (res.count == 0) {
                        showDialog("Error", "No Data Found")
                        return@OnClickListener
                    }

                    val buffer = StringBuffer()
                    while (res.moveToNext()) {
                        buffer.append("ID :" + res.getString(0) + "\n")
                        buffer.append("NAME :" + res.getString(1) + "\n")
                        buffer.append("CLASS :" + res.getString(2) + "\n")
                        buffer.append("SCHOOL :" + res.getString(3) + "\n\n")
                    }
                    showDialog("Data Listing", buffer.toString())
                }
        )
    }
}
