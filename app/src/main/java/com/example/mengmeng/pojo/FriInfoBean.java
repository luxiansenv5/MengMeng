package com.example.mengmeng.pojo;

import java.util.List;

/**
 * Created by admin on 2016/9/29.
 */
public class FriInfoBean {
        public Integer status;

        public List<FriInfo> friInfoList;

        public static class FriInfo {

                public Integer friId;
                public String friName;
                public String photoImg;
                public String address;
                public String petName;
                public String petKind;

                @Override
                public String toString() {
                        return "FriInfo{" +
                                "friId=" + friId +
                                ", friName='" + friName + '\'' +
                                ", photoImg='" + photoImg + '\'' +
                                ", address='" + address + '\'' +
                                ", petName='" + petName + '\'' +
                                ", petKind='" + petKind + '\'' +
                                '}';
                }
        }

        @Override
        public String toString() {
                return "FriInfoBean{" +
                        "status=" + status +
                        ", friInfoList=" + friInfoList +
                        '}';
        }
}
