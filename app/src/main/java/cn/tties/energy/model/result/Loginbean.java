package cn.tties.energy.model.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by li on 2018/3/22
 * description：
 * author：guojlli
 */

public class Loginbean implements Serializable {


    /**
     * bookInfo : [{"BOOK_ID":1105,"BOOK_TYPE":1},{"BOOK_ID":1106,"BOOK_TYPE":1},{"BOOK_ID":"policy","BOOK_TYPE":2}]
     * ledgerId : 1486535776800
     * accountId : 1486535427417
     * ledgerName : 南洋印染
     * freetime : 16-18
     * isShield : 1
     * errorCode : 0
     * loginFlag : true
     * firstFlag : 0
     */

    private long ledgerId;
    private long accountId;
    private String ledgerName;
    private String freetime;
    private int isShield;
    private int errorCode;
    private boolean loginFlag;
    private int firstFlag;
    private List<BookInfo> bookInfo=new ArrayList<>();

    public long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getFreetime() {
        return freetime;
    }

    public void setFreetime(String freetime) {
        this.freetime = freetime;
    }

    public int getIsShield() {
        return isShield;
    }

    public void setIsShield(int isShield) {
        this.isShield = isShield;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(boolean loginFlag) {
        this.loginFlag = loginFlag;
    }

    public int getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(int firstFlag) {
        this.firstFlag = firstFlag;
    }

    public List<BookInfo> getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(List<BookInfo> bookInfo) {
        this.bookInfo = bookInfo;
    }

    public class BookInfo  implements Serializable {
        Integer book_id;

        Integer book_type;

        public Integer getBook_id() {
            return book_id;
        }

        public void setBook_id(Integer book_id) {
            this.book_id = book_id;
        }

        public Integer getBook_type() {
            return book_type;
        }

        public void setBook_type(Integer book_type) {
            this.book_type = book_type;
        }
    }
}
