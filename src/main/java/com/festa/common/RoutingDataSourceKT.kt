package com.festa.common

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

import com.festa.common.ReplicationRoutingConstants.SLAVE
import com.festa.common.ReplicationRoutingConstants.MASTER

class RoutingDataSourceKT : AbstractRoutingDataSource() {

    override protected fun determineCurrentLookupKey(): Any {
        return if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            SLAVE
        } else {
            MASTER
        }
    }
}