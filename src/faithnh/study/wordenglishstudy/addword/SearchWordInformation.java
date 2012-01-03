package faithnh.study.wordenglishstudy.addword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import faithnh.study.wordenglishstudy.util.HttpClient;
import faithnh.study.wordenglishstudy.util.XMLParser;

public class SearchWordInformation {
	public static String searchTransrate(String word) throws Exception{
		String transrate;
		String url;
		String html;
		Pattern pattern;
		Matcher matcher;
		
		url = "http://eow.alc.co.jp/" + reviseWord(word) + "/UTF-8/?ref=sa";
		html = new String(HttpClient.getByteArrayFromURL(url));
		html = html.replace("\r", "");
		html = html.replace("&", "&amp;");
		html = html.replace("charset=\"shift-JIS\"", "charset=\"utf-8\"");
		pattern = Pattern.compile("<script.+?</script>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("<SCRIPT.+?</SCRIPT>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("<noscript.+?</noscript>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("<NOSCRIPT.+?</NOSCRIPT>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("<meta.+?>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("<META.+?>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("");
		pattern = Pattern.compile("(<img.+?)(>)", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("$1/$2");
		pattern = Pattern.compile("(<br[^/>]+?)(>)", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("$1/$2");
		pattern = Pattern.compile("(<input[^/>]+?)(>)", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("$1/$2");
		pattern = Pattern.compile("(<a.+?>)([^<]*?)(</a>)", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("$2");
		pattern = Pattern.compile("(=)([0-9a-zA-Z]+)", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("$1\"$2\"");
		pattern = Pattern.compile("(\n|\t)+", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		html = matcher.replaceAll("\n");
		html = html.replace("<br>", "<br/>");
		html = html.replace("<hr>", "<hr/>");
		html = html.replace("<meta>", "<meta/>");
		html = html.replace("//", "/");
		html = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + html;
		
		transrate = XMLParser.getXPath("//div[@id='resultsList']/ul/li[1]", html.getBytes());
		transrate = transrate.replace("Åy", "\nÅy");
		transrate = transrate.replace("ÅE", "\nÅE");
		transrate = transrate.replace("Åü", "\nÅü");
		transrate = transrate.replace("\n", "<br/>");
		return transrate;
	}
	
	public static String getPronunciationURI(String text) throws Exception{
		String html;
		String fileURI;
		Pattern pattern;
		Matcher matcher;
		html = HttpClient.getContentByPOSTMethod(
				"http://192.20.225.36/tts/cgi-bin/nph-nvdemo",
				"voice=crystal&txt="+text+"&speakButton=SPEAK");
		pattern = Pattern.compile("<A[^>]+?>", Pattern.DOTALL);
		matcher = pattern.matcher(html);
		if(matcher.find()){
			fileURI = matcher.group();
			pattern = Pattern.compile("(<A[^\"]+?)(\")([^\"]+?)(\"[^>]*?)(>)", Pattern.DOTALL);
			matcher = pattern.matcher(fileURI);
			fileURI = matcher.replaceAll("$3");
			fileURI = "http://192.20.225.36" + fileURI;
		}else{
			fileURI = null;
		}
		return fileURI;
		
	}
	public static String reviseWord(String word){
		return word.trim().replaceAll(" +", "+").toLowerCase();
	}
	
	public static String reviseInputWord(String word){
		return word.trim().replaceAll(" +", " ").toLowerCase();
	}
}
