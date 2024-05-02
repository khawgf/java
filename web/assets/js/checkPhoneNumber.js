function checkLogin() {
            var phoneNumber = document.getElementById("PhoneNumber").value;
            var errorMessage = document.getElementById("errorMessage");

            if (isNaN(phoneNumber) || phoneNumber.length !== 10) {
                errorMessage.innerHTML = "Số điện thoại không hợp lệ";
                return false;
            }
        }