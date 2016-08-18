/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.getElementById("editProfileButton").addEventListener("click", function (){

        document.getElementById("oldemail").style.display = "none";
        document.getElementById("email").setAttribute("type", "email");
        document.getElementById("oldphone").style.display = "none";
        document.getElementById("phone").setAttribute("type", "text");
        document.getElementById("oldaddress").style.display = "none";
        document.getElementById("address").setAttribute("type", "text");
        document.getElementById("oldcity").style.display = "none";
        document.getElementById("city").setAttribute("type", "text");
        document.getElementById("oldprovince").style.display = "none";
        document.getElementById("province").setAttribute("type", "text");
        document.getElementById("editProfileButton").setAttribute("type", "hidden");
        document.getElementById("changePasswordButton").setAttribute("type", "hidden");
        document.getElementById("editProfilePassword").setAttribute("type", "password");
        document.getElementById("saveProfileButton").setAttribute("type", "submit");

});
