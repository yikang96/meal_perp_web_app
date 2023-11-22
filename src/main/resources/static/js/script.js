function userScroll() {
  const navbar = document.querySelector(".navbar");
  const toTopBtn = document.querySelector("#to-top");
  if (navbar && toTopBtn) {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 50) {
        navbar.classList.add("navbar-sticky");
        toTopBtn.classList.add("show");
      } else {
        navbar.classList.remove("navbar-sticky");
        toTopBtn.classList.remove("show");
      }
    });
  }
}

function scrollToTop() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

function incrementStats() {
  const counters = document.querySelectorAll(".counter");

  counters.forEach((counter) => {
    counter.innerText = 0;

    const updateCounter = () => {
      const target = +counter.getAttribute("data-target");
      const c = +counter.innerText;

      const increment = target / 200;

      if (c < target) {
        counter.innerText = Math.ceil(c + increment);
        setTimeout(updateCounter, 1);
      } else {
        counter.innerText = target;
      }
    };

    updateCounter();
  });
}

// edit the stars for reviews
const rating = 0;
function starsEvent(event) {
  const starsNumber = event.target.dataset?.id;
  if (!starsNumber) return;

  setStars(starsNumber);

  if (event.type === "click") {
    rating = starsNumber;
  } else if (event.type === "mouseleave") {
    setStars(rating);
  }
}

function setStars(starsNumber) {
  const activeColor = "active-star";
  const starsElement = document.querySelectorAll("#rating i");
  starsElement.forEach((value, index, array) => {
    if (index < starsNumber) {
      value.classList.add(activeColor);
      value.classList.replace("fa-regular", "fa");
    } else {
      value.classList.remove(activeColor);
      value.classList.replace("fa", "fa-regular");
    }
  });
}

//settings for the submit button in customer review form
const reviewForm = document.querySelector(".customer-review");
reviewForm?.addEventListener("submit", function (event) {
  event.preventDefault();
  alert("We have received your review!");
});

// settings for the video in the bootstrap modal
function setVideo(e) {
  const modalContent = document.querySelector(".ratio.ratio-16x9");

  if (e.type === "hide.bs.modal") {
    modalContent.innerHTML = "";
  } else {
    modalContent.innerHTML = `<iframe
      class="embed-responsive-item"
      src="https://www.youtube-nocookie.com/embed/lCldQpmCVPc?si=43Z1x4EPeZBXhdD-"
      id="video"
      allowscriptaccess="always"
      allow="autoplay"
      allowfullscreen
    ></iframe>`;
  }

  // modalContent.innerHTML = `<p>Hello</p>`;
}
document.querySelector("#play-video")?.addEventListener("click", setVideo);
document
  .querySelector("#meal-prep-tips-video")
  ?.addEventListener("hide.bs.modal", setVideo);

// select text for the dropdown menu issue-report form
const issueReportDropdownEle = document.querySelector(".issue-report-dropdown");
const issueReportDropdownItems = document.querySelectorAll(
  ".issue-report-dropdown li a"
);
issueReportDropdownItems.forEach(function (item) {
  item.addEventListener("click", function (event) {
    event.preventDefault();
    let selectText = item.textContent || item.innerText;
    let dropdownToggle =
      issueReportDropdownEle.getElementsByClassName("dropdown-toggle");
    dropdownToggle.innerHTML = selectText + ' <span class="caret"></span>';
  });
});

//submit button in issue-report form
const issueReportForm = document.querySelector(".report-issue");
issueReportForm?.addEventListener("submit", function (event) {
  event.preventDefault();
  alert(
    "We have received your message! We will contact you regarding the issue that reported soon"
  );
});

// Event Listeners
document.addEventListener("DOMContentLoaded", userScroll);
document.addEventListener("DOMContentLoaded", incrementStats);
document.querySelector("#to-top")?.addEventListener("click", scrollToTop);

const starRating = document.getElementById("rating");
if (starRating) {
  starRating.addEventListener("click", starsEvent);
  starRating.addEventListener("mouseover", starsEvent);
  starRating.addEventListener("mouseleave", () => setStars(rating));
}

