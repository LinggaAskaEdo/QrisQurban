package com.qris.qurban.util;

import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qris.qurban.model.DataQR;
import com.qris.qurban.model.exception.InternalServerErrorException;
import com.qris.qurban.preference.ConfigPreference;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QrisQurbanUtil
{
    private static final Logger logger = LogManager.getLogger();

    List<String> sortList = Stream.of("ASC", "DESC").collect(Collectors.toList());

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private final ConfigPreference configPreference;

    @Autowired
    public QrisQurbanUtil(ConfigPreference configPreference)
    {
        this.configPreference = configPreference;
    }

    public String saveCloudStorage(String imageBase64)
    {
        String imageName;

        try
        {
            imageName = UUID.randomUUID().toString();

            Storage storage = StorageOptions.newBuilder().setProjectId(configPreference.projectId).build().getService();
            BlobId blobId = BlobId.of(configPreference.bucketName, imageName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
            storage.create(blobInfo, Base64.decodeBase64(imageBase64));
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            logger.info("Image already uploaded to bucket {} as {}", configPreference.bucketName, imageName);
        }
        catch (Exception e)
        {
            logger.error("Error when upload: ", e);

            throw new InternalServerErrorException(e.getMessage());
        }

        return imageName;
    }

    public String generateRandomPassword()
    {
        StringBuilder sb = new StringBuilder(8);

        String alphaNum = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for(int i = 0; i < 8; i++)
            sb.append(alphaNum.charAt(new SecureRandom().nextInt(alphaNum.length())));

        return sb.toString();
    }

    public String generateQR(String recipientName, String recipientEmail, String recipientPhone, String recipientYear)
    {
        String result;
        String content = new Gson().toJson(new DataQR(recipientName, recipientEmail, recipientPhone, recipientYear));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            BufferedImage logoImage = ImageIO.read(new URL(configPreference.masjidLogoUrl));
            BufferedImage image = getBufferedImage(content, logoImage);

            // Write combined image as JPEG to OutputStream
            ImageIO.write(image, "jpeg", baos);

            // Convert OutputStream to ByteArray
            byte[] bytes = baos.toByteArray();

            // Reformat with Base64
            result =  Base64.encodeBase64String(bytes);
        }
        catch (Exception e)
        {
            logger.error("Error when generateQR2: ", e);

            throw new InternalServerErrorException(e.getMessage());
        }

        return result;
    }

    private BufferedImage getBufferedImage(String content, BufferedImage logoImage)
    {
        BufferedImage image;

        try
        {
            // Set the coded character set
            EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);

            //Set encoding
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            //Set the highest fault tolerance rate
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 1);

            // 1. Generate QR code
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 500, 500, hints);

            // 2. Get the width and height of the QR code
            int codeWidth = bitMatrix.getWidth();
            int codeHeight = bitMatrix.getHeight();

            // 3. Put the QR code into the buffer stream
            image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);

            for (int i = 0; i < codeWidth; i++)
            {
                for (int j = 0; j < codeHeight; j++)
                {
                    // 4. Loop the QR code content into the picture
                    image.setRGB(i, j, bitMatrix.get(i, j) ? BLACK : WHITE);
                }
            }

            Graphics2D graphics = image.createGraphics();
            int widthLogo = Math.min(logoImage.getWidth(null), image.getWidth() * 2 / 10);
            int heightLogo = Math.min(logoImage.getHeight(null), image.getHeight() * 2 / 10);
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;

            // start drawing pictures
            graphics.drawImage(logoImage, x, y, widthLogo, heightLogo, null);
            graphics.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);

            //Border width
            graphics.setStroke(new BasicStroke(2));

            //Border color
            graphics.setColor(Color.WHITE);
            graphics.drawRect(x, y, widthLogo, heightLogo);
            graphics.dispose();
            logoImage.flush();
            image.flush();
        }
        catch (Exception e)
        {
            logger.error("Error when getBufferedImage: ", e);

            throw new InternalServerErrorException(e.getMessage());
        }

        return image;
    }

    public boolean urlValidator(String url)
    {
        try
        {
            new URL(url).toURI();

            return true;
        }
        catch (URISyntaxException | MalformedURLException exception)
        {
            return false;
        }
    }

    public boolean checkSortOrderType(String data)
    {
        return sortList.stream().anyMatch(x -> x.equalsIgnoreCase(data));
    }
}