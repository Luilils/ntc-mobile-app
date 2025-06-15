package com.ntc.mobileapp.models;

public class PersonalData {
    // Personal Data Tab
    private String profilePictureUrl;
    private String fullName;
    private String studentId;
    private String courseAndSection;
    private String dateOfBirth;
    private String placeOfBirth;
    private String civilStatus;
    private String gender;
    private String citizenship;
    private String religion;
    private String lrn;
    private String esc;
    private String motherTongue;
    private String ethnicGroup;

    // New fields for Personal Data Tab
    private String enrollmentStatus;
    private String entryLevel;
    private String studentBatch;
    private String studentEntrySem;
    private String studentClassification;
    private String surname;
    private String firstName;
    private String middleName;
    private String provinceOrigin;

    // Contact Information Tab
    private String currentAddress;
    private String permanentAddress;
    private String mobileNumber;
    private String emailAddress;
    private String facebookAccount;

    // New fields for Contact Information Tab
    private String guardian;
    private String guardianJob;

    // In Case of Emergency Tab
    private String emergencyContactPerson;
    private String emergencyContactRelationship;
    private String emergencyContactNumber;
    private String emergencyContactAddress;

    // New field for Emergency Contact Tab
    private String emergencyHomePhoneNumber;

    // Other Information Tab
    private String medicalConditions;
    private String allergies;
    private String medications;
    private String hobbies;
    private String talents;
    private String awardsAchieved;
    private String organizationsJoined;

    // New fields for Other Information Tab
    private String disability;
    private String indigenousGroup;
    private String voucherType;
    private String scholarshipType;

    public PersonalData() {
        // Default constructor required for Firestore
    }

    // Getters and Setters for all fields

    // Personal Data Tab
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseAndSection() {
        return courseAndSection;
    }

    public void setCourseAndSection(String courseAndSection) {
        this.courseAndSection = courseAndSection;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getLrn() {
        return lrn;
    }

    public void setLrn(String lrn) {
        this.lrn = lrn;
    }

    public String getEsc() {
        return esc;
    }

    public void setEsc(String esc) {
        this.esc = esc;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    // Getters and Setters for new Personal Data Tab fields
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEntryLevel() {
        return entryLevel;
    }

    public void setEntryLevel(String entryLevel) {
        this.entryLevel = entryLevel;
    }

    public String getStudentBatch() {
        return studentBatch;
    }

    public void setStudentBatch(String studentBatch) {
        this.studentBatch = studentBatch;
    }

    public String getStudentEntrySem() {
        return studentEntrySem;
    }

    public void setStudentEntrySem(String studentEntrySem) {
        this.studentEntrySem = studentEntrySem;
    }

    public String getStudentClassification() {
        return studentClassification;
    }

    public void setStudentClassification(String studentClassification) {
        this.studentClassification = studentClassification;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getProvinceOrigin() {
        return provinceOrigin;
    }

    public void setProvinceOrigin(String provinceOrigin) {
        this.provinceOrigin = provinceOrigin;
    }

    // Contact Information Tab
    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    // Getters and Setters for new Contact Information Tab fields
    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getGuardianJob() {
        return guardianJob;
    }

    public void setGuardianJob(String guardianJob) {
        this.guardianJob = guardianJob;
    }

    // In Case of Emergency Tab
    public String getEmergencyContactPerson() {
        return emergencyContactPerson;
    }

    public void setEmergencyContactPerson(String emergencyContactPerson) {
        this.emergencyContactPerson = emergencyContactPerson;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getEmergencyContactNumber() {
        return emergencyContactNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public String getEmergencyContactAddress() {
        return emergencyContactAddress;
    }

    public void setEmergencyContactAddress(String emergencyContactAddress) {
        this.emergencyContactAddress = emergencyContactAddress;
    }

    // Getter and Setter for new Emergency Contact Tab field
    public String getEmergencyHomePhoneNumber() {
        return emergencyHomePhoneNumber;
    }

    public void setEmergencyHomePhoneNumber(String emergencyHomePhoneNumber) {
        this.emergencyHomePhoneNumber = emergencyHomePhoneNumber;
    }

    // Other Information Tab
    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getTalents() {
        return talents;
    }

    public void setTalents(String talents) {
        this.talents = talents;
    }

    public String getAwardsAchieved() {
        return awardsAchieved;
    }

    public void setAwardsAchieved(String awardsAchieved) {
        this.awardsAchieved = awardsAchieved;
    }

    public String getOrganizationsJoined() {
        return organizationsJoined;
    }

    public void setOrganizationsJoined(String organizationsJoined) {
        this.organizationsJoined = organizationsJoined;
    }

    // Getters and Setters for new Other Information Tab fields
    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getIndigenousGroup() {
        return indigenousGroup;
    }

    public void setIndigenousGroup(String indigenousGroup) {
        this.indigenousGroup = indigenousGroup;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }
} 