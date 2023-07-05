package com.github.CCweixiao.hbase.sdk.shell;

/**
 * @author leojie 2023/7/4 20:50
 */
public final class Result {
    private final boolean success;
    private final String result;

    public Result(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public static Result of(boolean success, String message) {
        return new Result(success, message);
    }

    public static Result ok(String message) {
        return Result.of(true, message);
    }

    public static Result failed(String message) {
        return Result.of(false, message);
    }

    public static Result ok() {
        return Result.of(true, "ok");
    }

    public static Result failed() {
        return Result.of(false, "error");
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", result='" + result + '\'' +
                '}';
    }
}
