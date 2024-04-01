package com.ll.surl.domain.surl.surl.controller;

import com.ll.surl.domain.surl.surl.entity.Surl;
import com.ll.surl.domain.surl.surl.service.SurlService;
import com.ll.surl.global.rq.Rq;
import com.ll.surl.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
public class ApiV1SurlController {
    private final SurlService surlService;
    private final Rq rq;

    public record SurlCreateRequestBody(
            @NotBlank String title,
            @NotBlank String body,
            @NotBlank String url
    ) {
    }

    public record SurlCreateResponseBody(
            String shortUrl
    ) {
    }

    @PostMapping("")
    public RsData<SurlCreateResponseBody> create(
            @Valid @RequestBody SurlCreateRequestBody requestBody
    ) {
        Surl surl = surlService.create(rq.getMember(), requestBody.url, requestBody.title, requestBody.body);

        return RsData.of(new SurlCreateResponseBody(surl.getShortUrl()));
    }
}