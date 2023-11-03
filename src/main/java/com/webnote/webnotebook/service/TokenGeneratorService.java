package com.webnote.webnotebook.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenGeneratorService {
    private final Map<String, String> tokenToNoteIdMap = new HashMap<>();

    public String generateToken(String noteId) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[16];
        secureRandom.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        tokenToNoteIdMap.put(token, noteId);
        return token;
    }

    public String getNoteId(String token) {
        return tokenToNoteIdMap.get(token);
    }
}