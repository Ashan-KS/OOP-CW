import java.util.*;

public class ArticleCategorizer {

    // Define keywords for each category
    private static final Map<String, List<String>> CATEGORY_KEYWORDS = new HashMap<>() {{
        put("Politics", Arrays.asList(
                "election", "senate", "president", "congress", "policy", "law",
                "government", "democracy", "campaign", "vote", "voter",
                "legislation", "politician", "political party", "presidential", "debate",
                "reform", "policy change", "lawmaker", "scandal", "government shutdown",
                "impeachment", "constitution", "tax", "immigration", "foreign policy",
                "rights", "security", "public opinion", "poll", "democratic",
                "republican", "court ruling", "judiciary", "administration",
                "executive order", "climate policy", "state of the union", "bipartisan",
                "political analysis", "activism", "legislator", "international relations",
                "human rights", "corruption", "political crisis"
        ));
        put("Entertainment", Arrays.asList(
                "movie", "film", "music", "celebrity", "show", "series",
                "concert", "award", "festival", "hollywood", "actor",
                "actress", "director", "album", "song", "television",
                "tv", "theater", "comedy", "drama", "blockbuster",
                "performance", "streaming", "media", "artist", "reality show",
                "entertainment news", "broadcast", "red carpet", "oscars",
                "emmy", "grammy", "broadway", "box office"
        ));
        put("Business", Arrays.asList(
                "business", "economy", "market", "company", "stock", "trade",
                "investment", "finance", "merger", "acquisition", "startup",
                "entrepreneur", "industry", "corporate", "revenue", "profit",
                "loss", "shares", "IPO", "growth", "banking", "financial",
                "innovation", "enterprise", "capital", "venture", "sales",
                "retail", "commerce", "bank", "economic", "forecast",
                "inflation", "jobs", "employment", "labor market"
        ));
        put("Technology", Arrays.asList(
                "tech", "software", "internet", "AI", "blockchain", "computing",
                "artificial intelligence", "machine learning", "cloud", "data",
                "cybersecurity", "robotics", "automation", "network", "5G",
                "innovation", "digital", "IoT", "apps", "startup",
                "gadget", "smart", "programming", "developer", "technology",
                "virtual reality", "augmented reality", "big data", "database",
                "e-commerce", "devops", "tech company", "smartphone", "wearables",
                "cryptocurrency", "quantum computing"
        ));
        put("Comedy", Arrays.asList(
                "funny", "joke", "laugh", "comedy", "humor",
                "satire", "sketch", "stand-up", "parody", "comedian",
                "roast", "punchline", "improv", "mock", "spoof",
                "sarcasm", "banter", "wit", "humorous", "laugh-out-loud",
                "hilarious", "silly", "goof", "prank", "meme",
                "viral", "cartoon", "spoof", "funniest", "laughing",
                "comic", "skit", "fun", "entertainment", "late-night"
        ));
        put("Sports", Arrays.asList(
                "football", "basketball", "tennis", "Olympics", "soccer",
                "baseball", "hockey", "athlete", "world cup", "championship",
                "league", "match", "competition", "NBA", "NFL",
                "MLB", "FIFA", "rugby", "golf", "boxing",
                "swimming", "track", "marathon", "cycling", "wrestling",
                "sportsmanship", "medal", "victory", "team", "coach",
                "referee", "goal", "tournament", "all-star", "finals",
                "playoffs", "stadium", "sports news", "fitness", "training"
        ));
        put("Travel", Arrays.asList(
                "vacation", "holiday", "trip", "destination", "tourism", "adventure",
                "flight", "hotel", "resort", "explore", "road trip",
                "travel guide", "backpacking", "tour", "sightseeing", "holidaymaker",
                "cruise", "beach", "mountain", "hiking", "holiday destination",
                "itinerary", "travel tips", "wanderlust", "journey", "staycation",
                "passport", "tourist", "travel blog", "trip planning", "local culture",
                "sustainable travel", "airline", "excursion", "travel experience"
        ));
        put("Environment", Arrays.asList(
                "climate change", "global warming", "sustainability", "pollution", "ecosystem",
                "greenhouse gases", "carbon footprint", "conservation", "biodiversity", "renewable energy",
                "recycling", "environmental impact", "deforestation", "wildlife", "water conservation",
                "carbon emissions", "energy efficiency", "climate action", "natural resources", "clean energy",
                "fossil fuels", "environmental protection", "green energy", "earth", "air quality",
                "global crisis", "climate policy", "extinction", "greenhouse effect", "eco-friendly",
                "ecology", "habitat destruction", "carbon neutral", "environmental justice"
        ));
        put("Style & Beauty", Arrays.asList(
                "fashion", "beauty", "style", "makeup", "skincare", "hair", "trend",
                "outfit", "cosmetics", "beauty tips", "accessories", "nail art",
                "hairstyle", "luxury", "runway", "fashion week", "designer",
                "clothing", "wardrobe", "self-care", "beauty products", "face mask",
                "fragrance", "hygiene", "natural beauty", "skin treatment", "menswear",
                "hairstyling", "makeup tutorial", "glam", "anti-aging", "moisturizer",
                "personal care", "fashion trends", "style icon", "body care"
        ));
        put("Crime", Arrays.asList(
                "murder", "robbery", "theft", "assault", "burglary", "crime scene",
                "police", "investigation", "suspect", "victim", "homicide",
                "arrest", "court", "trial", "jury", "warrant", "fraud",
                "drug trafficking", "kidnapping", "violence", "domestic violence",
                "shooting", "gang", "prosecution", "defense", "conviction",
                "sentencing", "criminal justice", "prison", "detention", "lawsuit",
                "cybercrime", "terrorism", "extortion", "corruption", "forensic",
                "police department", "law enforcement", "crime wave", "robbery spree",
                "missing person", "drug bust", "organized crime", "hate crime"
        ));
    }};

