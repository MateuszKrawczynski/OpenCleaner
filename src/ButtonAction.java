import javax.swing.*;
import java.awt.*;
import java.io.File.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.List;

public class ButtonAction {
    static Runtime cmdClient = Runtime.getRuntime();
    public static JPanel panel = Main.mainframe.centerPanel;
    static void cmdrun(String command){
        try{ cmdClient.exec(new String[]{"cmd","/K",command}); }
        catch(Exception e){System.out.println(e);}

    }
    public static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');

        // handle cases with no extension or multiple dots
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";          // no extension found
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }
    static void resetCenterPanel(LayoutManager layout){
        panel.removeAll();
        panel.setLayout(layout);
        panel.revalidate();
        panel.repaint();
    }
    ButtonAction(){

    }

        static boolean checkBoxStatus ( int id){
            try{
                FileReader fileReader = new FileReader("customization.txt");
                if (fileReader.readAllAsString().charAt(id) == '1'){return true;}
                else{ return false;}
            } catch (Exception e){return false;}

        }
    static boolean checkBoxStatus2 ( int id){
        try{
            FileReader fileReader = new FileReader("customization2.txt");
            if (fileReader.readAllAsString().charAt(id) == '1'){return true;}
            else{ return false;}
        } catch (Exception e){return false;}

    }
        static void setBoxStatus(CheckBox box,int id){
            box.setSelected(checkBoxStatus(id));
        }
    static void setBoxStatus2(CheckBox box,int id){
        box.setSelected(checkBoxStatus2(id));
    }
        static JProgressBar progressBar;
        static StringBuilder filelist;
        static HashMap<String,Long> extMap;
        static int AmountOfFilesInC = 0;
        static int ProgressCounted = 0;
    static void walk() {
        try {
            Files.walkFileTree(Paths.get("C:\\"), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!attrs.isDirectory()) {
                        filelist.append(file.toAbsolutePath().toString().toLowerCase()).append("\n");
                        String extens = getExtension(file);

                        extMap.putIfAbsent(extens, 0L);
                        System.out.println(attrs.size());
                        extMap.put(extens, extMap.get(extens) + attrs.size());

                        ProgressCounted++;
                        if (ProgressCounted % 1000 == 0) {progressBar.setValue(Math.round(100f * ProgressCounted / AmountOfFilesInC));}
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {

        }
    }
    static void count(){
        try {
            Files.walkFileTree(Paths.get("C:\\"), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!attrs.isDirectory()) {
                        AmountOfFilesInC++;

                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {

        }
    }
    static HashMap<String, Long> sortByValue(HashMap<String, Long> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Long> > list =
                new LinkedList<Map.Entry<String, Long> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>() {
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue()); // reversed
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Long> temp = new LinkedHashMap<String, Long>();
        for (Map.Entry<String, Long> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
        static void scanResultLoad(){
            resetCenterPanel(new BorderLayout());
            JPanel titlePanel = new JPanel();
            titlePanel.setPreferredSize(new Dimension(100, 50));
            JLabel titleLabel = new JLabel("Analize files, check what takes up your disk space");
            titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
            titlePanel.add(titleLabel);
            panel.add(titlePanel, BorderLayout.NORTH);
            JPanel center = new JPanel();
            center.setLayout(new BorderLayout());
            JPanel latestScanTXTPanel = new JPanel();
            latestScanTXTPanel.setPreferredSize(new Dimension(100,30));
            JLabel latestScanTXTLabel = new JLabel("Your latest scan:");
            latestScanTXTLabel.setFont(new Font("Calibri",Font.PLAIN,23));
            latestScanTXTLabel.setVerticalAlignment(JLabel.CENTER);
            latestScanTXTPanel.add(latestScanTXTLabel);
            center.add(latestScanTXTPanel,BorderLayout.NORTH);
            JPanel parentForLRPanels = new JPanel();
            parentForLRPanels.setLayout(new GridLayout(1,2));
            JPanel leftPanel = new JPanel();
            JPanel rightPanel = new JPanel();
            JScrollPane leftScrollPane = new JScrollPane(leftPanel);
            JScrollPane rightScrollPane = new JScrollPane(rightPanel);
            leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            rightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            parentForLRPanels.add(leftScrollPane);
            parentForLRPanels.add(rightScrollPane);
            leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
            rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
            JLabel leftTXT = new JLabel("What takes up your disk space the most:");
            leftTXT.setFont(new Font("Arial",Font.PLAIN,25));
            JLabel rightTXT = new JLabel("All of your files, you can search for any file on your disk:");
            rightTXT.setFont(new Font("Arial",Font.PLAIN,25));

            leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
            rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));

            leftPanel.add(leftTXT);
            rightPanel.add(rightTXT);
            String fileMapContent = "";
            try {
                fileMapContent = new FileReader("lastScanFileextmap.map").readAllAsString();
                AmountOfFilesInC = Integer.parseInt( new FileReader("amountOfFilesInC.txt").readAllAsString() );
            } catch(Exception e){}
            String[] fileMapContentSplitted = fileMapContent.split("\n");
            extMap = new HashMap<String,Long>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0;i<fileMapContentSplitted.length;i++){
                        if (fileMapContentSplitted[i].isEmpty()){

                        }
                        else if (fileMapContentSplitted[i].split("%//%").length == 1){
                            System.out.println(Arrays.toString(fileMapContentSplitted[i].split("%//%")));
                            extMap.put("No filetype",Long.parseLong(fileMapContentSplitted[i].split("%//%")[0]));
                        }
                        else{
                            extMap.put(fileMapContentSplitted[i].split("%//%")[0],Long.parseLong(fileMapContentSplitted[i].split("%//%")[1]));}
                    }
                    HashMap<String,Long> sortedExtMap = sortByValue(extMap);
                    String[] keysOfSortedExtMap = new String[sortedExtMap.keySet().size()];
                    keysOfSortedExtMap = sortedExtMap.keySet().toArray(keysOfSortedExtMap);
                    for (int i = 0;i<keysOfSortedExtMap.length;i++){

                        JLabel lblBuild = new JLabel();
                        lblBuild.setText(keysOfSortedExtMap[i].toString() + " takes "+String.format("%.6f",(100f*sortedExtMap.get(keysOfSortedExtMap[i].toString()))/(new File("C:\\").getTotalSpace()-new File("C:\\").getFreeSpace())) + "% of used disk space");
                        lblBuild.setFont(new Font("Arial",Font.PLAIN,20));
                        leftPanel.add(lblBuild);
                    }
                    leftPanel.revalidate();
                    leftPanel.repaint();
                }
            }).start();
            JTextField searchBar = new JTextField("Search for a file");
            searchBar.setSize(new Dimension(100,1));
            searchBar.setFont(new Font("Arial",Font.BOLD,17));
            rightPanel.add(searchBar);
            JButton searchButton = new JButton();
            searchButton.setBackground(Color.black);
            searchButton.setForeground(Color.white);
            searchButton.setFocusPainted(false);
            searchButton.setFocusable(false);
            searchButton.setBorder(null);
            searchButton.setIcon(new ImageIcon("img/search_icon.png"));
            searchButton.setIconTextGap(10);
            rightPanel.add(searchButton);
            String[] filepathsNotInit = new String[]{};
            try{
                filepathsNotInit = new FileReader("lastScanFilelist.txt").readAllAsString().split("\n");
            } catch(Exception e){}
            final String[] filepaths = filepathsNotInit;
            Thread globalFileListingThread = new Thread(new Runnable() {
                @Override
                public void run() {



                    for (int i = 0;i<filepaths.length;i++){
                        if (Thread.currentThread().isInterrupted()) return;
                        String curFile = filepaths[i];

                        if (!curFile.isEmpty()){
                            JTextField lblBuild = new JTextField();
                            lblBuild.setFont(new Font("Arial",Font.PLAIN,20));
                            lblBuild.setText(curFile);
                            lblBuild.setBorder(null);
                            lblBuild.setEditable(false);
                            lblBuild.setPreferredSize(new Dimension(100,30));
                            rightPanel.add(lblBuild);
                        }
                    }

                }
            });
            globalFileListingThread.start(); // to stop globalFileListingThread.interrupt()
            var ref = new Object() {
                public Thread currentListingThread = globalFileListingThread;
            };
            searchButton.addActionListener(l -> {
                String entry = searchBar.getText().stripTrailing().toLowerCase();
                if (entry.contentEquals("search for a file")){ searchBar.setText(""); }
                else{
                    ref.currentListingThread.interrupt();
                    rightPanel.removeAll();
                    rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
                    rightPanel.add(rightTXT); rightPanel.add(searchBar); rightPanel.add(searchButton);
                    rightPanel.repaint();
                    ref.currentListingThread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SwingUtilities.invokeLater(()->{
                                String[] filepaths = new String[]{};
                                try{
                                    filepaths = new FileReader("lastScanFilelist.txt").readAllAsString().split("\n");
                                } catch(Exception e){}
                                for (int i = 0;i<filepaths.length;i++){
                                    if (Thread.currentThread().isInterrupted()) return;
                                    String curFile = filepaths[i];
                                    if (!entry.isBlank() && curFile.contains(entry)){
                                        JTextField lblBuild = new JTextField();
                                        lblBuild.setFont(new Font("Arial",Font.PLAIN,20));
                                        lblBuild.setText(curFile);
                                        lblBuild.setBorder(null);
                                        lblBuild.setEditable(false);
                                        rightPanel.add(lblBuild);
                                    }
                                }
                                rightPanel.revalidate();
                                rightPanel.repaint();
                            });

                        }

                    });
                    ref.currentListingThread.start();
                }
            });

            JButton scanAgainButton = new JButton("Scan again");
            scanAgainButton.setBackground(Color.black);
            scanAgainButton.setForeground(Color.white);
            scanAgainButton.setFocusable(false);
            scanAgainButton.setFocusPainted(false);
            scanAgainButton.setPreferredSize(new Dimension(100,50));
            scanAgainButton.setFont(new Font("Arial",Font.PLAIN,30));
            scanAgainButton.addActionListener(l -> {
                scanDisk();
            });
            panel.add(scanAgainButton,BorderLayout.SOUTH);

            parentForLRPanels.add(leftScrollPane); parentForLRPanels.add(rightScrollPane);
            center.add(parentForLRPanels);
            panel.add(center,BorderLayout.CENTER);

        }
        static void scanDisk(){
            resetCenterPanel(new BorderLayout());
            JPanel titlePanel = new JPanel();
            titlePanel.setPreferredSize(new Dimension(100, 50));
            JLabel titleLabel = new JLabel("Analize files, check what takes up your disk space");
            titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
            titlePanel.add(titleLabel);
            panel.add(titlePanel, BorderLayout.NORTH);
            progressBar = new JProgressBar();
            progressBar.setMinimum(0);
            progressBar.setMaximum(100);
            progressBar.setValue(0);
            progressBar.repaint();
            progressBar.setPreferredSize(new Dimension(700,100));
            progressBar.setForeground(Color.green);
            JPanel midP = new JPanel();
            midP.setLayout(new FlowLayout());
            JLabel prog = new JLabel("Progress of your scan: ");
            prog.setFont(new Font("Calibri", Font.BOLD,40));
            midP.add(prog);
            midP.add(progressBar);
            panel.add(midP,BorderLayout.CENTER);
            // ACTUAL SCANNING PROCESS


            filelist = new StringBuilder();
            extMap = new HashMap<>();



            new Thread(new Runnable() {
                @Override
                public void run() {
                    count();
                    System.out.println(AmountOfFilesInC);
                    walk();
                    try {
                        FileWriter writerTXT = new FileWriter("lastScanFilelist.txt");
                        writerTXT.write(filelist.toString());
                        writerTXT.close();
                        FileWriter writerMAP = new FileWriter("lastScanFileextmap.map");
                        // This will not be JSON but key -> value seperated by {|:|}
                        String buildMap = "";
                        Object[] arr = extMap.keySet().toArray();
                        for (int i = 0;i<arr.length;i++){
                            buildMap += arr[i].toString() + "%//%" + extMap.get(arr[i].toString()).toString() + "\n";
                        }
                        writerMAP.write(buildMap);
                        writerMAP.close();
                        FileWriter amount = new FileWriter("amountOfFilesInC.txt");
                        amount.write(String.valueOf(AmountOfFilesInC));
                        amount.close();
                        //AFTER SCAN
                        SwingUtilities.invokeLater(() -> {
                            scanResultLoad();
                        });

                    } catch (Exception e) {}
                }
            }).start();


        }

    public static void exec(int id){

            switch (id) {
                case 2: {
                    // START OF CUSTOMIZED CLEAN
                    resetCenterPanel(new BorderLayout());
                    JPanel titlePanel = new JPanel();
                    titlePanel.setPreferredSize(new Dimension(100, 50));
                    JLabel titleLabel = new JLabel("Customized clean, select what you want to clean up");
                    titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
                    titlePanel.add(titleLabel);
                    panel.add(titlePanel, BorderLayout.NORTH);

                    JPanel boxPanel = new JPanel();
                    boxPanel.setLayout(new GridLayout(4, 4, 2, 3));
                    CheckBox trash = new CheckBox("Clear trash bin", 0,1);
                    setBoxStatus(trash, 0);
                    boxPanel.add(trash);
                    CheckBox downloads = new CheckBox("Delete downloads", 1,1);
                    setBoxStatus(downloads, 1);
                    boxPanel.add(downloads);
                    CheckBox temp = new CheckBox("Clear temporary files", 2,1);
                    setBoxStatus(temp, 2);
                    boxPanel.add(temp);
                    CheckBox chdisk = new CheckBox("Clear chdisk files", 3,1);
                    setBoxStatus(chdisk, 3);
                    boxPanel.add(chdisk);
                    CheckBox dns = new CheckBox("Clear DNS cache", 4,1);
                    setBoxStatus(dns, 4);
                    boxPanel.add(dns);
                    CheckBox memoryDump = new CheckBox("Clear memory dump", 5,1);
                    setBoxStatus(memoryDump, 5);
                    boxPanel.add(memoryDump);
                    CheckBox notifications = new CheckBox("Delete notifications", 7,1);
                    setBoxStatus(notifications, 7);
                    boxPanel.add(notifications);
                    CheckBox tempWebFiles = new CheckBox("Delete temporary web files", 8,1);
                    setBoxStatus(tempWebFiles, 8);
                    boxPanel.add(tempWebFiles);
                    CheckBox cookies = new CheckBox("Delete web cookies", 9,1);
                    setBoxStatus(cookies, 9);
                    boxPanel.add(cookies);
                    CheckBox searchHistory = new CheckBox("Delete web search history", 10,1);
                    setBoxStatus(searchHistory, 10);
                    boxPanel.add(searchHistory);
                    CheckBox directxNvidia = new CheckBox("Delete DirectX cache (NVIDIA GPU)", 11,1);
                    setBoxStatus(directxNvidia, 11);
                    boxPanel.add(directxNvidia);
                    CheckBox directxAmd = new CheckBox("Delete DirectX cache (AMD GPU)", 12,1);
                    setBoxStatus(directxAmd, 12);
                    boxPanel.add(directxAmd);
                    CheckBox directxIntel = new CheckBox("Delete DirectX cache (INTEL GPU)", 13,1);
                    setBoxStatus(directxIntel, 13);
                    boxPanel.add(directxIntel);
                    CheckBox thumbnailCache = new CheckBox("Clear thumbnail cache", 14,1);
                    setBoxStatus(thumbnailCache, 14);
                    boxPanel.add(thumbnailCache);
                    CheckBox webFormData = new CheckBox("[NOT RECOMMENDED]Clear web form data", 15,1);
                    setBoxStatus(webFormData, 15);
                    boxPanel.add(webFormData);
                    panel.add(boxPanel);

                    JPanel bottomPanel = new JPanel();
                    bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 35, 10));
                    bottomPanel.setPreferredSize(new Dimension(100, 50));
                    bottomPanel.setBackground(new Color(107, 107, 106));
                    panel.add(bottomPanel, BorderLayout.SOUTH);
                    JLabel beforeGB = new JLabel();
                    beforeGB.setForeground(Color.white);
                    beforeGB.setFont(new Font("arial", Font.PLAIN, 17));
                    beforeGB.setText(String.format("Current disk space: %.1f GB of free space", (double) new File("C:\\").getFreeSpace() / (1024 * 1024 * 1024)));
                    bottomPanel.add(beforeGB);
                    JButton cleanBut = new JButton("Start the clean");
                    cleanBut.setBackground(Color.white);
                    cleanBut.setForeground(Color.BLACK);
                    cleanBut.setFocusable(false);
                    cleanBut.setFocusPainted(false);
                    cleanBut.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                    cleanBut.setPreferredSize(new Dimension(300, 35));
                    cleanBut.setFont(new Font("arial", Font.BOLD, 17));
                    cleanBut.addActionListener(l -> {
                        // ACTUAL CLEANING PROCESS
                        if (JOptionPane.showConfirmDialog(Main.mainframe,"Are you sure you want to do this clean?","Open Cleaner confirmation",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0) {
                            beforeGB.setText(String.format("Current disk space: %.1f GB of free space", (double) new File("C:\\").getFreeSpace() / (1024 * 1024 * 1024)));
                            if (trash.getStatus()) {
                                cmdrun("powershell Clear-RecycleBin -Force");
                            }
                            if (downloads.getStatus()) {
                                cmdrun("del /f /s /q /a %userprofile%\\Downloads");
                            }
                            if (temp.getStatus()) {
                                cmdrun("del  /f /q /a /s %temp%\\*");
                            }
                            if (chdisk.getStatus()) {
                                cmdrun("del  /q  /f /a /s \\*.chk");
                            }
                            if (dns.getStatus()) {
                                cmdrun("ipconfig /flushdns");
                            }
                            if (memoryDump.getStatus()) {
                                cmdrun("del /f  /q /a /s %systemroot%\\memory.dmp");
                                cmdrun("del /f /s /q /a %systemroot%\\Minidump\\*.*");
                            }
                            if (notifications.getStatus()) {
                                cmdrun("taskkill /F /FI \"SERVICES eq WpnUserService_*\"");
                                cmdrun("del /a  /q /f /s %localappdata%\\Microsoft\\Windows\\Notifications");
                            }
                            if (tempWebFiles.getStatus()) {
                                cmdrun("taskkill /f /im msedge.exe");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\\\Cache\\*\"");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\Code Cache\\*\"");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\GPUCache\\*\"");
                                cmdrun("taskkill /f /im chrome.exe");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\Cache\\*\"");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\Code Cache\\*\"");
                                cmdrun("del /f /q /s \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\GPUCache\\*\"");
                                cmdrun("taskkill /f /im firefox.exe");
                                cmdrun("del /f /q /s \"%AppData%\\Mozilla\\Firefox\\Profiles\\*\\cache2\\*\"");
                            }
                            if (cookies.getStatus()) {
                                cmdrun("taskkill /f /im msedge.exe");
                                cmdrun("taskkill /f /im chrome.exe");
                                cmdrun("taskkill /f /im firefox.exe");
                                cmdrun("del /f /q \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\Cookies\"");
                                cmdrun("del /f /q \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\Cookies\"");
                                cmdrun("del /f /q \"%AppData%\\Mozilla\\Firefox\\Profiles\\*\\cookies.sqlite\"");
                            }
                            if (searchHistory.getStatus()) {
                                cmdrun("taskkill /f /im msedge.exe");
                                cmdrun("taskkill /f /im chrome.exe");
                                cmdrun("taskkill /f /im firefox.exe");
                                cmdrun("del /f /q \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\History\"");
                                cmdrun("del /f /q \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\History\"");
                                cmdrun("del /f /q \"%AppData%\\Mozilla\\Firefox\\Profiles\\*\\places.sqlite\"");
                            }
                            if (directxNvidia.getStatus()) {
                                cmdrun("taskkill /f /im msedge.exe");
                                cmdrun("taskkill /f /im chrome.exe");
                                cmdrun("taskkill /f /im firefox.exe");
                                cmdrun("del /f /q /s /a %localappdata%\\D3DSCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\NVIDIA\\DXCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\NVIDIA\\GLCache\\*");
                            }
                            if (directxAmd.getStatus()) {
                                cmdrun("del /f /q /s /a %localappdata%\\D3DSCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\AMD\\DXCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\AMD\\GLCache\\*");
                            }
                            if (directxIntel.getStatus()) {
                                cmdrun("del /f /q /s /a %localappdata%\\D3DSCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\Intel\\ShaderCache\\*");
                                cmdrun("del /f /q /s /a %localappdata%\\Intel\\DXCache\\*");
                            }
                            if (thumbnailCache.getStatus()) {
                                cmdrun("del /f /s /q /a %LocalAppData%\\Microsoft\\Windows\\Explorer\\thumbcache_*.db");
                            }
                            if (webFormData.getStatus()) {
                                if ( JOptionPane.showConfirmDialog(Main.mainframe,"ARE YOU SURE YOU WANT TO DELETE ALL YOUR WEB FORM DATA (PASSWORD AUTOFILLS)?","Open Cleaner confirmation",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ){
                                cmdrun("taskkill /f /im msedge.exe");
                                cmdrun("taskkill /f /im chrome.exe");
                                cmdrun("taskkill /f /im firefox.exe");
                                cmdrun("del /f /q \"%LocalAppData%\\Microsoft\\Edge\\User Data\\Default\\Web Data\"");
                                cmdrun("del /f /q \"%LocalAppData%\\Google\\Chrome\\User Data\\Default\\Web Data\"");
                                cmdrun("del /f /q \"%AppData%\\Mozilla\\Firefox\\Profiles\\*\\formhistory.sqlite\"");}
                            }


                        }
                        JOptionPane.showMessageDialog(panel,"Your clean has ended","Open Cleaner",JOptionPane.PLAIN_MESSAGE);
                    });
                    bottomPanel.add(cleanBut);
                    break;
                    // END OF CUSTOM CLEAN
                }
                case 4: {
                    // START OF ANALIZE FILES
                    resetCenterPanel(new BorderLayout());
                    JPanel titlePanel = new JPanel();
                    titlePanel.setPreferredSize(new Dimension(100, 50));
                    JLabel titleLabel = new JLabel("Analize files, check what takes up you disk space");
                    titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
                    titlePanel.add(titleLabel);
                    panel.add(titlePanel, BorderLayout.NORTH);
                    if (!(new File("lastScanFilelist.txt").exists())){
                        try{
                            new File("lastScanFilelist.txt").createNewFile();
                            new File("lastScanFileextmap.map").createNewFile();
                            new File("amountOfFilesInC.txt").createNewFile();
                        } catch (Exception e){}
                        JButton but = new JButton("<html>Click this to scan & analize your files for the first time. <br/> It can take a while so be patient. <br/> It will make a list of all your files on the C:\\ hard drive <br/> and will give you information on how many percent of your disk space each filetype takes</html> ");
                        but.setBackground(Color.darkGray);
                        but.setForeground(Color.white);
                        but.setFont(new Font("Calibri",Font.PLAIN,30));
                        but.setFocusable(false);
                        but.setFocusPainted(false);
                        panel.add(but,BorderLayout.CENTER);
                        but.addActionListener(l -> { scanDisk(); });
                    }
                    else{
                        SwingUtilities.invokeLater(() -> {
                            scanResultLoad();
                        });
                    }
                    break;
                }
                case 6:
                    //Start of organize files (music,documents,executables,photos,others)
                {
                    resetCenterPanel(new BorderLayout());
                    JPanel titlePanel = new JPanel();
                    titlePanel.setPreferredSize(new Dimension(100, 50));
                    JLabel titleLabel = new JLabel("Organize your files, create a new folder for: ");
                    titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
                    titlePanel.add(titleLabel);
                    panel.add(titlePanel, BorderLayout.NORTH);
                    JPanel boxes = new JPanel();
                    boxes.setLayout(new GridLayout(2,3));
                    CheckBox music = new CheckBox("Music",1,2);setBoxStatus2(music,1);
                    CheckBox documents = new CheckBox("Documents",2,2);setBoxStatus2(documents,2);
                    CheckBox executables = new CheckBox("Executables",3,2);setBoxStatus2(executables,3);
                    CheckBox photos = new CheckBox("Photos",4,2);setBoxStatus2(photos,4);
                    CheckBox others = new CheckBox("Others",5,2);setBoxStatus2(others,5);
                    boxes.add(music);
                    boxes.add(documents);
                    boxes.add(executables);
                    boxes.add(photos);
                    boxes.add(others);
                    JButton perform = new JButton("Select a folder & organize it");
                    perform.setPreferredSize(new Dimension(100,60));
                    perform.setFont(new Font("Calibri",Font.BOLD,30));
                    perform.setFocusable(false);
                    perform.setFocusPainted(false);
                    perform.setBorder(null);
                    perform.setBackground(Color.black);
                    perform.setForeground(Color.white);
                    perform.addActionListener(l -> {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int result = fileChooser.showDialog(panel,"Select a folder that you want to organize"); // lub showSaveDialog(null)
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            if (selectedFile.isDirectory()){
                                // The actual organizing process
                                File rootManager = new File(selectedFile.getAbsolutePath());
                                if (music.getStatus()){
                                    File musicManager = new File(selectedFile.getAbsolutePath()+"\\Music");
                                    musicManager.mkdir();
                                    File[] tmpList = rootManager.listFiles();
                                    if (tmpList != null){
                                        String[] filterExtensions = new String[]{"mp3","aac","flac","wav","m4a","wma","ogg","aiff","alac","midi"};
                                        for (int i = 0;i<tmpList.length;i++){
                                            if (!tmpList[i].isDirectory()){
                                            String actualExtensionOfCurrentFile = getExtension(Paths.get(tmpList[i].getAbsolutePath())).toLowerCase();
                                            boolean isMusicFile = false;
                                            for (int j = 0;j<filterExtensions.length;j++){ if (filterExtensions[j].equals(actualExtensionOfCurrentFile)){ isMusicFile = true;break;} }
                                            if (isMusicFile) { tmpList[i].renameTo(new File(selectedFile.getAbsolutePath()+"\\Music\\"+Paths.get(tmpList[i].getAbsolutePath()).getFileName()) ); }
                                            }

                                            }

                                    }

                                }
                                //
                                if (documents.getStatus()){
                                    File musicManager = new File(selectedFile.getAbsolutePath()+"\\Documents");
                                    musicManager.mkdir();
                                    File[] tmpList = rootManager.listFiles();
                                    if (tmpList != null){
                                        String[] filterExtensions = new String[]{"txt","doc","docx","rtf","odt","pdf","tex","wpd","pages","md","html","htm","xml","epub","mobi","azw","azw3","djvu","log","csv","tsv","sxw","abw","latex"};
                                        for (int i = 0;i<tmpList.length;i++){
                                            if (!tmpList[i].isDirectory()){
                                                String actualExtensionOfCurrentFile = getExtension(Paths.get(tmpList[i].getAbsolutePath())).toLowerCase();
                                                boolean isMusicFile = false;
                                                for (int j = 0;j<filterExtensions.length;j++){ if (filterExtensions[j].equals(actualExtensionOfCurrentFile)){ isMusicFile = true;break;} }
                                                if (isMusicFile) { tmpList[i].renameTo(new File(selectedFile.getAbsolutePath()+"\\Documents\\"+Paths.get(tmpList[i].getAbsolutePath()).getFileName()) ); }
                                            }

                                        }

                                    }

                                }
                                //
                                if (executables.getStatus()){
                                    File musicManager = new File(selectedFile.getAbsolutePath()+"\\Executables");
                                    musicManager.mkdir();
                                    File[] tmpList = rootManager.listFiles();
                                    if (tmpList != null){
                                        String[] filterExtensions = new String[]{"exe","msi","bat","cmd","com","scr","ps1","vbs","jar","app","apk","ipa","bin","run","gadget","wsf","cpl","msc","sh","bash","ksh","zsh","py","pl","rb"};
                                        for (int i = 0;i<tmpList.length;i++){
                                            if (!tmpList[i].isDirectory()){
                                                String actualExtensionOfCurrentFile = getExtension(Paths.get(tmpList[i].getAbsolutePath())).toLowerCase();
                                                boolean isMusicFile = false;
                                                for (int j = 0;j<filterExtensions.length;j++){ if (filterExtensions[j].equals(actualExtensionOfCurrentFile)){ isMusicFile = true;break;} }
                                                if (isMusicFile) { tmpList[i].renameTo(new File(selectedFile.getAbsolutePath()+"\\Executables\\"+Paths.get(tmpList[i].getAbsolutePath()).getFileName()) ); }
                                            }

                                        }

                                    }

                                }
                                //
                                if (photos.getStatus()){
                                    File musicManager = new File(selectedFile.getAbsolutePath()+"\\Photos");
                                    musicManager.mkdir();
                                    File[] tmpList = rootManager.listFiles();
                                    if (tmpList != null){
                                        String[] filterExtensions = new String[]{"jpg","jpeg","png","gif","bmp","tiff","tif","webp","heic","heif","raw","cr2","nef","arw","dng","orf","rw2","sr2","raf","pef","ico","jfif","jp2","j2k","svg"};
                                        for (int i = 0;i<tmpList.length;i++){
                                            if (!tmpList[i].isDirectory()){
                                                String actualExtensionOfCurrentFile = getExtension(Paths.get(tmpList[i].getAbsolutePath())).toLowerCase();
                                                boolean isMusicFile = false;
                                                for (int j = 0;j<filterExtensions.length;j++){ if (filterExtensions[j].equals(actualExtensionOfCurrentFile)){ isMusicFile = true;break;} }
                                                if (isMusicFile) { tmpList[i].renameTo(new File(selectedFile.getAbsolutePath()+"\\Photos\\"+Paths.get(tmpList[i].getAbsolutePath()).getFileName()) ); }
                                            }

                                        }

                                    }

                                }
                                //
                                if (others.getStatus()){
                                    File musicManager = new File(selectedFile.getAbsolutePath()+"\\Others");
                                    musicManager.mkdir();
                                    File[] tmpList = rootManager.listFiles();
                                    if (tmpList != null){
                                        for (int i = 0;i<tmpList.length;i++){
                                            if (!tmpList[i].isDirectory()){
                                                tmpList[i].renameTo(new File(selectedFile.getAbsolutePath()+"\\Others\\"+Paths.get(tmpList[i].getAbsolutePath()).getFileName()) );
                                            }

                                        }

                                    }

                                }
                            }
                            cmdrun("explorer "+selectedFile);
                        }

                    });
                    panel.add(perform,BorderLayout.SOUTH);
                    panel.add(boxes,BorderLayout.CENTER);

                    break;
                }
                case 9 :
                {
                    // start of about this software
                    resetCenterPanel(new BorderLayout());
                    JPanel titlePanel = new JPanel();
                    titlePanel.setPreferredSize(new Dimension(100, 50));
                    JLabel titleLabel = new JLabel("About open cleaner: ");
                    titleLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
                    titlePanel.add(titleLabel);
                    panel.add(titlePanel,BorderLayout.NORTH);
                    JPanel boxPanel = new JPanel();
                    boxPanel.setLayout(new BoxLayout(boxPanel,BoxLayout.Y_AXIS));
                    JLabel author = new JLabel("<html>This software was made by Mateusz Krawczyński, completly free and open source<br/><br/></html>");
                    author.setForeground(Color.blue);
                    author.setFont(new Font("Calibri",Font.ITALIC,40));
                    JLabel bigIcon = new JLabel();
                    bigIcon.setIcon(Main.globalAppIcon);
                    bigIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
                    author.setAlignmentX(Component.CENTER_ALIGNMENT);
                    author.setHorizontalTextPosition(JLabel.CENTER);
                    boxPanel.add(bigIcon);
                    boxPanel.add(author);
                    JLabel customizedClean = new JLabel("<html>Customized clean - Select what kind of junk files you want to clean up (SELECTING TO CLEAR WEB FORM DATA WILL MAKE ALL OF YOUR SAVED PASSWORDS IN BROWSER TO BE CLEARED!)<br/><br/></html>");
                    customizedClean.setForeground(new Color(0, 87, 16));
                    customizedClean.setFont(new Font("Arial",Font.BOLD,30));
                    customizedClean.setAlignmentX(Component.CENTER_ALIGNMENT);
                    customizedClean.setHorizontalTextPosition(JLabel.CENTER);
                    boxPanel.add(customizedClean);
                    JLabel analizeFiles = new JLabel("<html>Analize files - Scan your hard drive and know what filetype takes the most disk space, also you will be able to quickly localize any file on the hard drive<br/><br/></html>");
                    analizeFiles.setForeground(new Color(0, 87, 16));
                    analizeFiles.setFont(new Font("Arial",Font.BOLD,30));
                    analizeFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
                    analizeFiles.setHorizontalTextPosition(JLabel.CENTER);
                    boxPanel.add(analizeFiles);
                    JLabel organizeFiles = new JLabel("<html>Organize files - Select a folder and organize it by creating seperate folders for music,documents,photos etc.<br/><br/></html>");
                    organizeFiles.setForeground(new Color(0, 87, 16));
                    organizeFiles.setFont(new Font("Arial",Font.BOLD,30));
                    organizeFiles.setAlignmentX(Component.CENTER_ALIGNMENT);
                    organizeFiles.setHorizontalTextPosition(JLabel.CENTER);
                    boxPanel.add(organizeFiles);

                    panel.add(boxPanel,BorderLayout.CENTER);


                    break;
                }

            }
    }
}
