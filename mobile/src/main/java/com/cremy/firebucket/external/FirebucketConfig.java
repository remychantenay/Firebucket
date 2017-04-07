package com.cremy.firebucket.external;

/**
 * This class will enumerate the different config parameters for the app
 */
public enum FirebucketConfig {
        MAINTENANCE("is_maintenance", "no");

        private String key;
        private String defaultValue;

        FirebucketConfig(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }