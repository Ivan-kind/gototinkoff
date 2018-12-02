package my.gototinkoff.data.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "REQUEST_COUNTER")
public class RequestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="CURRENCY_ID")
    private Currency currency;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "IS_SUCCESS")
    private boolean isSuccess;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "ResultOperation{" +
                "id=" + id +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
