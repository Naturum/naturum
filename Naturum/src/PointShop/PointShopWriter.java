/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PointShop;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author koayk
 */
public class PointShopWriter {
    public void WriteToMerchandiseOrder(String username, int purchaseNum, String merchandise, String address) {
        try {
            FileWriter myWriter = new FileWriter("MerchandiseOrder.txt", true);
            myWriter.write(username + " orders " + purchaseNum + " " + merchandise + " to " + address);
            myWriter.close();
            System.out.println("Successfully wrote to MerchandiseOrder file");
        }
        catch (IOException e) {
            System.out.println("Problem with file output");
            e.printStackTrace();
        }
    }
    
    public void WriteToTreePlantOrder(String username, String name) {
        try {
            FileWriter myWriter = new FileWriter("TreePlantOrder.txt", true);
            myWriter.write(username + " plants a tree with the name \"" + name + "\"");
            myWriter.close();
            System.out.println("Successfully wrote to TreePlantOrder.txt");
        }
        catch (IOException e) {
            System.out.println("Problem with file output");
            e.printStackTrace();
        }
    }
}
