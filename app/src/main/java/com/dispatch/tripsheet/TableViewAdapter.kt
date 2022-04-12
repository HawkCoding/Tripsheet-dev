package com.dispatch.tripsheet

    import android.R.array
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.*
    import androidx.recyclerview.widget.RecyclerView
    import kotlinx.android.synthetic.main.table_layout.view.*
    import kotlinx.android.synthetic.main.table_list_item.view.*


class TableViewAdapter(
    var tripsheetlist: Tripsheetlist,
    val driver: String,
    var tvHeader: TextView,
    val spnDriver: Spinner,
    var adapter: ArrayAdapter<String>
) : RecyclerView.Adapter<TableViewAdapter.RowViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_list_item, parent, false)




        var i: Int = 0
        var totT: Int = 1
        var a: Int = 0
        var b: Int = 0
        var c: Int = 0
        var l: Int = tripsheetlist.videos.size -1

        while (i <= l){

            if (driver == tripsheetlist.videos[i].Drivers) {

                if (a == 0){
                    a = tripsheetlist.videos[i].Tripsheet}

                totT = totT

                if(a != tripsheetlist.videos[i].Tripsheet) {

                    if (b == 0){
                        b = tripsheetlist.videos[i].Tripsheet}

                    if ( totT != 3){
                        totT = 2}

                    if(b != tripsheetlist.videos[i].Tripsheet) {
                        c = tripsheetlist.videos[i].Tripsheet
                        totT = 3

                    }
                }
            }
            i++
        }
        when (totT)
        {
            1 -> tvHeader.setText("Vulcan Steel Trip Sheet: " +a)
            2-> tvHeader.setText("Vulcan Steel Trip Sheet: " + a + " & " + b )
            3-> tvHeader.setText("Vulcan Steel Trip Sheet: " + a + " & " + b + " & " + c)
        }


        //Spinner needs to be tested

//            val driverList = tripsheetlist?.videos.distinctBy { it.Drivers }
            var h =0
//            while (h <= driverList.size){
//                adapter.add(driverList[h].Drivers.toString())
//                h++
//            }


        //used for testing
        while (h <= 4){
            adapter.add("driverList[h].Drivers.toString()"+h)
            h++
        }

        spnDriver.adapter = adapter



        return RowViewHolder(itemView) }

    override fun getItemCount(): Int { return tripsheetlist.videos.size + 1 // one more to add header row
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {

        val rowPos = holder.adapterPosition

        if (rowPos == 0) {
            holder.itemView.apply {
                setHeaderBg(txtWOrder)
                setHeaderBg(txtDElNote)
                setHeaderBg(txtCompany)
                setHeaderBg(txtWeight)
               // setHeaderBg(txttvdone)

                txtWOrder.text = "WOrder"
                txtDElNote.text = "DElNote"
                txtCompany.text = "Compan"
                txtWeight.text = "Weight"
               // txttvdone.text = ""
            }
        } else {
            val modal = tripsheetlist.videos[rowPos -1]

            holder.itemView.apply {
                setContentBg(txtWOrder)
                setContentBg(txtDElNote)
                setContentBg(txtCompany)
                setContentBg(txtWeight)
                setContentBg(txttvdone)

//                txtWOrder.text = modal.WOrder.toString()
//                txtDElNote.text = modal.DElNote.toString()
//                txtCompany.text = modal.Company
//                txtWeight.text = modal.Weight.toString()



                if (modal.Drivers == driver) {
                    txtWOrder.text = modal.WOrder.toString()
                    txtDElNote.text = modal.DElNote.toString()
                    txtCompany.text = modal.name
                    txtWeight.text = modal.Weight.toString()

                }
            }
        }

        //used for testing to look in concsole for responeses
        println(tripsheetlist.videos[rowPos  ].name) //name needs to be chagned to company
        println("where we need to see the state")
        println("-1")
        println(tripsheetlist.videos[rowPos  ].state)
        println("0")
        println(tripsheetlist.videos[0].state)
        println("+1")
        println(tripsheetlist.videos[1 ].state)




        if ( rowPos  != 0){
            val modal =tripsheetlist.videos[rowPos]

            //testing
            println("rowPos")
            println(rowPos)
            println(modal.state)


            holder.txttvdone.apply {
                setBackgroundResource(when (modal.state) {
                    DataState.Unselected -> android.R.color.transparent
                    DataState.Success -> R.color.green
                    DataState.Failure -> R.color.orange
                })
                text = when (modal.state) {
                    DataState.Unselected -> ""
                    DataState.Success -> "✓"
                    DataState.Failure -> "x"
                    //this is where I add code to export data through api maybe add it in the datastate set where it is success and Failure
                }
            }

            holder.apply {
                txtbutton1.setOnClickListener {
                    Log.e("Clicked", "Successful delivery")
                    //this is where I add code to export data through api
                    modal.state = DataState.Success
                    notifyDataSetChanged()
                }
                txtbutton2.setOnClickListener {
                    Log.e("Clicked", "Exception on delivery")

                    modal.state = DataState.Failure
                    notifyDataSetChanged()
                }
            }}

    }
     class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

         val txttvdone:TextView = itemView.findViewById<TextView>(R.id.txttvdone)
         val txtbutton1:Button = itemView.findViewById<Button>(R.id.txtbutton1)
         val txtbutton2:Button = itemView.findViewById<Button>(R.id.txtbutton2)
     }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

}