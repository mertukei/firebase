package com.example.morningfirebasedbapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class CustomAdapter(var context: Context, var data:ArrayList<User>):BaseAdapter() {
    private class ViewHolder(row:View?){
        var mTxtName:TextView
        var mTxtEmail:TextView
        var mTxtidnumber:TextView
        var btndelete:Button
        var btnupdate:Button
        init {
            this.mTxtName = row?.findViewById(R.id.mtvname) as TextView
            this.mTxtEmail = row?.findViewById(R.id.mtvemail) as TextView
            this.mTxtidnumber = row?.findViewById(R.id.mtvidnumber) as TextView
            this.btndelete = row?.findViewById(R.id.mbtndelete) as Button
            this.btnupdate = row?.findViewById(R.id.mbtnupdate) as Button
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View?
        var viewHolder:ViewHolder
        if (convertView == null){
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.user_layout,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var item:User = getItem(position) as User
        viewHolder.mTxtName.text = item.name
        viewHolder.mTxtEmail.text = item.email
        viewHolder.mTxtidnumber.text = item.idNumber
        viewHolder.btndelete.setOnClickListener{
            var ref = FirebaseDatabase.getInstance().
            getReference().child("Users/"+item.id)
            ref.removeValue().addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(context,"user deleted successfully",
                    Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,it.exception?.message,
                    Toast.LENGTH_LONG).show()
                }
            }
        }
        viewHolder.btnupdate.setOnClickListener {
            var intent = Intent(context,UserupdateActivity::class.java)
            intent.putExtra("name",item.name)
            intent.putExtra("email",item.email)
            intent.putExtra("idnumber",item.idNumber)
            intent.putExtra("id",item.id)
            context.startActivity(intent)
        }
        return view as View
    }

    override fun getItem(position: Int): Any {
        return  data.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.count()
    }
}