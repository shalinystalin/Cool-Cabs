<%-- 
    Document   : Register
    Created on : 12 Apr, 2016, 7:04:26 PM
    Author     : Vivek
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>CoolCabs - New Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600' rel='stylesheet' type='text/css'>
<link href="css/login-registration-style.css" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery-2.2.3.js"></script>

<script type="text/javascript">
function validateForm()
{
    if(document.getElementById("question1").value==="question1")
    {
      alert("Please choose a Question (i) from the drop down menu. .");
      document.getElementById("question1").focus();
      return false;
    }
    if(document.getElementById("question2").value==="question2")
    {
      alert("Please choose a Question (ii) from the drop down menu. .");
      document.getElementById("question2").focus();
      return false;
    }
    if(document.getElementById("question2").value===document.getElementById("question1").value)
    {
      alert("Question 1 and 2 can not be the same. Please choose a different question.");
      document.getElementById("question2").focus();
      return false;
    }
}
</script>

</head>
<body>
    <!----------start sign_up----------->
    <div class="sign_up">
            <!----------start form----------->
            <form class="sign" action="ValidateRegistration" method="post" onSubmit="return validateForm()">
                    <div class="formtitle" > Sign Up-It's free.</div>
                    <!----------start top_section----------->
                    <div class="top_section">
                            <div class="section">
                                    <div class="input-sign username">
                                        <input type="text" name="username" id="username" placeholder="User Name" 
                                               pattern="^[a-zA-Z0-9]+$" title="Username must contain only letters and numbers"  /> 
                                    </div>
                                    <div class="input-sign password">
                                        <input type="password" name="password" id="password" placeholder="Password" 
                                               pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" 
                                               title="Password must contain at least eight characters including at least one lowercase letter, uppercase letter and a number" 
                                                />
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                    </div>
                    <!----------end top_section----------->
                    <!----------start personal Details----------->
                    <!----------start bottom-section----------->
                    <div class="bottom-section">
                            <div class="title">Personal Details</div>
                            <!----------start name section----------->
                            <div class="section">
                                    <div class="input-sign details">
                                            <input type="text" name="firstname" id="firstname" placeholder="First Name" />
                                    </div>
                                    <div class="input-sign details1">
                                            <input type="text" name="lastname" id="lastname"  placeholder="Last Name" />
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <!----------start email & DOB section----------->
                            <div class="section">
                                    <div class="input-sign details email">
                                            <input type="email" name="email" id="email" placeholder="Email" /> 
                                    </div>
                                    <div class="input-sign details1 date-picker">
                                        <input placeholder="Date of Birth" class="textbox-n" type="text" onfocus="(this.type='date')" name="dob" id="dob">
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <!----------start Address (i) section----------->
                            <div class="section">
                                    <div class="input-sign details">
                                            <input type="tel" name="phone" id="phone" placeholder="Phone" /> 
                                    </div>
                                    <div class="input-sign details1">
                                            <input type="text" name="address" id="address" placeholder="Address" /> 
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <!----------start Address (ii) section----------->
                            <div class="section">
                                    <div class="input-sign details">
                                            <input type="text" name="city" id="city" placeholder="City" /> 
                                    </div>
                                    <div class="input-sign details1">
                                            <input type="text" name="province" id="province" placeholder="Province" /> 
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <!----------start security questions section----------->
                            <div class="title">Security Questions</div>
                            <div class="section-country section">
                                    <div class="input-sign details">
                                            <select id="question1" name="question1" class="frm-field" >
                                                <option value="question1">Question (i)</option>         
                                                <option value="What primary school did you attend?">What primary school did you attend?</option>
                                                <option value="What was the house number you lived in as a child?">What was the house number you lived in as a child?</option>
                                                <option value="What are the last five digits of your driver&#39;s licence number?">What are the last five digits of your driver's licence number?</option>
                                                <option value="In what town or city did your mother and father meet?">In what town or city did your mother and father meet?</option>
                                                <option value="What time of the day were you born? (hh:mm)">What time of the day were you born? (hh:mm)</option>
                                                <option value="What was your first pet&#39;s name?">What was your first pet’s name?</option>
                                                <option value="What is your favorite food?">What is your favorite food?</option>
                                                <option value="What is your mother&#39;s maiden name?">What is your mother’s maiden name?</option>
                                            </select>
                                    </div>
                                    <div class="input-sign details1 answers">
                                        <input type="text" name="answer1" id="answer1" placeholder="Answer (i)" /> 
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <div class="section-country section">
                                    <div class="input-sign details">
                                            <select id="question2" name="question2" class="frm-field" >
                                                <option value="question2">Question (ii)</option>         
                                                <option value="What primary school did you attend?">What primary school did you attend?</option>
                                                <option value="What was the house number you lived in as a child?">What was the house number you lived in as a child?</option>
                                                <option value="What are the last five digits of your driver&#39;s licence number?">What are the last five digits of your driver's licence number?</option>
                                                <option value="In what town or city did your mother and father meet?">In what town or city did your mother and father meet?</option>
                                                <option value="What time of the day were you born? (hh:mm)">What time of the day were you born? (hh:mm)</option>
                                                <option value="What was your first pet&#39;s name?">What was your first pet’s name?</option>
                                                <option value="What is your favorite food?">What is your favorite food?</option>
                                                <option value="What is your mother&#39;s maiden name?">What is your mother’s maiden name?</option>
                                            </select>
                                    </div>
                                    <div class="input-sign details1">
                                        <input type="text" name="answer2" id="answer2" placeholder="Answer (ii)" /> 
                                    </div>
                                    <div class="clear"> </div>
                            </div>
                            <div class="submit">
                                <input class="bluebutton submitbotton" type="submit" id="submit" value="Register" />
                            </div>
                            <div class="section">
                                <p style="font-size: 14px; text-align: center; padding-bottom:10px;">
                                    Already have an account? <a href="Login.jsp" style="font-weight:bold">Login here</a> 
                                </p>
                            </div>
                    </div>
                    <!----------end bottom-section----------->
            </form>
            
    <!----------end form----------->
    </div>
</body>
</html>