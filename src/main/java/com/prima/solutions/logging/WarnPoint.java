package com.prima.solutions.logging;

import org.aspectj.lang.ProceedingJoinPoint;


public class WarnPoint implements Comparable<WarnPoint> {

    private ProceedingJoinPoint point;
    private Loggable loggable;
    private long start;

    public WarnPoint() {
    }

    public WarnPoint(ProceedingJoinPoint point, Loggable loggable, long start) {
        this.point = point;
        this.loggable = loggable;
        this.start = start;
    }

    public ProceedingJoinPoint getPoint() {
        return point;
    }

    public void setPoint(ProceedingJoinPoint point) {
        this.point = point;
    }

    public Loggable getLoggable() {
        return loggable;
    }

    public void setLoggable(Loggable loggable) {
        this.loggable = loggable;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    @Override
    public int compareTo(WarnPoint obj) {
        return Long.compare(obj.getStart(), start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarnPoint warnPoint = (WarnPoint) o;

        if (start != warnPoint.start) return false;
        if (!point.equals(warnPoint.point)) return false;
        return loggable.equals(warnPoint.loggable);
    }

    @Override
    public int hashCode() {
        int result = point.hashCode();
        result = 31 * result + loggable.hashCode();
        result = 31 * result + (int) (start ^ (start >>> 32));
        return result;
    }
}