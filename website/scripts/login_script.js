async function login_request() {
    try{
        const response = await axios.post('https://stord.tech/api/login', {
            username: document.getElementById('username_txtbox').value,
            password: document.getElementById('password_txtbox').value
        });
        if (response.data.id != null) {
            document.cookie = "user_token=" + response.data.id;
            location.href = "actions_list.html";

        } else {
            alert("Invalid username or password");
        }
    } catch (error) {
        console.log(error);
    }
}

document.getElementById("login_btn").addEventListener("click", function() {
    login_request();
});