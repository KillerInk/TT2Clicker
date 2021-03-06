//
// Created by troop on 15.01.2019.
//

#ifndef CLICKERBOT_MYINPUT_H
#define CLICKERBOT_MYINPUT_H


#include <cstdint>
#include <android/log.h>
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>


#define  LOG_TAG    "clickerbot.MyInput"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

class MyInput {
public:

    MyInput(){}
    struct uinput_event {
        struct timeval time;
        unsigned short type;
        unsigned short code;
        unsigned int value;
    };
    int file;
    void openFile(char * file)
    {
        this->file = open(file, O_RDWR);
        LOGD("Open %s", file);
    }

    void closeFile()
    {
        close(file);
    }

    void sendEvent(uint16_t type, uint16_t code, int32_t value)
    {
        struct uinput_event event;
        int len;

        //LOGD("sendEvent %i %i %i", type, code,value);

        memset(&event, 0, sizeof(event));
        gettimeofday (&event.time, NULL);
        event.type = type;
        event.code = code;
        event.value = value;

        len = write(file, &event, sizeof(event));
    }


};


#endif //CLICKERBOT_MYINPUT_H
