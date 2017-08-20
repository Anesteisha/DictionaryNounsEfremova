package com.example;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import java.io.IOException;



public class RussianNouns {
    public static final String TOLKOVYJ_SLOVAR_EFREMOVOJ= "C:\\Users\\ANESTEISHA\\толковый словарь Ефремовой.txt";
    public static final String SPISOK_SUSHESTVITELJNIH_PO_EFREMOVOJ = "C:\\Users\\ANESTEISHA\\список существительных по ефремовой.txt";

    public static final String SLOVAR_SUSHCHESTVITILNYH_S_TOLKOVANIEM = "C:\\Users\\ANESTEISHA\\1.словарь русских существительных с толкованием.txt";
    public static final String SPISOK_NUM= "C:\\Users\\ANESTEISHA\\2.список_цифр.txt";
    public static final String SPISOK_SLOW= "C:\\Users\\ANESTEISHA\\3.список_слов.txt";
    public static final String SPISOK_SLOW_NUMEROVANNYJ= "C:\\Users\\ANESTEISHA\\4.список_слов_нумерованный.txt";
    public static final String SPISOK_TOLKOVANIJ= "C:\\Users\\ANESTEISHA\\5.список_толкований.txt";
    public static final String SPISOK_TOLKOVANIJ_NUMEROVANNYJ= "C:\\Users\\ANESTEISHA\\6.список_толкований_нумерованный.txt";

    public static final String SPISOK_SLOW_SM= "C:\\Users\\ANESTEISHA\\7.список невошедших слов.txt";
    public static final String SPISOK_SLOW_SM_PLUS= "C:\\Users\\ANESTEISHA\\8.список невошедших слов_plus.txt";



