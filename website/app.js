const menu = document.querySelector('#mobile-menu');
const menuLinks = document.querySelector('.navbar_menu');
const home_link = document.querySelector('#home_link');
const home_containter = document.querySelector('#home');
const download_link = document.querySelector('#download_link');
const download_containter = document.querySelector('#download');
const howtouse_link = document.querySelector('#howtouse_link');
const howtouse_containter = document.querySelector('#howtouse');


$(document).ready(function() {
    menu.addEventListener('click', function() {
        menu.classList.toggle('is-active');
        menuLinks.classList.toggle('active');
    });

    home_link.addEventListener('click', function() {
        $('html, body').animate({scrollTop: home_containter.offsetTop - 50}, "fast");
    });

    download_link.addEventListener('click', function() {
        $('html, body').animate({scrollTop: download_containter.offsetTop - 50}, "fast");
    });

    howtouse_link.addEventListener('click', function() {
        $('html, body').animate({scrollTop: howtouse_containter.offsetTop - 50}, "fast");
    });
});