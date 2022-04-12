package com.dispatch.tripsheet


import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerViewTripsheetlist.layoutManager = LinearLayoutManager(this)

        val spnDriver: Spinner = findViewById(R.id.spnDriver)

        var adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
//        spnDriver.adapter = adapter
//
//        for(Drivers in tripsheetlist?.videos!!){
//            adapter.add("Drivers.toString()")
//        }



        fetchJson( adapter, spnDriver)


    }


    private fun fetchJson(adapter: ArrayAdapter<String>, spnDriver: Spinner){

        println("Attempting to Fetch JSON")

        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val tripsheetlist = gson.fromJson(body, Tripsheetlist::class.java)

                //insert spinner here with driver select

                    weightsum(tvTotalweight, tripsheetlist)
                    totaldelNotes(tvTotaldelv,tripsheetlist)

                var driver : String = Spinner(tripsheetlist, adapter)

                 //   tripsheetnum(tvHeader, tripsheetlist, driver)

                runOnUiThread {
                    recyclerViewTripsheetlist.adapter = TableViewAdapter(tripsheetlist, driver, tvHeader, spnDriver, adapter)
                }
            }
        })
    }

    private fun Spinner(tripsheetlist: Tripsheetlist, adapter: ArrayAdapter<String>): String {


        var driver : String = ""
        var item : String = ""
        var rowTot : Int = tripsheetlist?.videos.size



//        for (Drivers in tripsheetlist?.videos!!){
//            adapter.add(Drivers.toString())
//        }


        spnDriver.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var rowPos : Int = 0
                while (rowPos != rowTot){
                    item  = tripsheetlist.videos[p2].Drivers.toString()
                    rowPos = rowTot
                }

                Toast.makeText(this@MainActivity, "Driver $item selected", Toast.LENGTH_SHORT).show()
                driver = item
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                driver = ""
                //empty
            }

            //  insert code that activates data pull of tripsheet for driver= actifavte by method the class/object that activates the data pull. so datapuul(Driver)
        }

        return driver





    }

//    private fun tripsheetnum(tvHeader: TextView, tripsheetlist: Tripsheetlist, driver: String) {
//        var i: Int = 0
//        var totT: Int = 1
//        var a: Int = 0
//        var b: Int = 0
//        var c: Int = 0
//        var l: Int = tripsheetlist.videos.size -1
//
//        while (i <= l){
//
//            if (driver == tripsheetlist.videos[i].Drivers) {
//
//                if (a == 0){
//                a = tripsheetlist.videos[i].Tripsheet}
//
//                totT = totT
//
//                if(a != tripsheetlist.videos[i].Tripsheet) {
//
//                    if (b == 0){
//                        b = tripsheetlist.videos[i].Tripsheet}
//
//                    if ( totT != 3){
//                    totT = 2}
//
//                    if(b != tripsheetlist.videos[i].Tripsheet) {
//                        c = tripsheetlist.videos[i].Tripsheet
//                        totT = 3
//
//                    }
//                }
//            }
//            i++
//        }
//                        when (totT)
//                        {
//                            1 -> tvHeader.setText("Vulcan Steel Trip Sheet: " +a)
//                            2-> tvHeader.setText("Vulcan Steel Trip Sheet: " + a + " & " + b )
//                            3-> tvHeader.setText("Vulcan Steel Trip Sheet: " + a + " & " + b + " & " + c)
//                        } }

    private fun totaldelNotes(tvTotaldelv: TextView, tripsheetlist: Tripsheetlist) {

       var totnotes :Int = tripsheetlist.videos.size -1
        tvTotaldelv.setText("Total Delivery Notes: " +totnotes)
    }

    private fun weightsum(tvTotalweight: TextView, tripsheetlist: Tripsheetlist) {

    fun weightsum(args: TextView, Tripsheetlist: ArrayList<DataModel>) {
        var totweight: Int = 0
        var sum: Int = 0

        for (i in 0 until tripsheetlist.videos.size -1) {
            sum +=  Tripsheetlist[i].Weight


        }

        totweight = sum
        tvTotalweight.setText("Total Weight: " + totweight + "kg")

    }
    }


//    private fun Spinner(): String {
//

//        var Driver : String = ""
//        val list : MutableList<String> = ArrayList()
//        list.add("")
//        list.add("Deon")
//        list.add("Leon")
//        list.add("David")
//        list.add("Dick")
//        list.add("Jim")
//        list.add("Harry")
//
//        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
//        //   val adapter = ArrayAdapter( this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list)
//        val spinner: Spinner = findViewById(R.id.spnDriver)
//        spinner.adapter = adapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                val  item  = list[p2]
//                Toast.makeText(this@MainActivity, "Driver $item selected", Toast.LENGTH_SHORT).show()
//                Driver = item
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                Driver = ""
//                //empty
//            }
//
//            //  insert code that activates data pull of tripsheet for driver= actifavte by method the class/object that activates the data pull. so datapuul(Driver)
//        }
//
//        return Driver
//    }


    //    val Tripsheetlist = ArrayList<DataModel>().apply {
//
//
//        add(DataModel(190617, 182832, "", 0,"", "",""))
//          // Need one row for dummy data. Gets removed so chance wont be seen.
//        add(DataModel(190617, 182832, "Jcorp", 100,"Delivery done", "Delivery not done",""))
//        add(DataModel(190617, 182833, "Honda", 100,"No exceptions", "Exceptions",""))
//        add(DataModel(190617, 182832, "Everflu", 100,"100%", "50%",""))
//        add(DataModel(190617, 182832, "Panado", 300,"OK", "NO",""))
//        add(DataModel(190617, 182832, "Gheiters", 100,"Success", "Failed",""))
//        add(DataModel(190617, 182832, "John", 300,"Yes", "No",""))
//
//    // need to change from hardcoded text to input
////
////          btnLoadData.setOnClickListener(View.OnClickListener {
//          //after button has been clicked- will be set to spinner- add this data to the tables.
//
//          //these values will change from sql input
//          var a  = 10000
//          var b  = 10000
//          var c   = "Absa"
//          var d = 50
//          var e = "No exceptions"
//          var f = "Exceptions"
//          var g = ""
//
//            // value to changes based on data size.
//          var x: Int = 29
//
//
//          for (i in 20..x)
//          {
//            add(DataModel(a,b,c,d,e,f,g))
//
//              a++
//          }
//        //  })
//
//     }
//setData is not used yet

    private fun setData() {


   // val btnLoadData: Button = findViewById(R.id.btnLoadData)


//    btnLoadData.setOnClickListener(View.OnClickListener {
//var x: Int = 2
//        for (i in 3..6)
//        {       //does not work
//            this.Tripsheetlist[x].WOrder  = 20022
//            this.Tripsheetlist[x].DElNote  = 20022
//            this.Tripsheetlist[x].name     = "FNB"
//            this.Tripsheetlist[x].Weight  = 20
//            this.Tripsheetlist[x].Button1 = "No exceptions"
//            this.Tripsheetlist[x].Button2 = "Exceptions"
//            this.Tripsheetlist[x].tvdone = ""
//            x++
//        }
//
//        btnLoadData.setBackgroundColor(getColor(R.color.green))
//
//    })

    }

//works
    fun limitDropDownHeight(spinner: Spinner){
        val popup = Spinner::class.java.getDeclaredField( "mPopup")
        popup.isAccessible = true
        val popupWindow = popup.get(spinner)as ListPopupWindow
        popupWindow.height = (200 * resources.displayMetrics.density).toInt()
    }
}


