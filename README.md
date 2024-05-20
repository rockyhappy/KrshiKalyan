# Krishi Kalyaan

## Overview
Krishi Kalyaan is a bilingual Android application developed to facilitate lending and borrowing of farming items among farmers. Built with a modern MVVM architecture and incorporating Firebase services, it offers a seamless experience for farmers to connect, share resources, and improve agricultural practices.

## Features

### User-Friendly Authentication
Krishi Kalyaan offers a seamless authentication experience for farmers through phone number authentication. Users can easily log in or sign up using their phone numbers, eliminating the need to remember complex passwords. The app provides OTP (One-Time Password) services for quick and secure authentication, ensuring a hassle-free login process.

### Intuitive UI
The app is designed with an intuitive and user-friendly interface to cater to farmers of all backgrounds and tech-savviness. The UI is crafted to be easily understandable and navigable, allowing users to access essential features and functionalities with minimal effort. Clear instructions and visual cues guide users through the app, enhancing their overall experience.

### Real-time Data Synchronization
Krishi Kalyaan utilizes Firebase Firestore for real-time data storage and synchronization. This enables farmers to access the latest information instantly, ensuring that they always have up-to-date data at their fingertips. Whether listing farming items, managing requests, or updating profile information, changes are synchronized across devices in real time, enhancing collaboration and communication among users.

### Image Management
The app allows users to upload and manage images of farming items using Firebase Storage. Farmers can easily add images to their product listings, providing visual representations of the items they want to lend or borrow. This not only enhances the user experience but also makes product listing more appealing and informative, helping users make informed decisions.

### Security Integration
Krishi Kalyaan integrates Play Integrity App Check to ensure app security and authenticity. This helps protect users from potential security threats and ensures that the app is trustworthy and safe to use. By leveraging Play Integrity, the app maintains a high level of security, providing users with peace of mind while using the platform.

### Dependency Injection
The app utilizes Dagger Hilt for streamlined dependency management, ensuring robustness, scalability, and maintainability of the codebase. Dagger Hilt simplifies the process of managing dependencies and promotes code reusability, making it easier for developers to add new features or make changes to existing ones.

### Efficient Image Loading
Krishi Kalyaan employs Coil for efficient image loading and caching. Coil is a lightweight image loading library that optimizes image loading performance and memory usage. By efficiently loading and caching images, the app delivers a smooth and responsive user experience, even when dealing with large amounts of image data.

### Local Data Storage
The app uses Datastore for local data storage, enabling offline access to essential information. Datastore provides a reliable and efficient way to store and retrieve data locally on the device, ensuring that users can access critical data even when they are offline or have limited connectivity. This enhances the app's usability and reliability, especially in rural areas with unreliable internet access.

## Usage
The app provides farmers with the following functionalities:
1. **Login/Signup**: Farmers can log in or sign up using their phone numbers. OTP services provide easy authentication.
2. **Choose Role**: Users can choose to be either lenders or borrowers.
3. **Product Listing**: Farmers can list farming items they want to lend or borrow from a predefined list or by adding new products.
4. **Request Management**: Lenders and borrowers can manage their lending and borrowing requests, including accepting or rejecting requests.

## Screenshots
### Authentication Screens 


### Lender's Dashboard
![Lender Dashboard](lender_dashboard.png)

### Borrower's Dashboard
![Borrower Dashboard](borrower_dashboard.png)

## Dependencies
Krishi Kalyaan relies on the following key dependencies:
- **Firebase Auth**: For user authentication via phone number. 
- **Firebase Firestore**: For real-time data storage and synchronization.
- **Firebase Storage**: For managing images of farming items.
- **Play Integrity App Check**: For ensuring app integrity and security.
- **Dagger Hilt**: For streamlined dependency management.
- **Kotlin Symbolic Programming (KSP)**: For efficient code generation.
- **Coil**: For efficient image loading and caching.
- **Datastore**: For local data storage.

## Getting Started
To start using Krishi Kalyaan:
1. Clone the repository: `git clone https://github.com/your-repo/krishi-kalyaan.git`
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
4. Log in or sign up using your phone number.
5. Choose your role as a lender or borrower and start lending or borrowing farming items.

## Contributing
Contributions to Krishi Kalyaan are welcome! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bug fix: `git checkout -b feature-name`.
3. Make your changes and commit them: `git commit -m 'Add new feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Create a pull request with a detailed description of your changes.

## Contact
For any inquiries or feedback regarding Krishi Kalyaan, please contact the maintainer.