const signInTogglePassword = document.getElementById("signInTogglePassword");
const signInPass = document.getElementById("signInPassword");
const signUpTogglePassword = document.getElementById("signUpTogglePassword");
const signUpPass = document.getElementById("signUpPassword");
function interchangePassToggle(passEle, toggleEle) {
  toggleEle?.addEventListener("click", function () {
    if (toggleEle.classList.contains("fa-eye-slash")) {
      toggleEle.classList.remove("fa-eye-slash");
      toggleEle.classList.add("fa-eye");
      passEle.type = "text";
    } else {
      toggleEle.classList.remove("fa-eye");
      toggleEle.classList.add("fa-eye-slash");
      passEle.type = "password";
    }
  });
}
interchangePassToggle(signInPass, signInTogglePassword);
interchangePassToggle(signUpPass, signUpTogglePassword);

const signUpForm = document.querySelector(".sign-up-form");
const signInForm = document.querySelector(".sign-in-form");
//sign up form validation
const userFirstName = document.getElementById("firstname");
const userLastName = document.getElementById("lastname");
const signUpEmail = document.querySelector(".sign-up-form #email");
const signUpConfirmPass = document.getElementById("confirm-password");

const isRequired = (value) => (value === "" ? false : true);
const isBetween = (length, min, max) =>
  length < min || length > max ? false : true;
const isEmailValid = (email) => {
  const re =
    /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
};
const isPasswordSecure = (password) => {
  const re = new RegExp(
    "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})"
  );
  return re.test(password);
};
const showError = (input, message) => {
  // get the form-field element
  const formField = input.parentElement;
  // add the error class
  formField.classList.remove("success");
  formField.classList.add("error");

  // show the error message
  const error = formField.querySelector("small");
  error.textContent = message;
};

const showSuccess = (input) => {
  // get the form-field element
  const formField = input.parentElement;

  // remove the error class
  formField.classList.remove("error");
  formField.classList.add("success");

  // hide the error message
  const error = formField.querySelector("small");
  error.textContent = "";
};

function validateInput(inputEle, min, max) {
  const value = inputEle.value.trim();
  let valid = false;

  if (!isRequired(value)) {
    showError(inputEle, `${inputEle.name} cannot be blank.`);
  } else if (!isBetween(value.length, min, max)) {
    showError(
      inputEle,
      `${inputEle.name} must be between ${min} and ${max} characters.`
    );
  } else {
    showSuccess(inputEle);
    valid = true;
  }

  return valid;
}

function validateEmail(emailEle) {
  let valid = false;
  const email = emailEle.value.trim();
  if (!isRequired(email)) {
    showError(emailEle, "Email cannot be blank.");
  } else if (!isEmailValid(email)) {
    showError(emailEle, "Email is not valid.");
  } else {
    showSuccess(emailEle);
    valid = true;
  }
  return valid;
}
function validPassword() {
  let valid = false;

  const password = signUpPass.value.trim();

  if (!isRequired(password)) {
    showError(signUpPass, "Password cannot be blank.");
  } else if (!isPasswordSecure(password)) {
    showError(
      signUpPass,
      "Password must has at least 8 characters that include at least 1 lowercase character, 1 uppercase characters, 1 number, and 1 special character in (!@#$%^&*)"
    );
  } else {
    showSuccess(signUpPass);
    valid = true;
  }

  return valid;
}
function checkConfirmPass() {
  let valid = false;
  // check confirm password
  const confirmPassword = signUpConfirmPass.value.trim();
  const password = signUpPass.value.trim();

  if (!isRequired(confirmPassword)) {
    showError(signUpConfirmPass, "Please enter the password again");
  } else if (password !== confirmPassword) {
    showError(signUpConfirmPass, "The password does not match");
  } else {
    showSuccess(signUpConfirmPass);
    valid = true;
  }

  return valid;
}
const debounce = (fn, delay = 500) => {
  let timeoutId;
  return (...args) => {
    // cancel the previous timer
    if (timeoutId) {
      clearTimeout(timeoutId);
    }
    // setup a new timer
    timeoutId = setTimeout(() => {
      fn.apply(null, args);
    }, delay);
  };
};

signUpForm?.addEventListener(
  "input",
  debounce(function (e) {
    switch (e.target.id) {
      case "firstname":
        validateInput(userFirstName, 2, 25);
        break;
      case "lastname":
        validateInput(userLastName, 2, 25);
        break;
      case "email":
        validateEmail(signUpEmail);
        break;
      case "signUpPassword":
        validPassword();
        break;
      case "confirm-password":
        checkConfirmPass();
        break;
    }
  })
);

//sign in form
const signInid = document.getElementById("sign-in-userid");
signInForm?.addEventListener(
  "input",
  debounce(function (e) {
    switch (e.target.id) {
      case "sign-in-userid":
        validateEmail(signInid);
        break;
      case "signInPassword":
        break;
    }
  })
);

