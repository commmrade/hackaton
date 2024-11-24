package org.example.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.model.Category;
import org.example.model.Event;
import org.example.repository.CategoryRepository;
import org.example.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PdfService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EventRepository eventRepository;

    private static final String URL_FILE_PATH = "https://storage.minsport.gov.ru/cms-uploads/cms/II_chast_EKP_2024_14_11_24_65c6deea36.pdf";
    private static final String LOCAL_FILE_PATH = "II_chast_EKP_2024_14_11_24_65c6deea36.pdf";


    public void parsePdf() {

        try {
            downloadFile(URL_FILE_PATH, LOCAL_FILE_PATH);



            PDDocument document = PDDocument.load(new File(LOCAL_FILE_PATH));


            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            document.close();

            eventRepository.deleteAll();
            categoryRepository.deleteAll();

            parseTasks(text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String fileUrl, String localFilePath) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        // Disable SSL checks
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            public X509Certificate[] getAcceptedIssuers() { return null; }
        }}, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        // Download the file
        try (InputStream inputStream = new URL(fileUrl).openStream();
             FileOutputStream outputStream = new FileOutputStream(localFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("File downloaded successfully: " + localFilePath);
        } catch (IOException e) {
            throw new IOException("Failed to download file: " + fileUrl, e);
        }
    }


    public void parseTasks(String pdf) throws CloneNotSupportedException, ParseException {
        String[] lines = pdf.split("\n");
        List<Event> events = new ArrayList<>();
        Event event = null;
        System.out.println("started");
        String categoryName = "";
        for (var i = 0; i < lines.length; i++) {

            String[] splLine = lines[i].split(" ");
            if (!splLine[0].matches("^\\d{16}$") && !lines[i].contains("(") && !lines[i].contains(")") && !lines[i].equals("Основной состав") && !lines[i].contains("Стр")) {

                categoryName = lines[i];

                //Add category to DB - DONE
                Category category = new Category();
                category.setName(categoryName);

                categoryRepository.save(category);
                //Category_id variable - DONE
            }


            if (splLine[0].matches("^\\d{16}$")) {

                String[] idType = lines[i].split(" ", 2);
                event = new Event();
                event.setId(Long.parseLong(idType[0]));
                event.setType(idType[1]);

                Category category = categoryRepository.findByName(categoryName);
                event.setCategoryId(category.getId());

                //Set category_id variable - DONE

                int j = i;
                j++;
                for (j = j; !lines[j].matches("^\\d{2}\\.\\d{2}\\.\\d{4}$") && j < lines.length; j++) {
                    if (j == i + 1) {
                        event.setSexAndAge(lines[j]);
                    } else {

                    }
                }

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                Date startDate = formatter.parse(lines[j].trim());
                event.setStartDate(startDate);

                j++;
                for (j = j; lines[j].matches("^\\d{2}\\.\\d{2}\\.\\d{4}$") && j < lines.length; j++) {
                    Date endDate = formatter.parse(lines[j].trim());
                    event.setEndDate(endDate);
                }
                event.setCountry(lines[j]);
                j++;

                event.setRegion(lines[j]);
                j++;
                for (j = j; !lines[j].matches("^\\d+$") && j < lines.length; j++) {
                    event.setRegion(event.getRegion() + " " + lines[j]);
                }
                event.setParticipants(Integer.parseInt(lines[j]));

                //System.out.println(event);

                i = j;
                System.out.println(event);
                events.add(event.clone());

            }




        }

        eventRepository.saveAll(events);

    }
}
