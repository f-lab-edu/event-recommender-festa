package com.festa.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.festa.common.ReplicationRoutingConstants.SLAVE;
import static com.festa.common.ReplicationRoutingConstants.MASTER;

/*
    Transactional이 readOnly 로 설정되어 있다면 Slave에서 읽어오도록 설정을 하기 위한 클래스
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ? SLAVE : MASTER;
    }
}
