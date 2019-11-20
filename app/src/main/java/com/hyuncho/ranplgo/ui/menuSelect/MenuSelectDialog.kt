package com.hyuncho.ranplgo.ui.menuSelect

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.hyuncho.ranplgo.R
import com.hyuncho.ranplgo.ui.guesser.GuesserActivity
import com.hyuncho.ranplgo.ui.tracking.TrackingActivity
import com.hyuncho.ranplgo.ui.visitor.VisitorActivity

class MenuSelectDialog (context: Context){
    val context : Context = context

    val edialog : LayoutInflater = LayoutInflater.from(context)
    val mView : View = edialog.inflate(R.layout.dialog_menu_select,null)

    val menu_select_to_guesser : Button = mView.findViewById(R.id.menu_select_to_gueeser)
    val menu_select_to_visitor : Button = mView.findViewById(R.id.menu_select_to_visitor)
    val menu_select_to_tracking : Button = mView.findViewById(R.id.menu_select_to_tracking)

    fun getView() : View{
        menu_select_to_guesser.setOnClickListener {
            val nextIntent = Intent(context, GuesserActivity::class.java)
            context.startActivity(nextIntent)
        }
        menu_select_to_tracking.setOnClickListener {
            val nextIntent = Intent(context, TrackingActivity::class.java)
            context.startActivity(nextIntent)
        }
        menu_select_to_visitor.setOnClickListener {
            val nextIntent = Intent(context, VisitorActivity::class.java)
            context.startActivity(nextIntent)
        }
        return mView;
    }
}