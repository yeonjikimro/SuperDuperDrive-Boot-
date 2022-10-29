package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }



    public List<Credentials> getAllCredentials(Integer userid){
        return this.credentialMapper.getAllCredentials(userid);
    }


    public Credentials getCredentialUrl(String url) {
        return this.credentialMapper.getCredentialUrl(url);
    }


    public void insertOrUpdateCredential(Credentials credentials) {


        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodeKey);
        credentials.setKey(encodeKey);
        credentials.setPassword(encryptedPassword);

        if (credentials.getCredentialId() == null) {
            credentialMapper.insertCredential(credentials);
        } else {
            credentialMapper.updateCredential(credentials);
        }

    }





    public void deleteCredential(Integer credentialId) {

        this.credentialMapper.deleteCredential(credentialId);
    }

}
