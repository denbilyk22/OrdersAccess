package com.project.orderaccess.service.clientbalance;

import com.project.orderaccess.feignclient.ClientBalanceServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientBalanceServiceImpl implements ClientBalanceService {

    private final ClientBalanceServiceIntegration clientBalanceServiceIntegration;

    @Override
    public void refreshProfitForAllClients() {
        clientBalanceServiceIntegration.refreshProfitForAllClients();
    }
}
