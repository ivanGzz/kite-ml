package examples

import edu.stanford.nlp.parser.lexparser.LexicalizedParser

/**
 * Created by nigonzalez on 1/29/17.
 */
object SentenceAnalyzer {

    def parseSentence2(sentence: String): String = {
        val parser = LexicalizedParser.loadModel()
        val parse = parser.parse(sentence)
        parse.pennPrint()
        val label = parse.label().value()
        println(label)
        ""
    }

}
