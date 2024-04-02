package com.ll.surl.domain.permission.permission.service;

import com.ll.surl.domain.member.member.entity.Member;
import com.ll.surl.domain.surl.surl.entity.Surl;
import com.ll.surl.global.exception.GlobalException;
import com.ll.surl.global.jap.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
    public enum Action {
        READ,
        MODIFY,
        DELETE
    }

    public boolean can(Member actor, BaseEntity target, Action action) {
        if (actor == null) return false;
        if (target == null) return false;

        return switch (target) {
            case Surl surl -> switch (action) {
                case READ -> true;
                case DELETE, MODIFY -> surl.getAuthor().equals(actor);
            };
            default -> false;
        };
    }

    public void check(Member member, Surl surl, Action action) {
        if (!can(member, surl, action)) {
            throw new GlobalException("403-1", "권한이 없습니다.");
        }
    }
}