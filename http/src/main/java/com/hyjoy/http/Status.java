/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyjoy.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by hyjoy on 2018/4/19.
 */
public class Status {

    private final static int LOADING = 0;
    private final static int ERROR = -1;
    private final static int SUCCESS = 1;
    private final static int NULL = -2;

    public boolean isLoading() {
        return status == LOADING;
    }

    public boolean isError() {
        return status == ERROR;
    }

    public boolean isSuccess() {
        return status == SUCCESS;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @NonNull
    public int getStatus() {
        return status;
    }

    @NonNull
    private final int status;

    @Nullable
    private final String message;

    private Status(@NonNull int status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static Status success() {
        return new Status(SUCCESS, null);
    }

    public static Status error(String msg) {
        return new Status(ERROR, msg);
    }

    public static Status loading() {
        return new Status(LOADING, null);
    }

    public static Status nul() {
        return new Status(NULL, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Status resource = (Status) o;

        if (status != resource.status) {
            return false;
        }
        return message != null ? message.equals(resource.message) : resource.message == null;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
