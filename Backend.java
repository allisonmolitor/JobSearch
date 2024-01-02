import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;


public class Backend {
    

    private IterableMultiKeyRBT<Job> storage;


    public Backend(IterableMultiKeyRBT<Job> storage) {
        this.storage = storage;
    }

    public void readData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder entryBuilder = new StringBuilder();
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                entryBuilder.append(line);

                if (line.endsWith("\"")) {
                    String[] data = entryBuilder.toString().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                    if (data.length == 13) {
                        data[0] = data[0].replaceAll("^\"|\"$", "");
                        Job job = new Job(
                                data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6], data[7], data[8], data[9],
                                data[10], data[11], data[12]
                        );

                        storage.insertSingleKey(job);
                    } else {
                        System.err.println("Invalid data format: " + entryBuilder.toString());
                    }

                    entryBuilder.setLength(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void listCompaniesByLocation(String location) {
        System.out.println("Companies in " + location + ":");

        for (Job job : storage) {
            if (job.getLocation().contains(location)) {
                System.out.println(job.getCompany());
            }
        }
    }
    
    public void listJobsByIndustry(String partialIndustry) {
        System.out.println("Jobs containing '" + partialIndustry + "' in the industry:");

        for (Job job : storage) {
            if (job.getIndustry().toLowerCase().contains(partialIndustry.toLowerCase())) {
                System.out.println("Company: " + job.getCompany() + ", Job Title: " + job.getJobTitle());
            }
        }
    }

    public void listJobsByRating(double minRating) {
        System.out.println("Jobs with a rating of " + minRating + " or higher:");

        for (Job job : storage) {
            String ratingStr = job.getRating().trim();
            if (!ratingStr.isEmpty()) {
                try {
                    double jobRating = Double.parseDouble(ratingStr);
                    if (jobRating >= minRating) {
                        System.out.println("Company: " + job.getCompany() + ", Job Title: " + job.getJobTitle() +
                                ", Rating: " + ratingStr);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid rating format for job: " + job.getCompany());
                }
            }
        }
    }

    public void listJobsBySalaryRange(int minSalary, int maxSalary) {
        System.out.println("Jobs with a salary between $" + minSalary + "K and $" + maxSalary + "K:");

        for (Job job : storage) {
            String salaryRange = job.getEstimatedSalary().trim();
            if (!salaryRange.isEmpty()) {
                try {
                    String[] salaryParts = salaryRange.split("-");
                    int lowSalary = Integer.parseInt(salaryParts[0].replaceAll("\\D", ""));
                    int highSalary = Integer.parseInt(salaryParts[1].replaceAll("\\D", ""));

                    if ((lowSalary >= minSalary && lowSalary <= maxSalary) ||
                        (highSalary >= minSalary && highSalary <= maxSalary)) {
                        System.out.println("Company: " + job.getCompany() + ", Job Title: " + job.getJobTitle() +
                                ", Estimated Salary Range: " + salaryRange);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid salary range format for job: " + job.getCompany());
                }
            }
        }
    }
    
    public void getDescriptionInfo(String companyName, int maxLineLength) {
        for (Job job : storage) {
            if (job.getCompany().trim().equalsIgnoreCase(companyName.trim())) {
                String description = job.getDescription();
                System.out.println("Description for " + companyName + ":");

                for (int i = 0; i < description.length(); i += maxLineLength) {
                    int endIndex = Math.min(i + maxLineLength, description.length());
                    System.out.println(description.substring(i, endIndex));
                }

                return;  
            }
        }
        System.out.println("Job with company name '" + companyName + "' not found.");
    }

    public void getWebsiteInfo(String companyName) {
        for (Job job : storage) {
            if (job.getCompany().trim().equalsIgnoreCase(companyName.trim())) {
                String website = job.getWebsite();
                if (website != null && !website.trim().isEmpty()) {
                    System.out.println("Company website for " + companyName + ": " + website);
                } else {
                    System.out.println("Website information not available for " + companyName);
                }
                return;  
            }
        }
        System.out.println("Job with company name '" + companyName + "' not found.");
    }

    public void getCompanySizeInfo(String companyName) {
        for (Job job : storage) {
            if (job.getCompany().trim().equalsIgnoreCase(companyName.trim())) {
                String companySize = job.getCompanySize();
                if (companySize != null && !companySize.trim().isEmpty()) {
                    System.out.println("Company Size for " + companyName + ": " + companySize);
                } else {
                    System.out.println("Company Size information not available for " + companyName);
                }
                return;  
            }
        }
        System.out.println("Job with company name '" + companyName + "' not found.");
    }
    
    public void getLatestReviewsInfo(String companyName, int maxLineLength) {
        for (Job job : storage) {
            if (job.getCompany().trim().equalsIgnoreCase(companyName.trim())) {
                String latestReviews = job.getLatestReviews();
                if (latestReviews != null && !latestReviews.trim().isEmpty()) {
                    System.out.println("Latest Reviews for " + companyName + ":");
                    
                    for (int i = 0; i < latestReviews.length(); i += maxLineLength) {
                        int endIndex = Math.min(i + maxLineLength, latestReviews.length());
                        System.out.println(latestReviews.substring(i, endIndex));
                    }
                } else {
                    System.out.println("Latest Reviews information not available for " + companyName);
                }
                return;  
            }
        }
        System.out.println("Job with company name '" + companyName + "' not found.");
    }

    
    public String applyNow(String companyName) {
        for (Job job : storage) {
            if (job.getCompany().trim().equalsIgnoreCase(companyName.trim())) {
                return job.getSearchUrl();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new Frontend(new Backend(new IterableMultiKeyRBT<>()), new Scanner(System.in)).startControlLoop();

    }
    
}
