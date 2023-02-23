package com.trangle.storage.service;

import com.trangle.storage.entity.StorageBasic;

public interface StorageService {

    void addStorage(StorageBasic storageBasic);

    void subStorage(StorageBasic storageBasic);
}
