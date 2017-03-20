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
    case class InquiryResponse(sentence: String, common_ground: Boolean, sentiment: Int)

    case class SentenceRequest(lang: String, content: String, sentiment: String, question: Boolean, common_ground: Boolean)
    case class SentenceUpdate(id: Long, sentiment: String, question: Boolean, common_ground: Boolean)

    implicit val inquiryRead = Json.reads[InquiryRequest]
    implicit val inquiryWrite = Json.writes[InquiryResponse]
    implicit val sentenceRead = Json.reads[SentenceRequest]
    implicit val sentenceWrite = Json.writes[Sentence]
    implicit val sentenceUpdate = Json.reads[SentenceUpdate]

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
                    sentence.common_ground,
                    new Date(today.getTime))
                Sentences.addToSentences(newSentence).map(res =>
                    Ok
                )
            }
            case JsError(errors) => Future(BadRequest)
        }
    }

    def put = Action.async(parse.json) { implicit request =>
        request.body.validate(sentenceUpdate) match {
            case JsSuccess(update, _) => {
                Sentences.updateSentence(update.id, update.sentiment, update.question, update.common_ground).map(res =>
                    Ok
                )
            }
            case JsError(errors) => Future(BadRequest)
        }
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
                val (_, inquiry, sentiment) = SentenceNet.rateSentence(sentence.sentence, sentence.language)
                Ok(Json.toJson(InquiryResponse(sentence.sentence, inquiry, sentiment)))
            }
            case JsError(errs) => BadRequest
        }
    }

    def train = Action {
        Ok
    }

    def text = Action(parse.json) { implicit request =>
        request.body.validate(inquiryRead) match {
            case JsSuccess(text, _) => {
                val response = SentenceNet.rateText(text.sentence, text.language).map { sentence =>
                    val (sentence_, inquiry, sentiment) = sentence
                    InquiryResponse(sentence_, inquiry, sentiment)
                }
                Ok(Json.toJson(response))
            }
            case JsError(errors) => BadRequest
        }
    }

}
