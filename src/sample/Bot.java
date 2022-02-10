package sample;



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private static String text="";
    public static ArrayList<String> mesages = new ArrayList<>();

    private static void saveFile(){


        Controller.getMas();
    }
    private final String BOTUSER = "Rabota_Nata_bot";
    private final String BOTTOKEN = "";


    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            String message = update.getMessage().getText();
           // message=update.getMessage().getPhoto().toString();
            System.out.println(message);
            sendKeyBoardMessage(update.getMessage().getChatId());
            if (message.equals("Save File")) {
                saveFile();
            } else {
                mesages.add(message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return BOTUSER;
    }

    @Override
    public String getBotToken() {
        return BOTTOKEN;
    }

    public void sendKeyBoardMessage(long chatId) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Save File");
        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        try {
            execute( new SendMessage().setChatId(chatId).setText("Принял").
                    setReplyMarkup(replyKeyboardMarkup).enableMarkdown(true));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}