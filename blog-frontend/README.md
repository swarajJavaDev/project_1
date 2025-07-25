# Blog Frontend - React Application

A modern, responsive React.js frontend for the Spring Boot Blog API. This application provides a complete interface for managing blog posts and comments with a beautiful, user-friendly design.

## Features

### 📝 Post Management
- **View all posts** with pagination support (5 posts per page)
- **Create new posts** with form validation
- **Edit existing posts** with pre-populated data
- **Delete posts** with confirmation dialog
- **Sort posts** by date or title (ascending/descending)
- **Post detail view** with full content display

### 💬 Comment System
- **View comments** for each post
- **Add new comments** with name, email, and message
- **Edit existing comments** inline
- **Delete comments** with confirmation
- **Real-time comment count** display

### 🎨 User Interface
- **Responsive design** works on desktop, tablet, and mobile
- **Bootstrap 5** for modern, clean styling
- **Loading spinners** for better user experience
- **Error handling** with user-friendly messages
- **Form validation** with real-time feedback
- **Navigation breadcrumbs** for easy navigation

## Prerequisites

Before running this application, make sure you have:

- **Node.js** (v14 or higher)
- **npm** (comes with Node.js)
- **Spring Boot Backend** running on `http://localhost:8080`

## Backend API Requirements

This frontend expects the following Spring Boot backend endpoints:

### Post Endpoints
- `GET /api/post` - Get all posts with pagination
- `GET /api/post/{id}` - Get post by ID
- `POST /api/post` - Create new post
- `PUT /api/post/{id}` - Update post
- `DELETE /api/post/{id}` - Delete post

### Comment Endpoints
- `GET /api/posts/{postId}/comments` - Get comments for a post
- `POST /api/posts/{postId}/comments` - Create new comment
- `PUT /api/posts/{postId}/comments/{commentId}` - Update comment
- `DELETE /api/posts/{postId}/comments/{commentId}` - Delete comment

## Installation & Setup

1. **Clone or navigate to the frontend directory:**
   ```bash
   cd blog-frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Configure API URL (if needed):**
   - Open `src/services/api.js`
   - Modify `API_BASE_URL` if your backend runs on a different port:
   ```javascript
   const API_BASE_URL = 'http://localhost:8080/api';
   ```

## Running the Application

1. **Start the development server:**
   ```bash
   npm start
   ```

2. **Open your browser and navigate to:**
   ```
   http://localhost:3000
   ```

The application will automatically reload when you make changes to the source code.

## Project Structure

```
blog-frontend/
├── public/
│   ├── index.html
│   └── ...
├── src/
│   ├── components/           # React components
│   │   ├── Navbar.js        # Navigation bar
│   │   ├── PostList.js      # Posts listing with pagination
│   │   ├── PostDetail.js    # Single post view
│   │   ├── CreatePost.js    # Create new post form
│   │   ├── EditPost.js      # Edit post form
│   │   ├── CommentSection.js # Comments management
│   │   └── LoadingSpinner.js # Loading indicator
│   ├── services/
│   │   └── api.js           # API service layer
│   ├── App.js               # Main application component
│   ├── App.css              # Application styles
│   └── index.js             # Application entry point
├── package.json
└── README.md
```

## Usage Guide

### Creating a New Post
1. Click **"Create New Post"** button on the homepage
2. Fill in the required fields:
   - **Title** (minimum 2 characters)
   - **Description** (minimum 5 characters)
   - **Content** (minimum 15 characters)
3. Click **"Create Post"** to save

### Viewing Posts
- The homepage shows all posts with pagination
- Click **"Read More"** to view the full post
- Use sorting controls to order by date or title

### Managing Comments
1. Navigate to a post detail page
2. Click **"Add Comment"** to create a new comment
3. Fill in your name, email, and comment
4. Use **Edit** or **Delete** buttons to manage existing comments

### Editing Posts
1. Click **"Edit"** button on any post
2. Modify the content in the form
3. Click **"Update Post"** to save changes

## Styling & Customization

The application uses **Bootstrap 5** for styling with custom CSS enhancements:

- **Primary color:** Blue (#007bff)
- **Responsive design:** Mobile-first approach
- **Custom animations:** Hover effects and transitions
- **Error/Success messages:** Color-coded feedback

To customize the appearance:
1. Modify `src/App.css` for custom styles
2. Update Bootstrap variables if needed
3. Add new CSS classes for custom components

## API Integration

The frontend communicates with the Spring Boot backend through:

- **Axios HTTP client** for API requests
- **Request/Response interceptors** for logging and error handling
- **Error handling** with user-friendly messages
- **Loading states** for better UX

## Troubleshooting

### Common Issues

1. **"Failed to fetch posts" error:**
   - Ensure Spring Boot backend is running on port 8080
   - Check CORS configuration in the backend
   - Verify API endpoints are working

2. **Posts not displaying:**
   - Check browser console for errors
   - Verify the API response format matches expected structure
   - Ensure backend returns `PostResponse` object with `postDto` array

3. **Comments not loading:**
   - Comments endpoint might return 404 if no comments exist (this is normal)
   - Check if the backend comment endpoints are properly configured

4. **Form validation errors:**
   - Ensure all required fields meet minimum length requirements
   - Check backend validation configuration

### CORS Issues
If you encounter CORS errors, add this to your Spring Boot backend:

```java
@CrossOrigin(origins = "http://localhost:3000")
```

Or configure global CORS in your Spring Boot application.

## Available Scripts

- `npm start` - Runs the app in development mode
- `npm test` - Launches the test runner
- `npm run build` - Builds the app for production
- `npm run eject` - Ejects from Create React App (irreversible)

## Technologies Used

- **React 18** - JavaScript library for building user interfaces
- **React Router DOM** - Client-side routing
- **Axios** - HTTP client for API requests
- **Bootstrap 5** - CSS framework for styling
- **Create React App** - React development environment

## Browser Support

This application supports all modern browsers including:
- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is open source and available under the MIT License.
