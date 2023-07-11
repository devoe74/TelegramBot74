import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class UserStatisticsPanel extends JPanel {
    Bot74 bot74 = new Bot74();
    private String mustPopularAPI;
    private JLabel[] labels;


    public UserStatisticsPanel() throws FileNotFoundException, UnirestException, URISyntaxException {
        this.setLayout(null);
        this.setBackground(new Color(255, 255, 185));
        JLabel label = new JLabel(this.getClass().getName());
        Font font1 = new Font("Ariel", Font.BOLD, 25);
        label.setFont(font1);
        label.setBounds(5,5,300,27);
        this.add(label);

        this.labels = new JLabel[] {new JLabel() , new JLabel() ,new JLabel() ,new JLabel()};

        int counter = 1;
        for (JLabel jLabel : this.labels) {
            jLabel.setFont(ChoicesPanel.FOND1);
            jLabel.setBounds(25, 100 * counter , 450 , 25);
            counter++;
            this.add(jLabel);
        }
        updateData();
    }

    public void updateData(){
        new Thread(()->{
            while (true){
                this.labels[0].setText("total number of requests: " + calcTotalNumberOfRequests());
                this.labels[1].setText("total number of users: " + calcTotalNumberOfUsers());
                this.labels[2].setText("must active user: " + calcMustActiveUser());
                this.labels[3].setText("must popular API: " + calcMustPopularAPI());


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }


    public int calcTotalNumberOfUsers(){
        return Bot74.users.size();
    }
    public int calcTotalNumberOfRequests(){
        int total = 0;
        for (MyUser user : Bot74.users.values()) {
            total += user.getNumberOfMessages();
        }
        return total;
    }
    public String calcMustActiveUser(){
        String name = "";
        int max = 0;
        for (MyUser user : Bot74.users.values()) {
            if (user.getNumberOfMessages() > max){
                max = user.getNumberOfMessages();
                if (user.getUserName() != null){
                    name = user.getUserName() + ", with: " + max + " massages";
                }
                else {
                    name = user.getFirstName();
                    if (user.getLastName() != null){
                        name += " " + user.getLastName();
                    }
                    name +=  ", with: " + max + " massages";
                }
            }
        }
        return name;
    }

    public String calcMustPopularAPI(){
        if (Bot74.selectedChoicesUses != null){
            Integer index = null;
            int max = 0;

            for (int i = 0 ; i < Bot74.selectedChoicesUses.length ; i++) {
                if (Bot74.selectedChoicesUses[i] > max){
                    max = Bot74.selectedChoicesUses[i];
                    index = i;
                }
            }
            if (index != null){
                return ChoicesPanel.getSelectedChoices()[index];
            }
        }
        return null;
    }



}
