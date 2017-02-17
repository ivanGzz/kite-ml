package nlp

import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.parser.lexparser.LexicalizedParser
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import nlp.Sentiment.Sentiment

import scala.collection.convert.wrapAll._

/**
 * Created by Nelson on 2/2/2017.
 */
object NLP {

    val inquiry = "SBARQ"

    val englishParser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz")
    val spanishParser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/spanishPCFG.ser.gz")

    val sentimentProperties = new Properties()
    sentimentProperties.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val sentimentPipeline: StanfordCoreNLP = new StanfordCoreNLP(sentimentProperties)

    def processSentence(sentence: String): (Boolean, Sentiment) = {
        (detectInquiry(sentence), sentiment(sentence))
    }

    def detectInquiry(sentence: String): Boolean = {
        val tree = englishParser.parse(sentence)
        tree.pennPrint()
        if (tree.numChildren() > 0) {
            val clause = tree.getChild(0)
            if (clause.label().value() == inquiry) {
                return true
            }
        }
        false
    }

    def sentiment(text: String): Sentiment = {
        val (_, sentiment) = sentiments(text).maxBy { case (sentence, _) => sentence.length }
        sentiment
    }

    def sentiments(text: String): List[(String, Sentiment)] = {
        val annotation: Annotation = sentimentPipeline.process(text)
        val sentences = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
        sentences
          .map(sentence => (sentence, sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])))
          .map { case (sentence, tree) => (sentence.toString, Sentiment.toSentiment(RNNCoreAnnotations.getPredictedClass(tree))) }
          .toList
    }

}
