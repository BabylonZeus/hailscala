package fr.zeus.hailscala

import _root_.android.app.Activity
import _root_.android.os.Bundle
import android.widget.{TextView, Toast, Button}
import AndroidImplicits._
class MainActivity extends Activity with TypedActivity {

  def btnClickMe = findViewById(R.id.button).asInstanceOf[Button]
  def txtMessage = findViewById(R.id.textview).asInstanceOf[TextView]

  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    txtMessage.setText("hello, world!")

    btnClickMe.setOnClickListener(() => showMessage())

  }

  def showMessage() {
    Toast.makeText(this, txtMessage.getText, Toast.LENGTH_SHORT).show()
  }
}
