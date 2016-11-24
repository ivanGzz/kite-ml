package examples

import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

/**
 * Created by nigonzalez on 11/2/16.
 */
object ExampleController extends Controller {

    def mnist = Action.async {
        Future {
            MnistExample.run
        }.map(result => Ok(result))
    }

    def sentiment(text: String) = Action {
        Ok(SentimentAnalyzer.mainSentiment(text).toString)
    }


}
