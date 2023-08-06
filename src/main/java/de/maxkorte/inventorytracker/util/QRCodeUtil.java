package de.maxkorte.inventorytracker.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class QRCodeUtil {
    private QRCodeUtil() {

    }

    public static void generateQRCode(String content, OutputStream outputStream) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8),
                BarcodeFormat.QR_CODE,
                200,
                200);
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
    }
}
