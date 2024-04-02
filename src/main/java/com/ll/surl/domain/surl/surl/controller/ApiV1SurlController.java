package com.ll.surl.domain.surl.surl.controller;

import com.ll.surl.domain.permission.permission.service.PermissionService;
import com.ll.surl.domain.surl.surl.entity.Surl;
import com.ll.surl.domain.surl.surl.service.SurlService;
import com.ll.surl.global.exception.GlobalException;
import com.ll.surl.global.rq.Rq;
import com.ll.surl.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
public class ApiV1SurlController {
    private final SurlService surlService;
    private final Rq rq;
    private final PermissionService permissionService;


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


    public record SurlModifyRequestBody(
            @NotBlank String title,
            @NotBlank String body
    ) {
    }

    public record SurlModifyResponseBody(
            String shortUrl
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData<SurlModifyResponseBody> modify(
            @PathVariable long id,
            @Valid @RequestBody SurlModifyRequestBody requestBody
    ) {
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        permissionService.check(rq.getMember(), surl, PermissionService.Action.MODIFY);

        surlService.modify(surl, requestBody.title, requestBody.body);

        return RsData.of(new SurlModifyResponseBody(surl.getShortUrl()));
    }
}