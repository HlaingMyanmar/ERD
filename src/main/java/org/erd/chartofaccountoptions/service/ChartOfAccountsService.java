package org.erd.chartofaccountoptions.service;

import org.erd.chartofaccountoptions.impl.ChartOfAccountsdb;
import org.erd.chartofaccountoptions.model.ChartOfAccounts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartOfAccountsService {

    private ChartOfAccountsdb chartOfAccountsdb;

    public ChartOfAccountsService(ChartOfAccountsdb chartOfAccountsdb) {
        this.chartOfAccountsdb = chartOfAccountsdb;
    }

    public List<ChartOfAccounts> getAllData(){

        return chartOfAccountsdb.getAllData();


    }

    public boolean addChartOfAccounts(ChartOfAccounts chartOfAccounts){
        return chartOfAccountsdb.insertData(chartOfAccounts);

    }

    public boolean updateChartOfAccounts(ChartOfAccounts chartOfAccounts){
        return chartOfAccountsdb.updateData(chartOfAccounts);
    }

    public boolean deleteChartOfAccounts(ChartOfAccounts chartOfAccounts){
        return chartOfAccountsdb.deleteData(chartOfAccounts);
    }




}
