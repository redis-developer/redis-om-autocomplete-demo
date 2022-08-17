package com.redis.om.autocomplete.domain;

import org.springframework.data.annotation.Id;

import com.redis.om.spring.annotations.Document;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Document
public class Airport {
  @Id
  private String id;
  @NonNull
  private String name;
  @NonNull
  private String code;
  @NonNull
  private String state;
}
