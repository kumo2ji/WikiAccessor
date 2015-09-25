package com.wa.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.InternalServerErrorException;

@Api(name = "wikiAccessor")
public class WikiAccessorApi {
  private static final String WIKI_ENDPOINT =
      "https://ja.wikipedia.org/w/api.php?action=query&prop=revisions&format=xml&rvprop=content&redirects=&titles=";

  @ApiMethod(path = "{title}", name = "get", httpMethod = HttpMethod.GET)
  public WikiInfo getWikiInfo(@Named("title") final String title)
      throws InternalServerErrorException {
    try {
      final String urlString = WIKI_ENDPOINT + URLEncoder.encode(title, "UTF-8");
      final String revText = parseRevText(urlString);
      final String text = getTvAnimeInfoboxText(revText);
      final WikiInfo info = new WikiInfo();
      info.setTitle(title);
      info.setDirector(splitToList(getValue(text, "監督")));
      info.setWriter(splitToList(getValue(text, "脚本")));
      info.setMusic(splitToList(getValue(text, "音楽")));
      info.setStudio(getValue(text, "アニメーション制作"));
      return info;
    } catch (final IOException | ParserConfigurationException | SAXException e) {
      e.printStackTrace();
      throw new InternalServerErrorException(e);
    }
  }

  private String parseRevText(final String url)
      throws ParserConfigurationException, SAXException, IOException {
    final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    final Document document = builder.parse(url);
    final NodeList nodeList = document.getElementsByTagName("rev");
    final Node node = nodeList.item(0);
    return node.getTextContent();
  }

  private String getTvAnimeInfoboxText(final String input) {
    final Pattern pattern = Pattern.compile("\\{\\{Infobox animanga/TVAnime([\\s\\S]+?)\\}\\}");
    final Matcher matcher = pattern.matcher(input);
    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return "";
    }
  }

  private String getValue(final String text, final String key) {
    final Pattern pattern = Pattern.compile("^\\|\\s*" + key + "\\s*=\\s*(.+)$", Pattern.MULTILINE);
    final Matcher matcher = pattern.matcher(text);
    if (matcher.find()) {
      return removeSquareBracket(matcher.group(1));
    } else {
      return "";
    }
  }

  private String removeSquareBracket(final String input) {
    return input.replaceAll("\\[|\\]", "");
  }

  private List<String> splitToList(final String input) {
    return Arrays.asList(input.split(",|、|<br ?/?>"));
  }
}
