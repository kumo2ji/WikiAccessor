package com.wa.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.google.api.server.spi.response.InternalServerErrorException;

public class WikiAccessorApiTest {
  private static final WikiAccessorApi api = new WikiAccessorApi();

  @Test
  public void testGetWikiInfo() {
    try {
      final WikiInfo info = api.getWikiInfo("WORKING");
      assertThat(info.getTitle(), is("WORKING"));
      assertThat(info.getDirector().get(0), not(nullValue()));
      assertThat(info.getDirector().get(0), not(emptyString()));
      assertThat(info.getWriter().get(0), not(nullValue()));
      assertThat(info.getWriter().get(0), not(emptyString()));
      assertThat(info.getMusic().get(0), not(nullValue()));
      assertThat(info.getMusic().get(0), not(emptyString()));
      assertThat(info.getStudio(), not(nullValue()));
      assertThat(info.getStudio(), not(emptyString()));
    } catch (final InternalServerErrorException e) {
      e.printStackTrace();
    }
  }

}
