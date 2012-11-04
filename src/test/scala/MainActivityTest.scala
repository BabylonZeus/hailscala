
import android.widget.TextView
import fr.zeus.hailscala.{R, MainActivity}
import org.specs2.mock.Mockito
import com.github.jbrechtel.robospecs.RoboSpecs

/**
 * Created on 03/11/2012 10:30 PM with IntelliJ IDEA,
 * by the mighty babylonzeus in all His wisdom and glory.
 */
class MainActivityTest extends RoboSpecs with Mockito {
  "onCreate" should {
    "set the content view" in {
      val activity = new MainActivity()
      activity.onCreate(null)
      activity.findViewById(R.id.textview) must not beNull
    }
  }

  "messageEditText" should {
    "contain 'world'" in {
      val activity = new MainActivity()
      activity.onCreate(null)
      activity.findViewById(R.id.textview).asInstanceOf[TextView].getText.toString must contain("world")
    }
  }
}
