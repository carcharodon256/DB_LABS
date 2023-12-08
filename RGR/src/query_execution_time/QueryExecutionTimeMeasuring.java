package query_execution_time;

public class QueryExecutionTimeMeasuring {
    public static long queryExecutionTime;

    // query execution time counting
    public static long measureQueryExecutionTime(long startTime, long endTime) {
        queryExecutionTime = endTime - startTime;
        return queryExecutionTime;
    }
}
