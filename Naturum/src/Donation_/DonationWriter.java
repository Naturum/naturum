/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Donation_;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author koayk
 */
public class DonationWriter {
    public void WriteToDonationFile(String username, double donation, String ngo) {
        try {
            FileWriter myWriter = new FileWriter("Donations.txt", true);
            myWriter.write(username + " has donated $" + donation + " to " + ngo + "\n");
            myWriter.close();
            System.out.println("Successfully write to the file");
        }
        catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
}
