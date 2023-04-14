package com.example.morningfirebasedbapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIdNumber:EditText
    lateinit var btnsave:Button
    lateinit var btnview:Button
    lateinit var ProgressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.Edtname)
        edtEmail = findViewById(R.id.Edtemail)
        edtIdNumber = findViewById(R.id.Edtnumber)
        btnsave = findViewById(R.id.Btnsave)
        btnview = findViewById(R.id.Btnview)
        ProgressDialog = ProgressDialog(this)
        ProgressDialog.setTitle("saving")
        ProgressDialog.setMessage("PLEASE WAIT....")

        btnsave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            var id = System.currentTimeMillis().toString()
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
                var user = User(name, email, idNumber, id)
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
                        Toast.makeText(this,"user saved successfully",
                             Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"user saving failed",
                        Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        btnview.setOnClickListener {

        }





    }
}