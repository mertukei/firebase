package com.example.morningfirebasedbapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserupdateActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIdNumber: EditText
    lateinit var ProgressDialog: ProgressDialog
    lateinit var btnupdate:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdate)
        edtName = findViewById(R.id.Edtname)
        edtEmail = findViewById(R.id.Edtemail)
        edtIdNumber = findViewById(R.id.Edtidnumber)
        btnupdate = findViewById(R.id.btnuserupdate)
        ProgressDialog = ProgressDialog(this)
        ProgressDialog.setTitle("Updating")
        ProgressDialog.setMessage("PLEASE WAIT....")
        //Receive data from intent
        var receivedName =intent.getStringExtra("name")
        var receivedEmail =intent.getStringExtra("email")
        var receivedIdNumber =intent.getStringExtra("idNumber")
        var receivedId =intent.getStringExtra("id")
        //set the received data to edit text
        edtName.setText(receivedName)
        edtEmail.setText(receivedEmail)
        edtIdNumber.setText(receivedIdNumber)
        //Set onclick listener on the button update
        btnupdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = receivedId
            if (name.isEmpty()){
                edtName.setError("please fill this field")
                edtName.requestFocus()
            }else if (email.isEmpty()){
                edtEmail.setError("please fill this field")
                edtEmail.requestFocus()
            }else if(idNumber.isEmpty()){
                edtIdNumber.setError("please fill this field")
                edtIdNumber.requestFocus()
            }else{
                //proceed to save
                var user = User(name, email, idNumber, id!!)
                // create a refference to the firebase database
                var ref = FirebaseDatabase.getInstance().
                getReference().child("Users/"+id)
                // show the programs to start saving
                ProgressDialog.show()
                ref.setValue(user).addOnCompleteListener(){
                    // Dismiss the progress and check if the task was successful
                        task->
                    ProgressDialog.dismiss()
                    if (task.isSuccessful){
                        Toast.makeText(this,"user updated successfully",
                            Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UserupdateActivity,UsersActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"user updating failed",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}