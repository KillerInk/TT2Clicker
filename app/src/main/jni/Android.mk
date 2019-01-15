LOCAL_PATH := $(call my-dir)

LOCAL_ARM_MODE := arm

include $(CLEAR_VARS)
LOCAL_MODULE    := touch
LOCAL_SRC_FILES := touch.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)