    public static void main(String[] args) throws Exception{
        FileWriter writer1 = new FileWriter(SLOVAR_SUSHCHESTVITILNYH_S_TOLKOVANIEM, true); // true - дозапись, false - перезапись
        FileWriter writer2 = new FileWriter(SPISOK_NUM, true);
        FileWriter writer3 = new FileWriter(SPISOK_SLOW, true);
        FileWriter writer4 = new FileWriter(SPISOK_SLOW_NUMEROVANNYJ, true);
        FileWriter writer5 = new FileWriter(SPISOK_TOLKOVANIJ, true);
        FileWriter writer6 = new FileWriter(SPISOK_TOLKOVANIJ_NUMEROVANNYJ, true);
        FileWriter writer7 = new FileWriter(SPISOK_SLOW_SM, true);
        FileWriter writer8 = new FileWriter(SPISOK_SLOW_SM_PLUS, true);


        int positionNum = 1;
        try{
            while (nonEmptyFile(SPISOK_SUSHESTVITELJNIH_PO_EFREMOVOJ)) {

                    String word = getFirstWordFromList(SPISOK_SUSHESTVITELJNIH_PO_EFREMOVOJ); //берет слово в списке слов
                    String definition = getWordDefinitionFromEfremova(word, TOLKOVYJ_SLOVAR_EFREMOVOJ); //записывает толкование со словаря Ефремовой

                Pattern p = Pattern.compile("(\\Qсм.\\E)|(\\QТо же, что\\E)|(\\QОтвлеч. сущ.\\E)|(\\QЖенск. к сущ.\\E)|(\\QПроцесс действия по \\E)|(\\QДействие по \\E)|(\\QДейстие по \\E)|" +
                        "(\\QДействия по \\E)|(\\QСостояние по \\E)|(\\QСССР\\E)|(\\QРоссийском государстве до\\E)|(\\QОпределение не найдено.\\E)");
                Matcher m = p.matcher(definition);

                if (m.find()) {

                        writerNewListWords(word, writer7);
                        writerNewListWords(word + " - " + definition, writer8);
                        System.out.println(word + " " + definition);
                        deleteFirstLineFromList(positionNum,SPISOK_SUSHESTVITELJNIH_PO_EFREMOVOJ);
                        continue;
                    }
                m.reset();

                    writerNewListWords(word, writer3);
                    writerNewListWords(positionNum + ". " + word, writer4);
                    writerNewListWords(positionNum + ". ", writer2);
                    writerNewListWords(positionNum + ". " + definition, writer6);
                    writerNewListWords(definition, writer5);

                    writerNewFileVocabulary(word, definition, writer1);

                positionNum = deleteFirstLineFromList(positionNum,SPISOK_SUSHESTVITELJNIH_PO_EFREMOVOJ);

            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Error IndexOutOfBoundsException");
        }

        catch (IOException e){
            System.out.println("Error IOException");
        }

        finally{

            writer1.close();
            writer2.close();
            writer3.close();
            writer4.close();
            writer5.close();
            writer6.close();
            writer7.close();
            writer8.close();

        }

    }


    public static String getFirstWordFromList(String sourceFileName) throws Exception{
        FileReader reader = new FileReader(sourceFileName);
        Scanner scan = new Scanner(reader);
        String word=null;

            while (scan.hasNextLine()) {
                word = scan.nextLine(); break; // читает первую строку
            }

        reader.close();
        scan.close();
        return word;
    }

    public static void writerNewFileVocabulary (String word, String definition, FileWriter writer) throws Exception {

        writer.write(word); //записывает данное слово
        writer.append(" - ");
        writer.append(definition);//записывает определение

        writer.append('\n');
        writer.flush();
    }


    public static String getWordDefinitionFromEfremova(String word, String fileEfremova) throws Exception{
        FileReader reader = new FileReader(fileEfremova);
        Scanner scan = new Scanner(reader);
        String definition = null;


        while (scan.hasNextLine()){

            String aa = scan.nextLine(); // очередная строка записывается в строку aa

            // System.out.println(word.length() + " word "); // проверить совпадает ли длина заданного слова и слова со строки
            // System.out.println(aa.length() + " aa "); // для того же
            // System.out.println(aa);


            // если заданное слово совпадает со словом строки aa делать
            if (word.equals(aa)) {


                String zero = scan.nextLine();  // пропускает строку после слова, там не нужная инфа
                if (zero.startsWith(" I") || zero.startsWith("I") || zero.startsWith(" (а также")) zero = scan.nextLine(); // пропустить еще одну лишнюю строку после слова, если она есть

                String a = scan.nextLine();

                if (a.startsWith(" 1)")) a = a.substring(3);
                if (a.startsWith(" а)")) a = a.substring(3);




                //выводит дефиницию(толкование) из словаря Ефремовой

                {
                    definition = a;
                    if (a.endsWith(".")) break;

                    for (int i = 0; i <4 ; i++) {
                        String b = scan.nextLine();
                        definition = definition + b;
                        if (b.endsWith(".")) break;
                    }

                }
                break;


            }

            else {
                definition = "Определение не найдено.";

            }

        }

        //если в строке есть лишние знаки аля " (1)", убирает

        for (int i = 1; i < 9; i++) {

            if (definition.indexOf(" (" + i +")")>0 ) {
                char[] mas = definition.toCharArray();
                for (int j = 0; j <definition.length(); j++) {

                    if(mas[j]== ' '&& mas[j+1]=='('&& mas[j+3]==')')   {
                        for (int k = 1; k <=4; k++) {
                            mas = remove(mas, j);
                        }
                        break;
                    }

                }
                definition = definition.copyValueOf(mas);
            }

        }


        //если в строке есть лишние знаки аля " (1*2-3)", убирает

        Pattern p = Pattern.compile("\\s\\D\\d\\D\\d\\D\\d\\D(\\s|\\.|,)");
        Matcher m = p.matcher(definition);

        if (m.find()) {
            char[] mas = definition.toCharArray();
            int j =m.start();

            for (int i = 1; i <=8; i++) {
                mas = remove(mas, j);
            }

            definition = definition.copyValueOf(mas);

        }
        m.reset();

        Pattern p2 = null;
        Matcher m2 = null;

        p2 = Pattern.compile("\\s?\\Q(противоп.: \\E.+\\Q)\\E");
        m2 = p2.matcher(definition);

        if (m2.find()) {
            System.out.println(definition);
            char[] mas = definition.toCharArray();
            int j =m2.start();
            int k =m2.end();
            for (int i = 1; i <=k-j; i++) {
                mas = remove(mas, j);
            }

            definition = definition.copyValueOf(mas);
            System.out.println(definition + " ПРОТИВОП.");
        }
        m2.reset();

        // System.out.println(word + " word");
        // System.out.println(definition);


        scan.close();
        return definition;
    }


    //удаляет символ из массива символов строки
    public static char[] remove(char[] mas, int index){
        int n = mas.length;
        char[] mas1 = new char[n-1];

        for (int i =index; i < n-1; i++)
            mas[i] = mas [i+1];

        for (int j = 0; j < mas1.length; j++) {
            mas1[j]=mas[j];
        }

        return mas1;
    }

     //удаляет первую строку в заданном файле,  увеличивает счётчик на 1, возвращает счётчик
    public static int deleteFirstLineFromList(int positionNum, String  sourceFileName) throws Exception{

        FileReader reader = new FileReader(sourceFileName);
        String outputFileName = sourceFileName + "_copy.txt";
        FileWriter writer = new FileWriter(outputFileName, false); //false - перезапись файла-копии, true - дозапись

        Scanner scan = new Scanner(reader);

        String line = scan.nextLine();

        while (scan.hasNextLine()) {

            line = scan.nextLine();

            {
                writer.write(line);
                writer.write('\n');

            }
        }

        scan.close();
        writer.flush();
        writer.close();

        fileDeleteRename(sourceFileName, outputFileName);

        positionNum ++;
        return positionNum;
    }

    //обновляет файл
    public static void fileDeleteRename(String sourceFileName, String outputFileName){
        File  reader = new File(sourceFileName);
        File writer = new File(outputFileName);
        reader.delete();
        writer.renameTo(reader);

    }

    //если файл не пустой, возвращает true
    public static boolean nonEmptyFile(String sourceFileName) throws Exception{
        boolean a = false;
        FileReader reader = new FileReader(sourceFileName);
        Scanner scan = new Scanner(reader);
        if (scan.hasNextLine()) a = true;

        scan.close();
        return a;
    }

    //создаёт новый список
    public static void writerNewListWords(String word, FileWriter writer) throws Exception{

        writer.write(word); //записывает данное слово
        writer.append('\n');
        writer.flush();

    }
}