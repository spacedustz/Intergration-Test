package com.converter.rtsp;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.tools.Logger;
import org.bytedeco.javacv.FFmpegLogCallback;

import static org.bytedeco.ffmpeg.global.avutil.*;
import static org.bytedeco.ffmpeg.global.avutil.AV_LOG_TRACE;

public class CustomFFmpegLogCallBack extends FFmpegLogCallback {
    private static final Logger logger = Logger.create(FFmpegLogCallback.class);

    @Override
    public void call(int level, BytePointer message) {
        switch (level) {
            case AV_LOG_PANIC:
            case AV_LOG_FATAL:
            case AV_LOG_ERROR:
                logger.error("FFmpeg [ Error ] Log (level " + level + "): " + message.toString());
                break;
            case AV_LOG_WARNING:
                logger.warn("FFmpeg [ Warn ] Log (level " + level + "): " + message.toString());
                break;
            case AV_LOG_INFO:
                logger.info("FFmpeg [ Info ] Log (level " + level + "): " + message.toString());
                break;
            case AV_LOG_VERBOSE:
            case AV_LOG_DEBUG:
            case AV_LOG_TRACE:
                logger.debug("FFmpeg [ Denug ] Log (level " + level + "): " + message.toString());
                break;
            default:
                assert false;
        }
    }
}
