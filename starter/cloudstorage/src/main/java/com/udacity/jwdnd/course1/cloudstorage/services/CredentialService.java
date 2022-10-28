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

    private void encryptPassword(Credentials credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);

    }

    public List<Credentials> getAllCredentials(Integer userid){
        return this.credentialMapper.getAllCredentials(userid);
    }


    public Credentials getCredentialUrl(String url) {
        return this.credentialMapper.getCredentialUrl(url);
    }


    public int insertCredential(Credentials credentials) {
        encryptPassword(credentials);
        return this.credentialMapper.insertCredential(new Credentials(null, credentials.getUrl(), credentials.getUsername(), credentials.getPassword(), credentials.getKey(), credentials.getUserid()));
    }


    public void updateCredential(Credentials credentials) {
        encryptPassword(credentials);
        this.credentialMapper.updateCredential(credentials);
    }


    public void deleteCredential(Integer credentialId) {

        this.credentialMapper.deleteCredential(credentialId);
    }

}
