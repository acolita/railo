package railo.runtime.search.lucene2.analyzer;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;


/**
 * <p>Analyzer for Italian language</p>
 */
public final class ItalianAnalyzer extends Analyzer {

	private static SnowballAnalyzer analyzer;

	private final static String[] STOP_WORDS = { "a", "abbia",
			"abbiamo", "abbiano", "abbiate", "ad", "agl", "agli", "ai",
			"al", "all", "alla", "alle", "allo", "anche", "avemmo",
			"avendo", "avesse", "avessero", "avessi", "avessimo",
			"aveste", "avesti", "avete", "aveva", "avevamo", "avevano",
			"avevate", "avevi", "avevo", "avrˆ", "avrai", "avranno",
			"avrebbe", "avrebbero", "avrei", "avremmo", "avremo",
			"avreste", "avresti", "avrete", "avr˜", "avuta", "avute",
			"avuti", "avuto", "c", "che", "chi", "ci", "coi", "come",
			"con", "contro", "cui", "da", "dagl", "dagli", "dai",
			"dal", "dall", "dalle", "dallo", "degl", "degli", "dei",
			"del", "dell", "della", "delle", "dello", "di", "dov",
			"dove", "e", "", "ebbe", "ebbero", "ebbi", "ed", "erano",
			"eravamo", "eravate", "eri", "ero", "essendo", "fa", "fˆ",
			"facciamo", "facciano", "faccio", "facemmo", "facendo",
			"facesse", "facessero", "facessi", "facessimo", "faceste",
			"facesti", "faceva", "facevamo", "facevano", "facevate",
			"facevi", "facevo", "fai", "fanno", "farˆ", "farai",
			"faranno", "farebbe", "farebbero", "farei", "faremmo",
			"faremo", "fareste", "faresti", "farete", "far˜", "fece",
			"fecero", "fossero", "fossimo", "foste", "fosti", "fu",
			"fui", "fummo", "furono", "gli", "ha", "hai", "hanno",
			"ho", "i", "il", "in", "io", "l", "la", "lˆ", "le", "lei",
			"li", "l“", "lo", "loro", "lui", "ma", "mi", "mia", "mie",
			"miei", "mio", "ne", "negl", "negli", "nei", "nel", "nell",
			"nella", "nelle", "nello", "noi", "non", "nostra",
			"nostre", "nostri", "nostro", "o", "per", "perchŽ", "pi",
			"quale", "quanta", "quante", "quanti", "quanto", "quella",
			"quelle", "quelli", "quello", "questa", "queste", "questi",
			"questo", "sarˆ", "sarai", "saranno", "sarebbe",
			"sarebbero", "sarei", "saremmo", "saremo", "sareste",
			"saresti", "sarete", "sar˜", "se", "sei", "si", "s“",
			"sia", "siamo", "siano", "siate", "siete", "sono", "sta",
			"stai", "stando", "stanno", "starˆ", "starai", "staranno",
			"starebbe", "starebbero", "starei", "staremmo", "staremo",
			"stareste", "staresti", "starete", "star˜", "stava",
			"stavamo", "stavano", "stavate", "stavi", "stavo",
			"stemmo", "stesse", "stessero", "stessi", "stessimo",
			"steste", "stesti", "stette", "stettero", "stetti", "stia",
			"stiamo", "stiano", "stiate", "sto", "su", "sua", "sue",
			"sugl", "sugli", "sui", "sul", "sull", "sulla", "sulle",
			"sullo", "suo", "suoi", "ti", "tra", "tu", "tua", "tue",
			"tuo", "tuoi", "tutti", "tutto", "un", "una", "uno", "vi",
			"voi", "vostra", "vostre", "vostri", "vostro" };


	/**
	 * Creates new instance of SpanishAnalyzer
	 */
	public ItalianAnalyzer() {
		analyzer = new SnowballAnalyzer("Italian", STOP_WORDS);
	}

	public ItalianAnalyzer(String stopWords[]) {
		analyzer = new SnowballAnalyzer("Italian", stopWords);
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		return analyzer.tokenStream(fieldName, reader);
	}
}