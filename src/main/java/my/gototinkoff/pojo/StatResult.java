package my.gototinkoff.pojo;

import java.util.Map;
import java.util.Objects;

public class StatResult {

    private long allRequestCount;
    private long successRequestCount;
    private Map<String, Long> successReuqestCntByCurrencies;

    public long getAllRequestCount() {
        return allRequestCount;
    }

    public void setAllRequestCount(long allRequestCount) {
        this.allRequestCount = allRequestCount;
    }

    public long getSuccessRequestCount() {
        return successRequestCount;
    }

    public void setSuccessRequestCount(long successRequestCount) {
        this.successRequestCount = successRequestCount;
    }

    public Map<String, Long> getSuccessReuqestCntByCurrencies() {
        return successReuqestCntByCurrencies;
    }

    public void setSuccessReuqestCntByCurrencies(Map<String, Long> successReuqestCntByCurrencies) {
        this.successReuqestCntByCurrencies = successReuqestCntByCurrencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatResult that = (StatResult) o;
        return allRequestCount == that.allRequestCount &&
                successRequestCount == that.successRequestCount &&
                Objects.equals(successReuqestCntByCurrencies, that.successReuqestCntByCurrencies);
    }

    @Override
    public int hashCode() {

        return Objects.hash(allRequestCount, successRequestCount, successReuqestCntByCurrencies);
    }

    @Override
    public String toString() {
        return "StatResult{" +
                "allRequestCount=" + allRequestCount +
                ", successRequestCount=" + successRequestCount +
                ", successReuqestCntByCurrencies=" + successReuqestCntByCurrencies +
                '}';
    }
}