//remove meal from database
document.querySelectorAll(".remove-meal-card")?.forEach(function (button) {
  button.addEventListener("click", function (event) {
    event.preventDefault();

    let mealId = this.closest(".card").getAttribute("data-id");

    fetch(`/remove-meal`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ mealId: mealId }),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        // Refresh the page after successful deletion
        location.reload();
      })
      .catch((error) => {
        console.error("Error:", error);
        // Handle error here, if needed
      });
  });
});

//remove meal from cart
document
  .querySelectorAll(".remove-product-from-cart")
  ?.forEach(function (button) {
    button.addEventListener("click", function (event) {
      event.preventDefault();

      let mealId = this.closest(".cart-item").getAttribute("data-id");

      fetch(`/remove-meal-from-cart?mealId=${mealId}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          // Refresh the page after successful deletion
          location.reload();
        })
        .catch((error) => {
          console.error("Error:", error);
          // Handle error here, if needed
        });
    });
  });

function updateTotalPrice(quantityInput) {
  let cartItem = quantityInput.closest(".cart-item");
  let quantity = Number(quantityInput.value);
  let pricePerUnit = Number(
    cartItem.querySelector(".product-price").textContent
  );
  let totalPriceElement = cartItem.querySelector(".product-total");

  // Calculate the new total price
  let totalPrice = pricePerUnit * quantity;
  // Update the total price on the page
  totalPriceElement.textContent = totalPrice.toFixed(2);

  // Update the subtotal
  updateSubtotal();
}

function updateSubtotal() {
  let totalPrices = Array.from(
    document.querySelectorAll(".product-total"),
    (totalPriceElement) => Number(totalPriceElement.textContent)
  );
  let subtotal = totalPrices.reduce((a, b) => a + b, 0);
  document.querySelector(".subtotal-price").textContent = subtotal.toFixed(2);
}

//update quantity of meal in cart
document
  .querySelectorAll(".update-product-quantity")
  ?.forEach(function (input) {
    input.addEventListener("change", function (event) {
      event.preventDefault();

      let mealId = this.closest(".cart-item").getAttribute("data-id");
      let quantity = this.value;

      fetch(`/update-meal-quantity?mealId=${mealId}&quantity=${quantity}`, {
        method: "PATCH",
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok");
          }
          // Update the quantity on the page
          // document.getElementById(`quantity-${mealId}`).textContent = quantity;
          updateTotalPrice(this);
        })
        .catch((error) => {
          console.error(
            "There has been a problem with your fetch operation:",
            error
          );
        });
    });
  });

// add new meal to system
document.addEventListener("DOMContentLoaded", (event) => {
  let form = document.getElementById("addMealForm");
  let myModal = document.getElementById("addMeal");

  form?.addEventListener("submit", function (event) {
    event.preventDefault();

    let formData = new FormData(form);

    fetch(form.action, {
      method: form.method,
      body: formData,
      headers: {
        Accept: "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.errors) {
          // Remove existing error messages
          let errorElements = form.querySelectorAll(".alert-error");
          errorElements.forEach((element) => element.remove());

          // Display new error messages
          for (let field in data.errors) {
            let fieldElement = form.querySelector(`#${field}-error`);
            const type = field === "errorMessage" ? "danger" : "error";
            if (fieldElement) {
              const wrapper = document.createElement("div");
              wrapper.innerHTML = [
                `<div class="alert alert-${type} role="alert">`,
                `   <div>${data.errors[field]}</div>`,
                "</div>",
              ].join("");

              fieldElement.append(wrapper);
            }
          }
        } else {
          // If there are no errors, close the modal and refresh the page
          myModal.hide();
          location.reload();
        }
      })
      .catch((error) => console.error("Error:", error));
  });

  myModal?.addEventListener("hidden.bs.modal", function (event) {
    form.reset();
    form.classList.remove("was-validated");
  });
});

