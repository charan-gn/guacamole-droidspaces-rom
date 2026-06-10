LOCAL_PATH := $(call my-dir)

# PickleHik3 — embedded Termux-based launcher with X11 desktop
include $(CLEAR_VARS)
LOCAL_MODULE := PickleHik3
LOCAL_SRC_FILES := PickleHik3.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true
LOCAL_DEX_PREOPT := false
include $(BUILD_PREBUILT)

# DroidSpaces — container management system app
include $(CLEAR_VARS)
LOCAL_MODULE := DroidSpaces
LOCAL_SRC_FILES := DroidSpaces.apk
LOCAL_MODULE_CLASS := APPS
LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := PRESIGNED
LOCAL_PRIVILEGED_MODULE := true
LOCAL_DEX_PREOPT := false
include $(BUILD_PREBUILT)
