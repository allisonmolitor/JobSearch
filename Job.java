public class Job implements Comparable<Job> {

    private String company;
    private String description;
    private String jobTitle;
    private String location;
    private String industry;
    private String website;
    private String companySize;
    private String estimatedSalary;
    private String foundedYear;
    private String latestReviews;
    private String recommendToFriend;
    private String rating;
    private String searchUrl;

    public Job(String company, String description, String jobTitle, String location,
               String industry, String website, String companySize, String estimatedSalary,
               String foundedYear, String latestReviews, String recommendToFriend,
               String rating, String searchUrl) {
        this.company = company;
        this.description = description;
        this.jobTitle = jobTitle;
        this.location = location;
        this.industry = industry;
        this.website = website;
        this.companySize = companySize;
        this.estimatedSalary = estimatedSalary;
        this.foundedYear = foundedYear;
        this.latestReviews = latestReviews;
        this.recommendToFriend = recommendToFriend;
        this.rating = rating;
        this.searchUrl = searchUrl;
    }

    // Getters for each field

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getIndustry() {
        return industry;
    }

    public String getWebsite() {
        return website;
    }

    public String getCompanySize() {
        return companySize;
    }

    public String getEstimatedSalary() {
        return estimatedSalary;
    }

    public String getFoundedYear() {
        return foundedYear;
    }

    public String getLatestReviews() {
        return latestReviews;
    }

    public String getRecommendToFriend() {
        return recommendToFriend;
    }

    public String getRating() {
        return rating;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    @Override
    public int compareTo(Job o) {
        return this.jobTitle.compareTo(o.jobTitle);

    }
}

