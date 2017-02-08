package controllers

import nlp.NLP
import play.api.mvc.{Action, Controller}
import play.api.libs.json.{JsError, JsSuccess, Json}

/**
 * Created by Nelson on 2/2/2017.
 */
object SentenceController extends Controller {

    case class InquiryRequest(sentence: String)
    case class InquiryResponse(inquiry: Boolean)

    implicit val inquiryRead = Json.reads[InquiryRequest]
    implicit val inquiryWrite = Json.writes[InquiryResponse]

    def post = Action(parse.json) { implicit request =>
        request.body.validate match {
            case JsSuccess(sentence, _) => Ok(Json.toJson(InquiryResponse(NLP.detectInquiry(sentence.sentence))))
            case JsError(errs) => BadRequest
        }
    }

}
