package learning

/**
 * Created by nigonzalez on 3/1/17.
 */
object SentenceNet {

    def split(text: String): List[String] = {
        val breakpoints = for (i <- text.indices if text.charAt(i) == '.' || text.charAt(i) == '?') yield i
        val sentences = breakpoints.foldLeft((List[String](), 0)) { (prev, n) =>
            val list: List[String] = prev._1
            val from = prev._2
            (text.substring(from, n) :: list, n)
        }
        sentences._1.reverse
    }

    def rateText(text: String, lang: String) = {
        lang match {
            case "es" => split(text).map(rateEs)
            case _ => split(text).map(rateEn)
        }
    }

    def rateSentence(text: String, lang: String) = {
        lang match {
            case "es" => rateEs(text)
            case _ => rateEn(text)
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
