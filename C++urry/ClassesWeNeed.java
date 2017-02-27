package Model {
    class User {
        private String user, pass, email;
        private UserType type;
        private boolean banned = false;
        private int id;
        private Profile profile;

        public User(String user, String pass, String email) {
            this.user = user;
            this.pass = pass;
            if (email == "" || email == null) {
                email = user + "@emails.com";
            }
            this.email = email;
            this.banned = false;
            type = UserType.USER;
            setID();
            setProfile();
        }
        public String getUser() {
            return user;
        }
        public void setUser(String newuser) {
            if (sql.contains(newUser)) {
                throw new Exception("username is taken");
            }
            this.user = newuser;
        }
        public void setEmail(String newEmail) {
            if (newEmail.indexOf("@") < 0) {
                throw new Exception("invalid email");
            }
            this.email = newEmail;
        }
        public String getPass() {
            return pass;
        }
        public String getEmail() {
            return email;
        }
        public UserType getType() {
            return type;
        }
        public boolean isBanned() {
            return banned;
        }
        public void setType(UserType newType)  {
            type = newType;
        }
        public void setBan(boolean status) {
            banned = status;
        }
        public void generateReport() {
            if (banned) {
                throw new BannedUserException(user + " is banned from generating reports");
            }
            Report report = new Report(user);
            report.setText(/* some method to make a report*/);
            firebase.add(report);
        }
        public void viewReports(){
            List<Report> reports = firebase.getAsList("reports");
            View reportView = new View(/* code to show the list*/);
        }
        public void editReport(Report report) {
            report.editText(/*code to edit report*/);
            firebase.edit(report);
        }

        public void setID() {
            id = user.hashCode() *  100;
            id += (pass.hashCode() * 100);
            id += (email.hashCode() * 100);
            id += (type.value().toString().hashCode());
        }
        public int getID() {
            return id;
        }
        public void makeProfile() {
            profile = new Profile(this);
        }
        public Profile getProfile() {
            return profile;
        }
        //security log stuff, needs to be added to every method of every
        //subclass of User and needs to detail what happens and add it to the security log
        public void addEvent(String message) {
            firebase.appendTo("Security Log", message);
        }

    }

    class Worker extends User {
        public Worker(String user, String pass, String email) {
            super(user, pass, email);
            setType(WORKER);
            setID();
        }
        public void generatePurityReport() {
            Report report = new PurityReport(user);
            report.setText(/* some method to make a report*/);
            firebase.add(report);
        }
    }

    class Manager extends Worker {
        public Manager(String user, String pass, String email) {
            super(user, pass, email);
            setType(MANAGER);
            setID();
        }
        public void removeReport(Report report) {
            firebase.removeByID(report.getID());
        }
        public void viewHistoricalReports() {
            List<Report> historicalReports = firebase.getAsList("historical_reports");
            View reportView = new View(/* code to show the list*/);
        }
        public void viewPurityTrends() {
            List<Report> purityTrends = firebase.getAsList("purity_trends");
            View trendView = new View(/* code to show the list*/);
        }
    }

    class Admin extends Manager {
        public Worker(String user, String pass, String email) {
            super(user, pass, email);
            setType(ADMIN);
            setID();
        }
        public void deleteAccount(User account) {
            firebase.removeByID(account.getID());
        }
        public void banUser(User user) {
            user.setBan(true);
        }
        public void unBanUser(User user) {
            user.setBan(false);
        }
        public void viewSecurityLog() {
            File log = new File(firebase.getSecurityLog());
            View logView = new View(/* code to show log*/);
        }

    }




    class Report {
        public File backingFile;
        public String user, info;

        public Report(String user) {
            this.user = user;
        }
        public void setMessage(String newMessage) {
            info = newMessage;
        }
        public String getMessage() {
            return info;
        }
        public void generateReport() {
            backingFile.println(user);
            backingFile.println(info);
        }
    }

    class PurityReport extends Report {
        public PurityReport(String user) {
            super(user);
        }
    }
    class HistoricalReport extends Report {
        public HistoricalReport(String user) {
            super(user);
        }
    }
    class PurityTrends {
        String worker;
        public PurityTrends(String worker) {
            this.worker = worker;
        }
    }



    class Profile {
        String user, name, email, type, address;
        boolean banned;
        User person;
        public Profile(User person) {
            this.person = person;
            this.user = person.getUser();
            this.name = user;
            this.email = person.getEmail();
            this.type = person.getType();
            this.banned = person.isBanned();
        }
        public boolean edit(String newuser, newname, newemail) {
            try {
                person.setUser(newuser);
                person.setEmail(newEmail);
            } catch (Exception e) {
                toast(exception.getMessage());
                return false;
            }
            this.user = person.getUser();
            this.name = user;
            this.email = person.getEmail();
            return true;
        }

    }

    class BackEnd {
        HashMap<String, User> users;

        public BackEnd() {
            users = new HashMap<String, User>;
        }
        void addUser(User user) {
            users.add(user.getUser(), user);
        }
    }

    class Map {
        //uses google maps api to generate and populate the map with pins etc
    }
}

package Controller {
    class WelcomeActivity extends Activity {

    }
    class LandingActivity extends Activity {

    }
    class LogViewActivity extends Activity {

    }
    class EditProfileActivity extends Activity {

    }
    class MapActivity extends Activity {

    }
}



package View {
    View MapView;

}