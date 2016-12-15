package examples

import models.AuditLog
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

    def sentiment(text: String) = Action.async { implicit request =>
      AuditLog.addToLog(request.uri, "{payload: payload}").map(res =>
          Ok(SentimentAnalyzer.mainSentiment(text).toString)
      )
    }


}