const getCardInfo = (cardEle) => {
  let cardContent = cardEle.querySelector(".card-body");
  let nutritionDetail = cardContent.querySelectorAll(".nutrition-detail");
  return {
    id: cardEle.getAttribute("data-id"),
    name: cardContent.querySelector(".card-title").textContent,
    photo: cardEle.querySelector(".card-img-top").dataset.photo,
    price: parseFloat(
      cardContent.querySelector(".meal-price").textContent.replace("$", "")
    ),
    ingredients: cardContent.querySelector(".card-text").textContent,
    nutrition: {
      calorie: parseInt(nutritionDetail[0].textContent),
      carbohydrate: parseInt(nutritionDetail[1].textContent),
      protein: parseInt(nutritionDetail[2].textContent),
      fat: parseInt(nutritionDetail[3].textContent),
    },
    category: cardContent
      .querySelector(".meal-category")
      .textContent.split(", "),
  };
};
const updateCardInfo = (cardEle, mealInfo) => {
  cardEle.querySelector(".card-title").textContent = mealInfo.name;
  cardEle.querySelector(".meal-price").textContent = mealInfo.price;
  cardEle.querySelector(".card-text").textContent = mealInfo.ingredients;
  cardEle.querySelector("#calorie").textContent = mealInfo.nutrition.calorie;
  cardEle.querySelector("#carbohydrate").textContent =
    mealInfo.nutrition.carbohydrate;
  cardEle.querySelector("#protein").textContent = mealInfo.nutrition.protein;
  cardEle.querySelector("#fat").textContent = mealInfo.nutrition.fat;
  cardEle.querySelector(".meal-category").textContent = mealInfo.category;
};

const getFormInfo = (formEle) => {
  let categories = formEle.querySelectorAll("[name=category]:checked");
  console.log(categories);
  return {
    name: formEle.querySelector("#meal-name").value,
    photo: formEle.querySelector("#meal-photo").value,
    price: parseFloat(
      formEle.querySelector("#meal-price").value.replace("$", "")
    ),
    ingredients: formEle.querySelector("#meal-ingredients").value,
    nutrition: {
      calorie: parseInt(formEle.querySelector("#calorie").value),
      carbohydrate: parseInt(formEle.querySelector("#carbohydrate").value),
      protein: parseInt(formEle.querySelector("#protein").value),
      fat: parseInt(formEle.querySelector("#fat").value),
    },
    category: [...categories].map((category) => category.value),
  };
};

const updateFormInfo = (formEle, mealInfo) => {
  formEle.querySelector("#meal-name").value = mealInfo.name;
  formEle.querySelector("#meal-photo").value = mealInfo.photo;
  formEle.querySelector("#meal-price").value = mealInfo.price;
  formEle.querySelector("#protein").value = mealInfo.nutrition.protein;
  formEle.querySelector("#calorie").value = mealInfo.nutrition.calorie;
  formEle.querySelector("#fat").value = mealInfo.nutrition.fat;
  formEle.querySelector("#carbohydrate").value =
    mealInfo.nutrition.carbohydrate;
  formEle.querySelector("#meal-ingredients").value = mealInfo.ingredients;

  // logic for checkbox
  const categories = mealInfo.category;
  const allCategories = formEle.querySelectorAll("[name=category]");
  allCategories.forEach((category) => {
    if (categories.includes(category.value)) {
      category.checked = true;
    } else {
      category.checked = false;
    }
  });
};

// update the meal info
document.querySelectorAll(".update-meal-info")?.forEach(function (button) {
  button.addEventListener("click", function () {
    let form = document.getElementById("updateMealForm");
    let cardElement = this.closest(".card");
    let currentMealInfo = getCardInfo(cardElement);

    updateFormInfo(form, currentMealInfo);

    let updateMealModal = new bootstrap.Modal(
      document.getElementById("updateMeal")
    );
    updateMealModal.show();

    document.getElementById("updateMealForm").addEventListener(
      "submit",
      function (event) {
        event.preventDefault();
        const newMealInfo = getFormInfo(form);

        fetch(`/update-meal?mealId=${currentMealInfo.id}`, {
          method: "PUT",
          body: JSON.stringify(newMealInfo),
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => response.json())
          .then((data) => {
            if (data.errors) {
              // Remove existing error messages
              let errorElements = form.querySelectorAll(".alert-error");
              errorElements.forEach((element) => element.remove());

              // Display new error messages
              for (let field in data.errors) {
                let fieldElement = form.querySelector(`#${field}-error`);
                const type = field === "errorMessage" ? "danger" : "error";
                if (fieldElement) {
                  const wrapper = document.createElement("div");
                  wrapper.innerHTML = [
                    `<div class="alert alert-${type} role="alert">`,
                    `   <div>${data.errors[field]}</div>`,
                    "</div>",
                  ].join("");

                  fieldElement.append(wrapper);
                }
              }
            } else {
              // Update the meal card with the new meal information
              updateMealModal.hide();
              location.reload();
            }
          })
          .catch((error) => {
            console.log(error);
          });
      },
      { once: true }
    );
  });
});
