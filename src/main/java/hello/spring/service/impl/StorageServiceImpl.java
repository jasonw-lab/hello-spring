package hello.spring.service.impl;

import hello.spring.service.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageServiceImpl implements IStorageService {

    @Override
    public Object findAccountByUsername(String username) {
        return "ImoocEngineeringGuide";
    }
}
