package controllers

import nlp.NLP
import nlp.Sentiment.Sentiment
import play.api.mvc.{Action, Controller}
import play.api.libs.json.{JsError, JsSuccess, Json}

/**
 * Created by Nelson on 2/2/2017.
 */
object SentenceController extends Controller {

    case class InquiryRequest(language: String, sentence: String)
    case class InquiryResponse(inquiry: Boolean, sentiment: Sentiment)

    implicit val inquiryRead = Json.reads[InquiryRequest]
    implicit val inquiryWrite = Json.writes[InquiryResponse]

    def get = Action {
        Ok
    }

    def post = Action {
        Ok
    }

    def put = Action {
        Ok
    }

    def random = Action {
        Ok
    }

    def commonGround = Action(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(sentence, _) => {
                val (inquiry, sentiment) = NLP.processSentence(sentence.sentence)
                Ok(Json.toJson(InquiryResponse(inquiry, sentiment)))
            }
            case JsError(errs) => BadRequest
        }
    }

}
