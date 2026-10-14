/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import com.sun.net.httpserver.HttpServer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Handles the web server for the Paint program.
 *
 * @author tanya
 */
public class WebServer {

    // stores the server while it is running
    private static HttpServer server;

    // stores images chosen by the user for the website
    private static final ArrayList<byte[]> selectedImages =
            new ArrayList<>();


    /**
     * Adds the image in the current tab to the web page.
     */
    public static void selectCurrentImage() {

        // make sure a drawing area is open
        if (GuiImage.drawingArea == null) {
            return;
        }

        // get the complete image from the current tab
        WritableImage image =
                GuiImage.drawingArea.getEditedImage();

        BufferedImage bufferedImage =
                SwingFXUtils.fromFXImage(
                        image,
                        null
                );

        try {

            // turn the image into PNG data
            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            ImageIO.write(
                    bufferedImage,
                    "png",
                    output
            );

            // save image data for the website
            selectedImages.add(
                    output.toByteArray()
            );

            System.out.println(
                    "Image selected for web server"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error selecting image for web server"
            );
        }
    }


    /**
     * Starts the web server.
     */
    public static void startServer() {

        // do not start another server if one is already running
        if (server != null) {
            return;
        }

        try {

            // create server using port 8000
            server = HttpServer.create(
                    new InetSocketAddress(8000),
                    0
            );

            // main web page
            server.createContext("/", exchange -> {

                StringBuilder page =
                        new StringBuilder();

                page.append(
                        """
                        <html>
                        <head>
                            <title>Paint Web Server</title>
                        </head>

                        <body>
                            <h1>Paint Web Server</h1>
                        """
                );

                // show message if no images were selected
                if (selectedImages.isEmpty()) {

                    page.append(
                            "<p>No images are currently selected.</p>"
                    );

                } else {

                    // add each selected image to the page
                    for (int i = 0;
                            i < selectedImages.size();
                            i++) {

                        page.append(
                                "<img src=\"/image/"
                                + i
                                + "\" style=\"max-width:600px;\"><br><br>"
                        );
                    }
                }

                page.append(
                        """
                        </body>
                        </html>
                        """
                );

                byte[] response =
                        page.toString().getBytes(
                                StandardCharsets.UTF_8
                        );

                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "text/html; charset=UTF-8"
                );

                // 200 - request worked successfully 
                exchange.sendResponseHeaders(
                        200,
                        response.length
                );

                try (OutputStream output =
                        exchange.getResponseBody()) {

                    output.write(response);
                }
            });


            // sends selected images to the browser
            server.createContext("/image", exchange -> {

                try {

                    // get image number from the address
                    String path =
                            exchange.getRequestURI().getPath();

                    String number =
                            path.substring(
                                    path.lastIndexOf("/") + 1
                            );

                    int imageNumber =
                            Integer.parseInt(number);

                    // make sure image exists
                    if (imageNumber < 0
                            || imageNumber >= selectedImages.size()) {

                        // 404 - requested image was not found
                        exchange.sendResponseHeaders(
                                404,
                                -1
                        );

                        return;
                    }

                    byte[] imageData =
                            selectedImages.get(
                                    imageNumber
                            );

                    // tell browser this is a PNG image
                    exchange.getResponseHeaders().set(
                            "Content-Type",
                            "image/png"
                    );

                    // 200 - image was found and can be sent to browser 
                    exchange.sendResponseHeaders(
                            200,
                            imageData.length
                    );

                    try (OutputStream output =
                            exchange.getResponseBody()) {

                        output.write(imageData);
                    }

                } catch (Exception e) {

                    exchange.sendResponseHeaders(
                            404,
                            -1
                    );
                }
            });


            // start server
            server.start();

            System.out.println(
                    "Web server started on port 8000"
            );

        } catch (IOException e) {

            System.out.println(
                    "Error starting web server"
            );
        }
    }


    /**
     * Stops the web server.
     */
    public static void stopServer() {

        if (server != null) {

            server.stop(0);

            server = null;

            System.out.println(
                    "Web server stopped"
            );
        }
    }
}