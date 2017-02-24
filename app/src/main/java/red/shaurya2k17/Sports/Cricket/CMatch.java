package red.shaurya2k17.Sports.Cricket;

/**
 * Created by reddy on 9/2/17.
 */

public class CMatch {

    private String MatchName;
    private String Team1;
    private String Team2;
    private String Team1Score;
    private String Team2Score;
    private String Winner;
    private String Overs;
    private String Comments;
    private String TossWon;
    private String TossWonPref;

    public String getTossWonPref() {
        return TossWonPref;
    }

    public void setTossWonPref(String tossWonPref) {
        TossWonPref = tossWonPref;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String matchName) {
        MatchName = matchName;
    }

    public String getTeam1() {
        return Team1;
    }

    public void setTeam1(String team1) {
        Team1 = team1;
    }

    public String getTeam2() {
        return Team2;
    }

    public void setTeam2(String team2) {
        Team2 = team2;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public String getTeam1Score() {
        return Team1Score;
    }

    public void setTeam1Score(String team1Score) {
        Team1Score = team1Score;
    }

    public String getTeam2Score() {
        return Team2Score;
    }

    public void setTeam2Score(String team2Score) {
        Team2Score = team2Score;
    }

    public String getOvers() {
        return Overs;
    }

    public void setOvers(String overs) {
        Overs = overs;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getTossWon() {
        return TossWon;
    }

    public void setTossWon(String tossWon) {
        TossWon = tossWon;
    }
}
