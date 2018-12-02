package my.gototinkoff.pojo;

import java.util.Objects;

public class ResultOperation<R> {

    public static final int SUCCESS_CODE = 0;

    private int code;
    private String message;
    private R result;

    public ResultOperation(int code, String message, R result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static ResultOperation error(int errorCode, String message) {
        return new ResultOperation(errorCode, message, null);
    }

    public static <R> ResultOperation ok(R result) {
        return new ResultOperation(SUCCESS_CODE, "", result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultOperation<?> that = (ResultOperation<?>) o;
        return code == that.code &&
                Objects.equals(message, that.message) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, message, result);
    }

    @Override
    public String toString() {
        return "ResultOperation{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
