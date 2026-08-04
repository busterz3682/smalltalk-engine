package com.taeyn.smalltalk.topic;

import java.util.List;

public interface TopicRepository {

    Topic save(Topic topic);

    List<Topic> findAll();
}