package com.rumba.functions;

import android.app.Application;

public class Prowling extends Application {

        private int colorName;
        private String userName, userDesc;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

        public int getColorName() {
            return colorName;
        }

        public void setColorName(int colorName) {
        this.colorName = colorName;
    }
    }