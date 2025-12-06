	<%@ page language="java" %>
	<!DOCTYPE html>
	<html>
	<head>
	    <title>Chatbot</title>
	    <style>
	        #chatbot-toggle-btn {
	            position: fixed;
	            bottom: 20px;
	            right: 20px;
	            width: 60px;
	            height: 60px;
	            background-color: #ff5757;
	            color: white;
	            border: none;
	            border-radius: 50%;
	            box-shadow: 0 0 10px rgba(0,0,0,0.3);
	            font-size: 24px;
	            cursor: pointer;
	            z-index: 9999;
	        }
	
	        #chatbot-container {
	            position: fixed;
	            bottom: 90px;
	            right: 20px;
	            width: 320px;
	            max-height: 500px;
	            background: white;
	            border-radius: 12px;
	            box-shadow: 0 0 10px rgba(0,0,0,0.2);
	            overflow: hidden;
	            display: none;
	            flex-direction: column;
	            z-index: 9998;
	            font-family: Arial, sans-serif;
	        }
	
	        #chatbot-header {
	            background: #ff5757;
	            color: white;
	            padding: 10px;
	            text-align: center;
	            font-weight: bold;
	        }
	
	        #chatbot-messages {
	            flex: 1;
	            padding: 10px;
	            overflow-y: auto;
	            font-size: 14px;
	            height: 300px;
	        }
	
	        .chatbot-message {
	            margin: 6px 0;
	        }
	
	        .chatbot-user {
	            text-align: right;
	            color: #2c3e50;
	        }
	
	        .chatbot-bot {
	            text-align: left;
	            color: #27ae60;
	        }
	
	        #chatbot-input {
	            display: flex;
	            border-top: 1px solid #ccc;
	        }
	
	        #chatbot-input input {
	            flex: 1;
	            padding: 8px;
	            border: none;
	            outline: none;
	            font-size: 14px;
	        }
	
	        #chatbot-input button {
	            background: #ff5757;
	            color: white;
	            border: none;
	            padding: 8px 12px;
	            cursor: pointer;
	        }
	    </style>
	</head>
	<body>
	
	<!-- Floating Button -->
	<button id="chatbot-toggle-btn" onclick="toggleChatbot()">ai</button>
	
	<!-- Chatbot Window -->
	<div id="chatbot-container">
	    <div id="chatbot-header">Ask Disneyland Bot</div>
	    <div id="chatbot-messages"></div>
	    <div id="chatbot-input">
	        <input type="text" id="userInput" placeholder="Ask something..." />
	        <button onclick="sendMessage()">Send</button>
	    </div>
	</div>
	
	<script>
	    function toggleChatbot() {
	        const container = document.getElementById("chatbot-container");
	        container.style.display = (container.style.display === "none" || container.style.display === "") ? "flex" : "none";
	    }
	
	    function appendMessage(message, type) {
	        const div = document.createElement("div");
	        div.className = "chatbot-message chatbot-" + type;
	        div.innerText = message;
	        document.getElementById("chatbot-messages").appendChild(div);
	        document.getElementById("chatbot-messages").scrollTop = document.getElementById("chatbot-messages").scrollHeight;
	    }
	
	    function sendMessage() {
	        const input = document.getElementById("userInput");
	        const message = input.value.trim();
	        if (!message) return;
	        appendMessage(message, "user");
	        input.value = "";
	
	        fetch("ChatServlet", {
	            method: "POST",
	            headers: { "Content-Type": "application/x-www-form-urlencoded" },
	            body: "message=" + encodeURIComponent(message)
	        })
	        .then(res => res.text())
	        .then(reply => appendMessage(reply, "bot"))
	        .catch(err => appendMessage("Oops! Something went wrong.", "bot"));
	    }
	</script>
	</body>
	</html>
