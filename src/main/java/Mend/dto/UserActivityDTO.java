package Mend.dto;

    public class UserActivityDTO {

        private int userId;
        private String username;
        private long scanCount;

        public UserActivityDTO(int userId, String username, long scanCount) {
            this.userId = userId;
            this.username = username;
            this.scanCount = scanCount;
        }

        public String getUserName() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public long getScanCount() {
            return scanCount;
        }

        public void setScanCount(long scanCount) {
            this.scanCount = scanCount;
        }

        public int getUserId(){
            return userId;
        }

        public void setUserId(int userId){
            this.userId=userId;
        }

    }

