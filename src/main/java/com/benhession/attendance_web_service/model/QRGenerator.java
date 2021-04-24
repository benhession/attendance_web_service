package com.benhession.attendance_web_service.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;

public class QRGenerator {
    public static BufferedImage generateQRCodeImage(String QRCodeText) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(QRCodeText, BarcodeFormat.QR_CODE, 800, 800);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
