package com.momentary.galaxy.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.momentary.galaxy.csv.DutyCSV;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import jakarta.servlet.http.HttpServletResponse;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;

@RestController
@RequestMapping("/api/common")
public class CommonContorller {

    @GetMapping(value = "/zip")
    public ResponseEntity<byte[]> zip(HttpServletResponse response) throws Exception {

        byte[] csvByteArray = beanToCsvByNameAnnotation();
        System.out.println("csvByteArray.length = " + csvByteArray.length);
        byte[] attachByteArray = compress2(csvByteArray);
        System.out.println("attachByteArray.length = " + attachByteArray.length);

        InputStream ins = new ByteArrayInputStream(attachByteArray);
        uncompress1(ins);

        // response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;
        // filename=test.zip");
        // response.setHeader("Content-Type", "application/zip; charset=utf-8");
        // response.setHeader("Pragma", "no-cache");
        // response.setHeader("Expires", "0");
        // ByteArrayResource resource = new ByteArrayResource(attachByteArray);

        // return attachByteArray;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=export.zip");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(attachByteArray.length)
                .body(attachByteArray);
    }

    private static byte[] beanToCsvByNameAnnotation() throws Exception {
        List<DutyCSV> list = new ArrayList<DutyCSV>();
        DutyCSV vo1 = new DutyCSV("DNB00100", "0921222222", "店點不存在");
        DutyCSV vo2 = new DutyCSV("DNB00101", "0921555555", "店點不存在");
        DutyCSV vo3 = new DutyCSV("DNB00102", "0921888888", "店點不存在");
        list.add(vo1);
        list.add(vo2);
        list.add(vo3);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream, Charset.forName("UTF-8"));
        byte[] bs = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
        stream.write(bs);
        HeaderColumnNameMappingStrategy<DutyCSV> strategy = new HeaderColumnNameMappingStrategy<>();
        HashMap<String, Integer> columnOrderMap = new HashMap<>();
        columnOrderMap.put("門市代碼", 1);
        columnOrderMap.put("值星機電話", 10);
        columnOrderMap.put("匯入結果", 100);
        strategy.setColumnOrderOnWrite(Comparator.comparingInt(column -> (columnOrderMap.getOrDefault(column, 0))));
        strategy.setType(DutyCSV.class);

        StatefulBeanToCsv<DutyCSV> stateffulBeanToCsv = new StatefulBeanToCsvBuilder<DutyCSV>(
                streamWriter)
                .withMappingStrategy(strategy)
                .withApplyQuotesToAll(false)
                .build();
        stateffulBeanToCsv.write(list);
        streamWriter.flush();
        return stream.toByteArray();
    }

    private static byte[] compress2(byte[] csvByteArray) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, Charset.forName("UTF-8"))) {
            ZipParameters zipParameters = new ZipParameters();
            zipParameters.setCompressionMethod(CompressionMethod.STORE);
            zipParameters.setFileNameInZip("值星機設定匯入失敗紀錄.csv");
            zipParameters.setEntrySize(1L); // just one file in the zip
            zipOutputStream.putNextEntry(zipParameters);
            IOUtils.write(csvByteArray, zipOutputStream);
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        } catch (final IOException e) {
            System.err.println(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static void uncompress1(InputStream ins) {
        try {
            File zipFile = new File("D:\\temp\\cos\\costest.zip");
            if (zipFile.exists()) {
                zipFile.delete();
            }
            String targetPath = zipFile.getParent();
            IOUtils.copy(ins, new FileOutputStream(zipFile));
            ZipFile zip = new ZipFile(zipFile);
            zip.extractAll(targetPath);
            zipFile.delete();
            zip.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
