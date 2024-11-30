![bookXpress Banner](https://github.com/paulmacatangay/MacatangayPaulAndrewCS2102_OOPFinalproject_bookXpress/blob/60d598d329da3a9987cd378a5b62b4b12e922b0f/images/bookXpress%20Banner.jpg)
# 📚 **bookXpress - Book Rental System**

## I. **Project Overview**
### **Project Description**  
The **Book Rental System** is an interactive software application designed to manage the rental of books. It provides functionality for both **admin users** and **regular users** to interact with a library of books.  
Admin users can manage the book catalog by adding new books, updating book availability, and adjusting rental prices. Regular users can create accounts, log in, browse available books, rent them, and manage their inventory of rented books.

---

### **Key Features**

#### **🔧 Admin Functionality:**
- Add, update, and remove books in the system.
- Modify book availability and rental prices.
- View the complete list of books and manage the catalog.

#### **👥 User Functionality:**
- Create and manage user accounts with secure login.
- Browse available books in the library.
- Rent books and maintain an inventory of rented books.
- Top-up account balance and earn points based on book rentals.

#### **📖 Book Management:**
- Books are categorized as **Fiction** and **Non-Fiction**, each having additional specific attributes such as genre or subject.
- Users can rent books if copies are available and they have sufficient balance.
- Real-time updates to the book catalog when books are rented or when inventory is modified.

---

This project leverages **object-oriented programming** principles, including **encapsulation**, **inheritance**, **polymorphism**, and **abstraction**, to manage a structured and extensible system for book rental management.

---

## II. **Application of OOP Principles**

### **🔒 Encapsulation:**

**Definition:**  
Encapsulation hides the internal state of objects and only exposes methods that allow controlled access to the data.

**Example:**  
- The **`Account`** class encapsulates user-specific details like `username`, `password`, `balance`, and `inventory` in private fields. Methods such as **`createAccount()`** and **`login()`** handle access to these fields.
- Similarly, the **`Book`**, **`FictionBook`**, and **`NonFictionBook`** classes encapsulate book-related details like `cost`, `copiesAvailable`, `genre`, and `subject` with private fields and provide public methods for interaction.

---

### **🔄 Polymorphism:**

**Definition:**  
Polymorphism allows objects of different types to be treated as objects of a common super type. It allows one method to be used for different types.

**Example:**  
- In the **`displayAvailableBooks()`** method, polymorphism is applied when the system dynamically checks the book type (either **`FictionBook`** or **`NonFictionBook`**) and prints additional details based on the type. The method works seamlessly for both types of books.

---

### **🔍 Abstraction:**

**Definition:**  
Abstraction involves hiding the complex implementation details and exposing only the necessary parts of the object.

**Example:**  
- Methods like **`rentBook()`**, **`updateBookCopies()`**, and **`topUpBalance()`** abstract the complexity of performing actions like renting a book or updating book data, presenting a simple interface to the user. The user does not need to know the implementation details, only the actions they can perform.

---

### **🌳 Inheritance:**

**Definition:**  
Inheritance allows a class to inherit properties and methods from another class, promoting code reuse.

**Example:**  
- The **`FictionBook`** and **`NonFictionBook`** classes extend the **`Book`** class and inherit its properties such as `copiesAvailable` and `cost`. These subclasses also add specific properties like `genre` and `subject`, allowing for specialized behavior while maintaining common functionality from the **`Book`** class.

---

## III. **Chosen Sustainable Development Goal (SDG)**

### **Goal 4: Quality Education**

**Target:**  
Ensure inclusive and equitable quality education and promote lifelong learning opportunities for all.

---

### **Integration of SDG into the Project:**

#### **📚 Promoting Accessibility to Educational Resources:**
- By offering a rental service for books, users can access a wide range of educational materials without the high cost of purchasing them. This ensures that knowledge is accessible to all, regardless of financial constraints.

#### **🔄 Encouraging Lifelong Learning:**
- The **points system** incentivizes users to rent more books and engage in continuous learning. As users earn points by renting books, they are motivated to continue their learning journey.

#### **🌍 Digital Inclusivity:**
- The system provides an easy-to-use digital platform for renting books. Users can manage their accounts, track their rented books, and top-up their balances, making it a convenient option for people who might not have easy access to physical libraries.

#### **🚀 Future Growth:**
- The platform can be expanded in the future to offer digital books (eBooks), integrating technology for broader access to resources. This would align more closely with SDG 4 by providing more flexible learning options for people globally.
