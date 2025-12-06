<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<%@ page session="true" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Disneyland Attractions</title>
    <link rel="stylesheet" type="text/css" href="css/attractions.css">
    
    <!-- ✅ Override the fixed header for this page -->
    <style>
        .main-header {
            position: static !important;
            background-color: black; /* Optional: ensure it's not transparent on scroll */
        }
    </style>
</head>
<body>

<%@ include file="header.jsp" %>

<div class="attraction-page">
    <h2>✨ Explore Our Magical Attractions</h2>

    <div class="attraction-container">

        <div class="attraction-row">
            <div class="attraction-media">
                <img src="images/SleepingBeauty.jpg" alt="Castle">
            </div>
            <div class="attraction-content">
                <h3>Sleeping Beauty Castle</h3>
                <p>The Sleeping Beauty Castle at Disneyland is an enchanting fairytale icon 
                that captures the magic of dreams and imagination.
                Inspired by Neuschwanstein Castle in Germany, its pink spires and blue turrets rise gracefully above the park,
                inviting guests into a world of wonder. Opened in 1955,
                it was the first Disney castle ever built and remains a beloved symbol of Disneyland.
                Inside, visitors can walk through a storybook journey depicting scenes from Sleeping Beauty.
                Surrounded by a serene moat and drawbridge,
                the castle serves as the heart of the park and a timeless reminder that dreams really do come true.</p>
            </div>
        </div>

        <div class="attraction-row reverse">
            <div class="attraction-media">
                <video muted autoplay loop>
                    <source src="videos/parade.mp4" type="video/mp4">
                </video>
            </div>
            <div class="attraction-content">
                <h3>Fantasyland Parade</h3>
                <p>The Fantasyland Parade at Disneyland is a magical journey through the heart of your favorite Disney stories. 
                As the parade makes its way from Main Street to Fantasyland, 
                guests are transported into a world of enchantment and wonder. 
                Vibrant floats glide by, each themed after beloved tales like Cinderella,
                 Beauty and the Beast, Frozen, and Peter Pan, with characters waving and performing to the iconic 
                 songs that bring each story to life. Tinker Bell leads the procession in a sparkle of pixie dust, 
                 while Mickey and friends close the parade with a grand, joyful finale. The combination of dazzling costumes, 
                 uplifting music, and theatrical performances makes the Fantasyland Parade.</p>
            </div>
        </div>

        <div class="attraction-row">
            <div class="attraction-media">
                <img src="images/SpaceMountain.webp" alt="Space Mountain">
            </div>
            <div class="attraction-content">
                <h3>Space Mountain</h3>
                <p>Space Mountain at Magic Kingdom in Walt Disney World is an iconic indoor roller coaster that debuted on January 15, 
                1975. As the first fully computerized coaster in the world, it delivers a pioneering thrill ride through darkness, 
                with dazzling “stars,” meteors, and spatial effects lighting whipping past as you zoom along at nearly 29 mph . 
                Encased in a towering 183 foot cone in Tomorrowland, riders navigate twists, turns, and drops they can’t anticipate, 
                creating a heart pounding adventure. With two nearly identical tracks—Alpha and Omega—it’s a ride worth repeating. 
                At just over 2½ minutes long, Space Mountain challenges riders 44″ + with unforgettable cosmic excitement.</p>
            </div>
        </div>

        <div class="attraction-row reverse">
            <div class="attraction-media">
                <video muted autoplay loop>
                    <source src="videos/pirates.mp4" type="video/mp4">
                </video>
            </div>
            <div class="attraction-content">
                <h3>Pirates of the Caribbean</h3>
                <p>Pirates Dinner Adventure is one of Orange County’s most exciting family attractions, 
                offering a thrilling combination of live theater, action, and dining. 
                Set aboard an 18th-century Spanish galleon surrounded by a 250,000-gallon indoor lagoon,
                the show features sword fights, acrobatics, aerial stunts, and audience participation.
                Up to 150 guests become part of the performance, making it the “world’s most interactive dinner show.”
                Enjoy a hearty pirate feast as you’re swept into a swashbuckling tale of good versus evil, led by the infamous 
                Captain Sebastian the Black. Located just minutes from Disneyland and Knott’s Berry Farm, 
                it’s perfect for all ages!</p>
            </div>
        </div>

        <div class="attraction-row">
            <div class="attraction-media">
                <img src="images/starwars.webp" alt="Star Wars Galaxy's Edge">
            </div>
            <div class="attraction-content">
                <h3>Star Wars Galaxy’s Edge</h3>
                <p>Star Wars: Galaxy’s Edge at Disneyland is an immersive land that transports you to the planet Batuu, 
                a remote outpost on the galaxy’s edge. Guests can build their own lightsabers at Savi’s Workshop, craft droids, 
                and sample galactic cuisine at Oga’s Cantina. The land features two major attractions: Millennium Falcon: Smugglers Run, 
                where you pilot the iconic ship, and Star Wars: Rise of the Resistance, 
                an epic battle between the Resistance and the First Order. 
                With authentic Star Wars details, characters like Kylo Ren and Chewbacca, 
                and interactive adventures, it’s a must-visit destination
                 for every Star Wars fan.</p>
            </div>
        </div>

    </div>
</div>
	<%@ include file="chatbot.jsp" %>
</body>
</html>
