package com.converter.rtsp;

import org.bytedeco.ffmpeg.avcodec.AVCodecContext;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class RtspConverter {

    private static final String RTSP_URL = "rtsp://admin:dains1001!@192.168.0.61:554/Streamings/Channels/101";
    private static final String RTSP_URL2 = "rtsp://210.99.70.120:1935/live/cctv004.stream";
    String outputFolder = "E:/Output-Data/Streaming/"; // hls 폴더로 변경
    String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
    String outputFileName = "stream_" + timestamp + ".m3u8"; // 파일 이름에 타임스탬프 포함
    String outputFilePath = outputFolder + outputFileName;

    public String convertRtspToHls() {

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(RTSP_URL);

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
                new File(outputFilePath),
                grabber.getImageWidth(),
                grabber.getImageHeight(),
                grabber.getVideoBitrate()
        );

        try {
            grabber.start();
            System.out.println("★ Grabber Start ★");

            System.out.println("Grabber Image Width = " + grabber.getImageWidth()); //
            System.out.println("Grabber Image Height = " + grabber.getImageHeight()); //
            System.out.println("Grabber Video Bitrate = " + grabber.getVideoBitrate()); //
            FFmpegLogCallback.set();

            System.out.println("========== Start Process ==========");

            recorder.setFormat("hls");
            System.out.println("[1] : Set Format = " + recorder.getFormat()); // hls

            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            System.out.println("[2] : Set Video Codec = " + recorder.getVideoCodec()); // 27

            recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
            System.out.println("[3] : Set Audio Codec = " + recorder.getAudioCodec()); // 86018

            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            System.out.println("[4] : Set Pixel Format = " + recorder.getPixelFormat()); // 0

            recorder.setFrameRate(30);
            System.out.println("[5] : Set Frame Rate = " + recorder.getFrameRate()); // 30.0

            recorder.setOption("hls_time", "2");
            System.out.println("[6] : Set HLS_Time = " + recorder.getOption("hls_time")); // 2

            recorder.setOption("hls_list_size", "10");
            System.out.println("[7] : Set HLS_List_Size = " + recorder.getOption("hls_list_size")); // 10

            recorder.setOption("hls_flags", "delete_segments");
            System.out.println("[8] : Set HLS_Flags = " + recorder.getOption("hls_flags")); // delete_segments

            recorder.setOption("start_number", "1");
            System.out.println("[9] : Set Start Number = " + recorder.getOption("start_number")); // 1

            recorder.setOption("sws_flags", "full_chroma_int");
            System.out.println("[10] : Set SWS_Flags = " + recorder.getOption("sws_flags")); // full_chroma_int

            recorder.setOption("color_range", "limited");  // 또는 "limited"
            System.out.println("[11] : Set Color_Range = " + recorder.getOption("color_range")); // limited

            recorder.setVideoBitrate(5000);
            System.out.println("[12] : Set Video Bitrate = " + recorder.getVideoBitrate()); // 0

            recorder.setImageWidth(grabber.getImageWidth());
            recorder.setImageHeight(grabber.getImageHeight());
            recorder.setAudioChannels(grabber.getAudioChannels());

            recorder.start();
            System.out.println("★ Recorder Start★ ");

            Frame frame;
            int frameNum = 0;

            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
                frameNum++;
                System.out.println("Frame Number = " + frameNum);
                recorder.close();
                recorder.start();
            }
        } catch (FrameRecorder.Exception | FFmpegFrameGrabber.Exception e) {
            e.printStackTrace();
        } finally {
            try {
                grabber.stop();
                System.out.println("========== Grabber Stopped ==========");
                recorder.stop();
                System.out.println("========== Recorder Stopped ==========");
                recorder.release();
                System.out.println("========== Recorder Released ==========");

                updatePlaylist(outputFilePath);
            } catch (FrameRecorder.Exception | FrameGrabber.Exception e) {
                e.printStackTrace();
            }
        }

        return outputFilePath;
    }

    private void updatePlaylist(String playlistPath) {
        try {
            File playlistFile = new File(playlistPath);
            PrintWriter writer = new PrintWriter(new FileWriter(playlistFile, true));
            File[] segmentFiles = playlistFile.getParentFile().listFiles(
                    (dir, name) -> name.endsWith(".ts")
            );

            if (segmentFiles != null) {
                for (File segment : segmentFiles) {
                    writer.println("E:/Output-Data/Streaming/" + segment.getName()); // Update with the correct path
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Async // 비동기 콜백 함수
    public CompletableFuture<String> convertOnLocal() {
        String command = "ffmpeg -rtsp_transport tcp -i rtsp://210.99.70.120:1935/live/cctv004.stream -c:v copy -c:a copy -hls_time 2 -hls_list_size 10 -hls_flags delete_segments -start_number 1 -analyzeduration 10000000 -probesize 10000000 C:/Users/Administrator/Desktop/output2/output.m3u8";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.redirectErrorStream(true);

        processBuilder.directory(new File("E:\\Tools\\ffmpeg-2023-08-10\\bin")); // FFmpeg 실행에 필요한 파일이 있는 디렉토리 경로로 설정
        processBuilder.command(
//                "C:/Windows/system32/cmd.exe", "/c",
                "E:\\Tools\\ffmpeg-2023-08-10\\bin",
                "ffmpeg",
                "-rtsp_transport", "tcp",
                "-i", RTSP_URL,
                "-c:v", "copy",
                "-c:a", "copy",
                "-hls_time", "2",
                "-hls_list_size", "10",
                "-hls_flags", "delete_segments",
                "-start_number", "1",
                "-analyzeduration", "10000000",
                "-probesize", "10000000",
                outputFolder + outputFileName
        );

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            if (exitCode != 0) {
                throw new RuntimeException("FFmpeg process failed with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error while converting RTSP to HLS", e);
        }

        return CompletableFuture.completedFuture(outputFilePath);
    }
}
