package cn.tties.energy.model.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/3/28
 * description：
 * author：guojlli
 */

public class AllElectricitybean {


    /**
     * ledgerId : 1486535776800
     * ledgerName : 南洋印染
     * meterList : [{"meterName":"南洋1车间","meterId":1486536312217},{"meterName":"南洋2车间","meterId":1486536454743},{"meterName":"南洋3车间","meterId":1486536361594},{"meterName":"南洋4车间","meterId":1486536481282},{"meterName":"南洋5车间","meterId":1486536395204},{"meterName":"南洋6车间","meterId":1486536421895}]
     */

    private long ledgerId;
    private String ledgerName;
    private List<MeterListBean> meterList=new ArrayList<>();

    public long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public List<MeterListBean> getMeterList() {
        return meterList;
    }

    public void setMeterList(List<MeterListBean> meterList) {
        this.meterList = meterList;
    }

    public static class MeterListBean {
        /**
         * meterName : 南洋1车间
         * meterId : 1486536312217
         */

        private String meterName;
        private long meterId;

        public String getMeterName() {
            return meterName;
        }

        public void setMeterName(String meterName) {
            this.meterName = meterName;
        }

        public long getMeterId() {
            return meterId;
        }

        public void setMeterId(long meterId) {
            this.meterId = meterId;
        }
    }
}
