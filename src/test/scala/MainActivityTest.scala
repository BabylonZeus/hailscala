
import android.widget.TextView
import com.xtremelabs.robolectric.shadows.ShadowToast
import fr.zeus.hailscala.{R, MainActivity}
import org.specs2.mock.Mockito
import com.github.jbrechtel.robospecs.RoboSpecs

/**
 * Created on 03/11/2012 10:30 PM with IntelliJ IDEA,
 * by the mighty babylonzeus in all His wisdom and glory.
 */
class MainActivityTest extends RoboSpecs with Mockito {

  //Help on matchers : http://etorreborre.github.com/specs2/guide/org.specs2.guide.Matchers.html

  "onCreate" should {
    "set the content view" in {
      val activity = new MainActivity()
      activity.onCreate(null)
      activity.findViewById(R.id.textview) must not beNull
    }
  }

  "main TextView" should {
    "contain 'world'" in {
      val activity = new MainActivity()
      activity.onCreate(null)
      activity.findViewById(R.id.textview).asInstanceOf[TextView].getText.toString must contain("world")
    }
  }

  "when clicking on the 'Click Me' button" should {
    "show a toast popup with the same text of the main TextView" in {
      val activity = new MainActivity()
      activity.onCreate(null)
      activity.txtMessage.setText("où est toto ?")
      activity.btnClickMe.performClick()
      ShadowToast.getTextOfLatestToast must beEqualTo("où est toto ?")
    }
  }
}
