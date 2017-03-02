package controllers

import java.sql.Date

import learning.SentenceNet
import models.{Sentences, Sentence}

import play.api.mvc.{Action, Controller}
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

/**
 * Created by Nelson on 2/2/2017.
 */
object SentenceController extends Controller {

    case class InquiryRequest(language: String, sentence: String)
    case class InquiryResponse(inquiry: Boolean, sentiment: Int)

    case class SentenceRequest(lang: String, content: String, sentiment: String, question: Boolean, commonGround: Boolean)

    implicit val inquiryRead = Json.reads[InquiryRequest]
    implicit val inquiryWrite = Json.writes[InquiryResponse]
    implicit val sentenceRead = Json.reads[SentenceRequest]
    implicit val sentenceWrite = Json.writes[Sentence]
    val rand = new Random()

    def post = Action.async(parse.json) { implicit request =>
        request.body.validate(sentenceRead) match {
            case JsSuccess(sentence, _) => {
                val today = new java.util.Date()
                val newSentence = Sentence(0L,
                    sentence.lang,
                    sentence.content,
                    sentence.sentiment,
                    sentence.question,
                    sentence.commonGround,
                    new Date(today.getTime))
                Sentences.addToSentences(newSentence).map(res =>
                    Ok
                )
            }
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action {
        Ok
    }

    def random = Action.async {
        Sentences.getSentencesCount.flatMap { res =>
            val skip = if (res > 0) rand.nextInt(res) else 0
            Sentences.getSentenceByPosition(skip)
        }.map {
            _ match {
                case Some(sentence) => Ok(Json.toJson(sentence))
                case None => NotFound
            }
        }
    }

    def commonGround = Action(parse.json) { implicit request =>
        request.body.validate(inquiryRead) match {
            case JsSuccess(sentence, _) => {
                val (inquiry, sentiment) = SentenceNet.rate(sentence.sentence)
                Ok(Json.toJson(InquiryResponse(inquiry, sentiment)))
            }
            case JsError(errs) => BadRequest
        }
    }

}
