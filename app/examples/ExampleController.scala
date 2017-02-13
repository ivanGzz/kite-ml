package examples

import models.{AuditLogs, AuditLog}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

/**
 * Created by nigonzalez on 11/2/16.
 */
object ExampleController extends Controller {

    case class ParseRequest(sentence: String)

    implicit val sentenceRead = Json.reads[ParseRequest]

    def mnist = Action.async {
        Future {
            MnistExample.run
        }.map(result => Ok(result))
    }

    def sentiment(text: String) = Action.async { implicit request =>
      AuditLogs.addToLog(request.uri, "POST", "{payload: payload}").map(res =>
          Ok(SentimentAnalyzer.mainSentiment(text).toString)
      )
    }

    def parseSentence = Action(parse.json) { implicit request =>
      request.body.validate match {
          case JsSuccess(sentence, _) => {
              SentenceAnalyzer.parseSentence2(sentence.sentence)
              Ok
          }
          case JsError(errors) => BadRequest
      }
    }

}
