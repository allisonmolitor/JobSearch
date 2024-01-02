import java.util.InputMismatchException;
import java.util.Scanner;

public class Frontend {

    private Backend backend; 
    private Scanner userInput; 
    public boolean active;
    
    public Frontend(Backend backend, Scanner userInput) {
        
        this.backend = backend; 
        this.userInput = userInput;
    }

    public void startControlLoop() {
        
        
        active = false;
        
            while(!active) { 
            System.out.println("Welcome to the Main Menu! Please pick an option by entering the corresponding number.");
            System.out.println("1) Input a File");
            System.out.println("2) Search Menu");
            System.out.println("3) Get Menu");
            System.out.println("4) Apply Now!");
            System.out.println("5) Exit");
            System.out.print("Enter your choice here: ");
            int option = userInput.nextInt();
            userInput.nextLine();
            if (option == 1) {
                fileInput();
            }else if (option == 2) {
                searchMenu();
            }else if (option == 3) {
                getMenu();
            }else if (option == 4) {
                apply();
            } else if (option == 5) {
                active = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    public void searchMenu() {

        boolean active = false;
       
        while (!active) {
            System.out.println("Welcome to the Search Menu! Here you can find a job by location, "
                    + " industry, rating, or salary! \nPlease pick an option by entering the corresponding number");
            System.out.println("1. Search for a job by location");
            System.out.println("2. Search for a job by industry");
            System.out.println("3. Search for a job by rating");
            System.out.println("4. Search for a job by salary");
            System.out.println("5. Exit");
            System.out.print("Enter your choice here: ");
            int option = userInput.nextInt();
            if (option == 1) {
                findJobByLocation();
            } else if (option == 2) {
                findJobByIndustry();
            } else if (option == 3) {
                findJobByRating();
            } else if (option == 4) {
                findJobBySalary();
            } else if (option == 5) {
                active = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    
    public void getMenu() {
        boolean active = false;

        while (!active) {
            System.out.println("Welcome to the Get Menu! Here you can get information about a job by specifying the company name.");
            System.out.println("Please pick an option by entering the corresponding number");
            System.out.println("1. Get description of job");
            System.out.println("2. Get website of job");
            System.out.println("3. Get company size of job");
            System.out.println("4. Get latest reviews of job");
            System.out.println("5. Exit");
            System.out.print("Enter your choice here: ");

            int option = userInput.nextInt();
            userInput.nextLine(); 

            if (option == 1) {
                getDescription();
            } else if (option == 2) {
                getWebsite();
            } else if (option == 3) {
                getCompanySize();
            } else if (option == 4) {
                getLatestReviews();
            } else if (option == 5) {
                active = true;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    public void fileInput() {
            
        boolean active = false;
        
        while (!active) {
            System.out.println("Please pick an option by entering the corresponding number.");
            System.out.println("1. Input a File");
            System.out.println("2. Go to Search Menu");
            System.out.println("3. Go to Get Menu");
            System.out.println("4. Go back to Main Menu");
            System.out.print("Enter your choice here: ");
            int option = userInput.nextInt();
            if (option == 1) {
                inputNewFile();
            } else if (option == 2) {
                searchMenu();
            } else if (option == 3) {
                getMenu();
            } else if (option == 4) {
                active = true;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public void inputNewFile() {

        System.out.print("Please enter the file path here: ");
        String filePath = userInput.next();
        backend.readData(filePath);
    }

    public void findJobByLocation() {
        System.out.println("Enter the location (e.g., 'Fort Meade, MD'):");
        userInput.nextLine();
        String location = userInput.nextLine(); 

        backend.listCompaniesByLocation(location);
    }

    public void findJobByIndustry() {
        System.out.print("Enter the industry you want to search for jobs in (e.g., 'Aerospace'):");
        String partialIndustry = userInput.next();
        backend.listJobsByIndustry(partialIndustry); 
    }
    
    public void findJobByRating() {
        System.out.print("Enter the minimum rating to search for (e.g., '3.5'):");
        try {
            double minRating = userInput.nextDouble();
            backend.listJobsByRating(minRating); 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number for the rating.");
            userInput.nextLine(); 
        }
    }

    
    public void findJobBySalary() {
        System.out.print("Enter the minimum salary to search for (e.g., '50'): ");
        int minSalary = userInput.nextInt();
        
        System.out.print("Enter the maximum salary to search for (e.g., '100'): ");
        int maxSalary = userInput.nextInt();
        
        backend.listJobsBySalaryRange(minSalary, maxSalary); 
    }
    
    public void getDescription() {
        System.out.print("Enter the company name to get the description (e.g., 'Aylo Health'): ");
        String companyName = userInput.nextLine();
        backend.getDescriptionInfo(companyName, 80); 
    }
    
    public void getWebsite() {
        System.out.print("Enter the company name to get the website (e.g., 'Aylo Health'): ");
        String companyName = userInput.nextLine();
        backend.getWebsiteInfo(companyName);
    }
    
    public void getCompanySize() {
        System.out.print("Enter the company name to get the company size (e.g., 'Aylo Health'): ");
        String companyName = userInput.nextLine();
        backend.getCompanySizeInfo(companyName);
    }

    public void getLatestReviews() {
        System.out.print("Enter the company name to get the latest reviews (e.g., 'Aylo Health'): ");
        String companyName = userInput.nextLine();
        backend.getLatestReviewsInfo(companyName, 80); 
    }
    public void apply() {
        System.out.print("Enter the company name to get the URL to apply! (e.g., 'Aylo Health'): ");
        String companyName = userInput.nextLine();
        String applyUrl = backend.applyNow(companyName);

        if (applyUrl != null) {
            System.out.println("URL for " + companyName + ": " + applyUrl);
        } else {
            System.out.println("Company with name '" + companyName + "' not found.");
        }
    }
}