    private static final List<String> STOP_WORDS = Arrays.asList("the", "is", "at", "which", "on", "a", "an", "of", "and", "in");

    // Preprocess text by removing stop words and tokenizing
    public static List<String> preprocessText(String content) {
        String[] tokens = content.toLowerCase().split("\\W+");
        List<String> processedTokens = new ArrayList<>();

        for (String token : tokens) {
            if (!STOP_WORDS.contains(token) && !token.isEmpty()) {
                processedTokens.add(token);
            }
        }
        return processedTokens;
    }

    // Categorize the article based on keywords in the headline and short description
    public static String categorizeArticle(String headline, String shortDescription) {
        String combinedText = headline + " " + shortDescription;
        List<String> tokens = preprocessText(combinedText);

        // Tally matches for each category
        Map<String, Integer> categoryScores = new HashMap<>();
        for (String token : tokens) {
            for (Map.Entry<String, List<String>> entry : CATEGORY_KEYWORDS.entrySet()) {
                String category = entry.getKey();
                List<String> keywords = entry.getValue();
                if (keywords.contains(token)) {
                    categoryScores.put(category, categoryScores.getOrDefault(category, 0) + 1);
                }
            }
        }

        // Find category with the highest score
        return categoryScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Uncategorized");
    }

    public static void main(String[] args) {
        // Example articles
        String[][] articles = {
                {"Seth Meyers Has A Field Day With Rudy Giuliani's 'Diet Pepsi' Claim", "“Sorry, buddy. You just gave yourself away. No one’s favorite drink is Diet Pepsi,” the 'Late Night' host said.", "Josephine Harvey"},
                {"25 Of The Funniest Tweets About Cats And Dogs This Week (June 4-10)", "“i keep hearing this ad for fresh cat food that says 'your cute kitty is descended from fierce desert cats' and then i look at my cat and say 'this cat??'”", "Elyse Wanshel"},
                {"REI Workers At Berkeley Store Vote To Unionize In Another Win For Labor", "They follow in the footsteps of REI workers in New York City who formed a union earlier this year.", "Dave Jamieson"},
                {"Twitter Lawyer Calls Elon Musk 'Committed Enemy' As Judge Sets October Trial", "Delaware Chancery Judge Kathaleen McCormick dealt the world's richest person a setback in ordering a speedy trial on his abandoned deal to buy Twitter.", "Marita Vlachou"}
        };

        // Process each article
        for (String[] article : articles) {
            String headline = article[0];
            String shortDescription = article[1];
            String category = categorizeArticle(headline, shortDescription);
            System.out.println("Headline: " + headline);
            System.out.println("Short Description: " + shortDescription);
            System.out.println("Predicted Category: " + category);
            System.out.println("-------------");
        }
    }
}