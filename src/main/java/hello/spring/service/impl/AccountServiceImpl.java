package hello.spring.service.impl;

import hello.spring.service.IAccountService;
import hello.spring.service.IStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    /** 注入存储服务 bean */
    private final IStorageService storageService;

    @Override
    public void queryAccountInfo(String username) {
        storageService.findAccountByUsername(username);
    }
}
