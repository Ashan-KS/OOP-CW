package Article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ArticleCategorizer {
    private static final Map<String, List<String>> CATEGORY_KEYWORDS = new HashMap<String, List<String>>() {
        {
            this.put("Comedy", Arrays.asList("funniest", "tweets", "cats", "dogs", "this", "week", "sept", "17", "23", "until", "you", "have", "a", "dog", "don't", "understand", "what", "could", "be", "eaten", "elyse", "wanshel", "comedy", "july", "16", "22", "ever", "bring", "ur", "pet", "up", "to", "mirror", "like", "that's", "you", "petition", "stop", "ringing", "doorbell", "on", "tv", "so", "my", "lead", "less", "confusing", "life", "seth", "meyers", "has", "field", "day", "with", "rudy", "giuliani's", "diet", "pepsi", "claim", "sorry", "buddy", "just", "gave", "yourself", "away", "no", "one’s", "favorite", "drink", "is", "diet", "pepsi", "late", "night", "host", "said", "fresh", "cat", "food", "says", "your", "cute", "kitty", "descended", "from", "fierce", "desert", "then", "look", "at", "and", "say", "this"));
            this.put("Business", Arrays.asList("rei", "workers", "berkeley", "store", "vote", "to", "unionize", "in", "another", "win", "for", "labor", "follow", "the", "footsteps", "of", "new", "york", "city", "who", "formed", "a", "union", "earlier", "this", "year", "dave", "jamieson", "business", "twitter", "lawyer", "calls", "elon", "musk", "committed", "enemy", "as", "judge", "sets", "october", "trial", "delaware", "chancery", "kathaleen", "mccormick", "dealt", "world's", "richest", "person", "setback", "ordering", "speedy", "his", "abandoned", "deal", "buy", "starbucks", "leaving", "russian", "market", "shutting", "130", "stores", "move", "follows", "mcdonald's", "exit", "from", "last", "week", "dee-ann", "durbin", "ap", "crypto", "crash", "leaves", "trading", "platform", "coinbase", "slumped", "cryptocurrency", "has", "lost", "half", "its", "value", "past", "matt", "ott", "us", "added", "428,000", "jobs", "april", "despite", "surging", "inflation", "at", "3.6%", "unemployment", "nearly", "reached", "lowest", "level", "half", "century", "paul", "wiseman"));
            this.put("Sports", Arrays.asList("maury", "wills", "base-stealing", "shortstop", "for", "dodgers", "dies", "at", "89", "who", "helped", "los", "angeles", "win", "three", "world", "series", "titles", "with", "his", "prowess", "has", "died", "beth", "harris", "ap", "las", "vegas", "aces", "first", "wnba", "title", "chelsea", "gray", "named", "mvp", "never", "had", "a", "professional", "champion", "until", "sunday", "pat", "eaton-robb", "boston", "marathon", "make", "race", "more", "inclusive", "nonbinary", "runners", "organizers", "say", "athletes", "won't", "have", "to", "register", "men's", "or", "women's", "divisions", "and", "provided", "qualifying", "times", "guide", "their", "training", "sanjana", "karanth", "anthony", "varvaro", "mlb", "pitcher", "turned", "transit", "cop", "dies", "in", "crash", "on", "way", "9/11", "ceremony", "pitched", "mostly", "atlanta", "braves", "started", "law", "enforcement", "career", "2016", "carlos", "alcaraz", "u.s.", "open", "1st", "slam", "title", "top", "ranking", "defeated", "casper", "ruud", "final", "earn", "grand", "slam", "age", "19", "become", "youngest", "man", "move", "up", "no", "1", "rankings", "howard", "fendrich"));
            this.put("Travel", Arrays.asList("lovely", "honeymoon", "destinations", "in", "u.s.", "newlyweds", "don't", "have", "to", "travel", "too", "far", "for", "a", "relaxing", "scenic", "vacation", "caroline", "bologna", "how", "salvage", "your", "if", "it", "rains", "most", "of", "the", "time", "experts", "share", "their", "advice", "finding", "bright", "side", "gloomy", "weather", "definitive", "guide", "airplane", "seat", "etiquette", "explain", "what", "you", "should", "know", "about", "reclining", "sharing", "armrests", "and", "more", "rudest", "things", "can", "do", "at", "hotel", "faux", "pas", "avoid", "while", "staying", "especially", "during", "pandemic", "passport", "wait", "times", "are", "awful", "right", "now", "here's", "what", "make", "renewal", "process", "less", "stressful"));
            this.put("Environment", Arrays.asList("first", "public", "global", "database", "fossil", "fuels", "launches", "world's", "production", "reserves", "emissions", "drew", "costley", "ap", "alaska", "prepares", "historic-level", "storm", "barreling", "towards", "coast", "10", "years", "people", "will", "referring", "september", "2022", "benchmark", "becky", "bohrer", "mark", "thiessen", "john", "antczak", "puerto", "rico", "braces", "landslides", "severe", "flooding", "tropical", "storm", "fiona", "approaches", "under", "hurricane", "watch", "saturday", "barreled", "u.s.", "territory", "dánica", "coto", "privatization", "isn’t", "answer", "jackson’s", "water", "crisis", "studies", "have", "repeatedly", "shown", "ending", "public", "administration", "supplies", "doesn’t", "work", "but", "now", "on", "table", "mississippi", "nathalie", "baptiste", "severe", "winds", "batter", "southern", "california", "heat", "wave", "breaks", "after", "10-day", "that", "nearly", "overwhelmed", "electrical", "grid", "cooler", "weather", "also", "ferocious", "tropical", "julie", "watson", "john", "antczak"));
            this.put("Technology", Arrays.asList("twitch", "bans", "gambling", "sites", "streamer", "scams", "folks", "out", "$200,000", "one", "man's", "claims", "he", "scammed", "people", "on", "platform", "caused", "several", "popular", "streamers", "consider", "boycott", "ben", "blanchet", "tiktok", "search", "results", "riddled", "with", "misinformation", "report", "u.s.", "firm", "monitors", "false", "online", "claims", "reports", "searches", "for", "information", "about", "prominent", "news", "topics", "david", "klepper", "ap", "cloudflare", "drops", "hate", "site", "kiwi", "farms", "ceo", "matthew", "prince", "had", "previously", "resisted", "calls", "block", "associated", "press", "instagram", "and", "facebook", "remove", "posts", "offering", "abortion", "pills", "began", "removing", "some", "these", "just", "as", "millions", "across", "searching", "clarity", "around", "access", "amanda", "seitz", "google", "engineer", "leave", "after", "he", "claims", "ai", "program", "has", "gone", "sentient", "artificially", "intelligent", "chatbot", "generator", "lamda", "wants", "to", "be", "acknowledged", "an", "employee", "rather", "than", "property", "says", "blake", "lemoine", "mary", "papenfuss"));
            this.put("Entertainment", Arrays.asList("golden", "globes", "returning", "to", "nbc", "in", "january", "after", "year", "off-air", "past", "18", "months", "hollywood", "effectively", "boycotted", "hfpa", "87", "members", "non-american", "journalists", "included", "no", "black", "james", "cameron", "says", "he", "clashed", "with", "studio", "before", "'avatar'", "release", "director", "aspects", "his", "2009", "movie", "still", "competitive", "everything", "out", "there", "these", "days", "ben", "blanchet", "amazon", "greenlights", "'blade", "runner", "2099'", "limited", "series", "produced", "ridley", "scott", "director", "original", "1982", "film", "joins", "writer", "2017", "sequel", "newest", "installment", "sci-fi", "franchise", "marco", "margaritoff", "'phantom", "of", "opera'", "close", "broadway", "next", "year", "longest-running", "show", "scheduled", "february", "2023", "victim", "post-pandemic", "softening", "theater", "attendance", "new", "york", "mark", "kennedy", "ap", "viola", "davis", "feared", "heart", "attack", "during", "'woman", "king'", "training", "oscar", "winner", "said", "she", "worked", "out", "five", "hours", "a", "day", "role", "action", "movie"));
            this.put("Politics", Arrays.asList("biden", "says", "us", "forces", "would", "defend", "taiwan", "if", "china", "invaded", "president", "issues", "vow", "tensions", "rise", "beautiful", "and", "sad", "at", "the", "same", "time", "ukrainian", "cultural", "festival", "takes", "on", "deeper", "meaning", "this", "year", "annual", "celebration", "different", "feel", "russia's", "invasion", "day", "206", "jonathan", "nicholson", "queen's", "death", "left", "giant", "hole", "for", "royal", "family", "us", "president", "joe", "biden", "in", "london", "funeral", "queen", "elizabeth", "ii", "says", "heart", "went", "out", "to", "adding", "death", "giant", "hole", "darlene", "superville", "ap", "bill", "help", "afghans", "escaped", "taliban", "faces", "long", "odds", "senate", "republican", "outrage", "shoddy", "withdrawal", "afghanistan", "hasn't", "spurred", "support", "resettling", "refugees", "hamed", "ahmadi", "arthur", "delaney", "mark", "meadows", "complies", "justice", "dept", "subpoena", "report", "former", "white", "house", "chief", "staff", "turned", "over", "records", "part", "federal", "investigation", "jan", "6", "2021", "assault", "capitol", "eric", "tucker", "ap"));
            this.put("Crime", Arrays.asList("memphis", "police", "arrest", "made", "jogger's", "disappearance", "woman", "abducted", "while", "jogging", "tennessee", "trump", "org", "cfo", "plead", "guilty", "testify", "against", "company", "allen", "weisselberg", "charged", "taking", "more", "than", "1.7", "million", "off-the-books", "compensation", "organization", "michael", "r", "sisak", "ap", "officials", "nh", "missing", "girl", "case", "shifts", "homicide", "probe", "authorities", "search", "new", "hampshire", "disappeared", "age", "5", "2019", "reported", "late", "last", "year", "considered", "homicide", "investigation", "holly", "ramer", "ap", "albuquerque", "share", "photo", "car", "eyed", "slayings", "muslim", "men", "authorities", "four", "killings", "several", "things", "common", "looking", "see", "if", "any", "other", "crimes", "could", "related", "nina", "golgowski", "vigilant", "amid", "series", "murders", "shooter", "responsible", "string", "around", "new", "mexico", "city", "sara", "boboltz"));
            this.put("Style & Beauty", Arrays.asList("hide", "worst", "tan", "lines", "makeup", "pros", "quick", "solutions", "fix", "farmer's", "body", "self", "tanner", "julie", "kendrick", "jello", "skin", "latest", "tiktok", "trend", "achieve", "bouncy", "jiggly", "snaps", "back", "movement", "rebecca", "rovenstine", "treat", "bug", "bites", "way", "dermatologists", "home", "order", "now", "itch-free", "summer", "prevent", "chafing", "men", "ditch", "discomfort", "down", "there", "tips", "katie", "mcpherson", "mole", "looks", "like", "time", "see", "dermatologist", "experts", "signs", "melanoma", "look", "out", "skin", "kelsey", "borresen", "atypical", "recommendations", "care", "treatment"));
        }
    };
    private static final List<String> STOP_WORDS = Arrays.asList("the", "is", "at", "which", "on", "a", "an", "of", "and", "in");

    public ArticleCategorizer() {
    }

    public static List<String> preprocessText(String content) {
        String[] tokens = content.toLowerCase().split("\\W+");
        List<String> processedTokens = new ArrayList();
        String[] var3 = tokens;
        int var4 = tokens.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String token = var3[var5];
            if (!STOP_WORDS.contains(token) && !token.isEmpty()) {
                processedTokens.add(token);
            }
        }

        return processedTokens;
    }

    public static String categorizeArticle(String headline, String shortDescription) {
        String combinedText = headline + " " + shortDescription;
        List<String> tokens = preprocessText(combinedText);
        Map<String, Integer> categoryScores = new HashMap();
        Iterator var5 = tokens.iterator();

        while(var5.hasNext()) {
            String token = (String)var5.next();
            Iterator var7 = CATEGORY_KEYWORDS.entrySet().iterator();

            while(var7.hasNext()) {
                Map.Entry<String, List<String>> entry = (Map.Entry)var7.next();
                String category = (String)entry.getKey();
                List<String> keywords = (List)entry.getValue();
                if (keywords.contains(token)) {
                    categoryScores.put(category, (Integer)categoryScores.getOrDefault(category, 0) + 1);
                }
            }
        }

        return (String)categoryScores.entrySet().stream().max(Entry.comparingByValue()).map(Map.Entry::getKey).orElse("Uncategorized");
    }

    public void Categorize() {
        String url = "jdbc:sqlite:articles.db";
        String query = "SELECT articleID, headline, description FROM articles WHERE predicted = ''";
        String updateQuery = "UPDATE articles SET predicted = ? WHERE articleID = ?";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                Statement stmt = conn.createStatement();

                try {
                    ResultSet rs = stmt.executeQuery(query);

                    try {
                        PreparedStatement pstmt = conn.prepareStatement(updateQuery);

                        try {
                            while(rs.next()) {
                                int articleID = rs.getInt("articleID");
                                String headline = rs.getString("headline");
                                String shortDescription = rs.getString("description");
                                String predictedCategory = categorizeArticle(headline, shortDescription);
                                pstmt.setString(1, predictedCategory);
                                pstmt.setInt(2, articleID);
                                pstmt.executeUpdate();
                            }
                        } catch (Throwable var16) {
                            if (pstmt != null) {
                                try {
                                    pstmt.close();
                                } catch (Throwable var15) {
                                    var16.addSuppressed(var15);
                                }
                            }

                            throw var16;
                        }

                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (Throwable var17) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var14) {
                                var17.addSuppressed(var14);
                            }
                        }

                        throw var17;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var18) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var13) {
                            var18.addSuppressed(var13);
                        }
                    }

                    throw var18;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var19) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var12) {
                        var19.addSuppressed(var12);
                    }
                }

                throw var19;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var20) {
            var20.printStackTrace();
        }

    }
}
