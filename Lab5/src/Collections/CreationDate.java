package Collections;

public class CreationDate {

    private class DateTime {
        private class Date {
            private long year;
            private long month;
            private long day;

            public Date(long year, long month, long day) {
                this.year = year;
                this.month = month;
                this.day = day;
            }
        }
        private class Time {
            private long hour;
            private long minute;
            private long second;
            private long nano;

            public Time(long hour, long minute, long second, long nano) {
                this.hour = hour;
                this.minute = minute;
                this.second = second;
                this.nano = nano;
            }
        }
    }
    private class OffSet {
        private long totalSeconds;

        public OffSet(long totalSeconds) {
            this.totalSeconds = totalSeconds;
        }
    }
    private class Zone{
        private String Id;

        public Zone(String id) {
            Id = id;
        }
    }
}
