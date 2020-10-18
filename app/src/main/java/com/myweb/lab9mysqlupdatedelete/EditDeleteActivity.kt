    package com.myweb.lab9mysqlupdatedelete

    import android.os.Bundle
    import android.view.View
    import android.widget.Toast
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import kotlinx.android.synthetic.main.activity_edit_delete.*
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class EditDeleteActivity : AppCompatActivity() {
        val createClient = StudentAPI.create()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_delete)

            val mID = intent.getStringExtra("mID")
            val mName = intent.getStringExtra("mName")
            val mAge = intent.getStringExtra("mAge")

            edt_id.setText(mID)
            edt_id.isEnabled = false
            edt_name.setText(mName)
            edt_age.setText(mAge)

        }

        fun saveStudent(v: View) {
            createClient.updateStudent(
                edt_id.text.toString(),
                edt_name.text.toString(),
                edt_age.text.toString().toInt()
            )
                .enqueue(object : Callback<Student> {
                    override fun onResponse(call: Call<Student>, response: Response<Student>) {
                        if (response.isSuccessful) {
                            Toast.makeText(application, "Successfully Updated", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(application, "Update Failure", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Student>, t: Throwable) = t.printStackTrace()

                })
        }

        fun deleteStudent(v: View) {
            val mID = intent.getStringExtra("mID")
            val myBuilder = AlertDialog.Builder(this)
            myBuilder.apply {
                setTitle("Warning")
                setMessage("Do you want to delete the movie?")
                setNegativeButton("Yes") { dialog, which ->
                    createClient.deleteStudent(mID.toString())
                        .enqueue(object : Callback<Student> {
                            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(applicationContext, "Successfully Deleted", Toast.LENGTH_LONG).show()
                                }
                            }
                            override fun onFailure(call: Call<Student>, t: Throwable) {
                                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            }
                        })
                    finish()
                }
                setPositiveButton ("No"){ dialog, which ->dialog.cancel()}
                show()
            }
        }

    }

