package learning

import java.io.File
import java.sql.Date

import models.{Networks, Network, Sentences}
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer

import org.deeplearning4j.models.word2vec.Word2Vec
import org.deeplearning4j.text.sentenceiterator.{SentencePreProcessor, CollectionSentenceIterator}
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by nigonzalez on 3/1/17.
 */
object SentenceNet {

    val pathToSaveW2V = "public/files/word-vec-"
    val pathToSaveNet = "public/files/sentences-"

    var word2vec: Option[Word2Vec] = None

    def train: Future[Boolean] = {
        Sentences.getSentencesCount.flatMap { res =>
            if (res <= 1000) {
                throw new Exception()
            } else {
                val entries = res - res % 1000
                Sentences.getSentences(entries)
            }
        }.flatMap { res =>
            val iterator = new CollectionSentenceIterator(scala.collection.JavaConversions.seqAsJavaList(res.map(_.content)))
            iterator.setPreProcessor(new SentencePreProcessor {
                override def preProcess(s: String): String = s.toLowerCase
            })
            val tokenizer = new DefaultTokenizerFactory()
            tokenizer.setTokenPreProcessor(new CommonPreprocessor())
            val vec = new Word2Vec.Builder()
              .minWordFrequency(5)
              .iterations(1)
              .layerSize(100)
              .seed(42)
              .windowSize(5)
              .iterate(iterator)
              .tokenizerFactory(tokenizer)
              .build()
            vec.fit()
            word2vec = Some(vec)
            val today = new java.util.Date()
            val file = new File(pathToSaveW2V + today.getTime + ".zip")
            WordVectorSerializer.writeWord2VecModel(vec, file)
            val network = Network(0L, "word_vector", "/assets/files/" + file.getName, 1, new Date(today.getTime))
            Networks.addToNetworks(network).map { r =>
                res
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
