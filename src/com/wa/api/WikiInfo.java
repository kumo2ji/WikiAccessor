package com.wa.api;

import java.util.List;

public class WikiInfo {
  public String title;
  public List<String> director;
  public List<String> writer;
  public List<String> music;
  public String studio;

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public List<String> getDirector() {
    return director;
  }

  public void setDirector(final List<String> director) {
    this.director = director;
  }

  public List<String> getWriter() {
    return writer;
  }

  public void setWriter(final List<String> writer) {
    this.writer = writer;
  }

  public List<String> getMusic() {
    return music;
  }

  public void setMusic(final List<String> music) {
    this.music = music;
  }

  public String getStudio() {
    return studio;
  }

  public void setStudio(final String studio) {
    this.studio = studio;
  }
}
