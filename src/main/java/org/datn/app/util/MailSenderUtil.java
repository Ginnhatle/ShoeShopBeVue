package org.datn.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {
    @Value("${frontend.urlRedirect}")
    private String urlRedirect;
    public  String sendMail(String subject,String code,String url) {
        StringBuilder stringBuilder = new StringBuilder();
        String emailHTML = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Chào mừng đến với Aboo Shop</title>\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Font */\n" +
                "        @import url('https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap');\n" +
                "        body {\n" +
                "            font-family: 'Roboto', sans-serif;\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "\n" +
                "        /* Container */\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 30px;\n" +
                "            background-color: #f5f5f5;\n" +
                "            border-radius: 10px;\n" +
                "        }\n" +
                "\n" +
                "        /* Header */\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .header h1 {\n" +
                "            font-size: 28px;\n" +
                "            font-weight: 700;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        /* Code */\n" +
                "        .code {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        .code p {\n" +
                "            font-size: 18px;\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .code .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #007bff;\n" +
                "            color: #fff;\n" +
                "            border-radius: 5px;\n" +
                "            text-decoration: none;\n" +
                "            font-size: 16px;\n" +
                "            transition: all 0.3s ease;\n" +
                "        }\n" +
                "\n" +
                "        .code .button:hover {\n" +
                "            background-color: #0062cc;\n" +
                "        }\n" +
                "\n" +
                "        /* Footer */\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        .footer p {\n" +
                "            margin-bottom: 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>"+ subject +"</h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"code\">\n" +
                "            <p>Chào bạn,</p>\n" +
                "            <p>Bạn đang lấy lại mật khẩu tại Aboo Shop. Mã xác nhận của bạn là:</p>\n" +
                "            <p><strong>"+ code +"</strong></p>\n" +
                "            <p>Vui lòng nhập mã xác nhận này vào trang để hoàn tất quá trình.</p>\n" +
                "            <a href=\""+ urlRedirect.concat(url) +"\" class=\"button\">Đổi mật khẩu</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Nếu bạn không lấy lại mật khẩu tại Aboo Shop, vui lòng bỏ qua email này.</p>\n" +
                "            <p>Trân trọng,</p>\n" +
                "            <p>Aboo Shop Team</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        stringBuilder.append(emailHTML);
        return stringBuilder.toString();
    }
}
