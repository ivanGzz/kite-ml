package learning

import models.Sentences

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nigonzalez on 3/1/17.
 */
object SentenceNet {

    def train: Future[Boolean] = {
        Sentences.getSentencesCount.flatMap { res =>
            if (res <= 1000) {
                throw new Exception()
            } else {
                val entries = res - res % 1000
                Sentences.getSentences(entries)
            }
        }.map { res =>

            true
        }.recover {
            case e => {
                e.printStackTrace()
                false
            }
        }
    }

    def split(text: String): List[String] = {
        val text_ = if (text.endsWith(".") || text.endsWith("?")) text else text + "."
        val breakpoints = for (i <- text_.indices if text_.charAt(i) == '.' || text_.charAt(i) == '?') yield i
        val sentences = breakpoints.foldLeft((List[String](), 0)) { (prev, n) =>
            val list: List[String] = prev._1
            val from = prev._2
            (text_.substring(from, n).trim :: list, n + 1)
        }
        sentences._1.reverse
    }

    def rateText(text: String, lang: String): Seq[(String, Boolean, Int)] = {
        lang match {
            case "es" => split(text).map { sentence =>
                val (inquiry, sentiment) = rateEs(sentence)
                (sentence, inquiry, sentiment)
            }
            case _ => split(text).map { sentence =>
                val (inquiry, sentiment) = rateEn(sentence)
                (sentence, inquiry, sentiment)
            }
        }
    }

    def rateSentence(text: String, lang: String): (String, Boolean, Int) = {
        lang match {
            case "es" => {
                val (inquiry, sentiment) = rateEs(text)
                (text, inquiry, sentiment)
            }
            case _ => {
                val (inquiry, sentiment) = rateEn(text)
                (text, inquiry, sentiment)
            }
        }
    }

    val commonGroundsEn = List("what if",
        "what do you think if",
        "how do you see if",
        "what are your thoughts on",
        "why not",
        "i suggest to",
        "i believe we should", "i recommend to",
        "how would you see if")

    val commonGroundsEs = List("que tal si",
        "como ven si",
        "como verian",
        "que opinan si",
        "porque no",
        "sugiero que",
        "creo que deberiamos",
        "recomiendo que",
        "que les pareceria si")

    def rateEs(text: String) = {
        val normalized = text.filter( c => c.isLetter || c.isSpaceChar).map(_.toLower)
        (commonGroundsEs.exists(normalized.startsWith), 0)
    }

    def rateEn(text: String) = {
        val normalized = text.filter( c => c.isLetter || c.isSpaceChar).map(_.toLower)
        (commonGroundsEn.exists(normalized.startsWith), 0)
    }

}
